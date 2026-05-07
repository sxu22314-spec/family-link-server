package com.example.linkfamily.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.linkfamily.Exception.FileException;
import com.example.linkfamily.Model.Entity.Story;
import com.example.linkfamily.Response.Response;
import com.example.linkfamily.Service.MinioService;
import com.example.linkfamily.Service.StoryService;
import com.example.linkfamily.Utils.AudioValidationUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/story")
public class StoryController {

    private static final Set<String> ALLOWED_TASK_TYPES = Set.of("drawing", "question", "memory-match", "photo-upload");

    @Autowired
    private StoryService storyService;

    @Autowired
    private MinioService minioService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/list")
    public Response<Map<String, Object>> listStories(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer limit
    ) {
        int resolvedPageNum = firstValid(pageNum, pageNo, page, 1);
        int resolvedPageSize = firstValid(pageSize, size, limit, 4);

        if (resolvedPageNum < 1 || resolvedPageSize < 1 || resolvedPageSize > 100) {
            return Response.error("Invalid pagination params");
        }

        Page<Story> pageData = storyService.listStories(resolvedPageNum, resolvedPageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("records", pageData.getRecords());
        data.put("total", pageData.getTotal());
        data.put("pageNum", resolvedPageNum);
        data.put("pageSize", resolvedPageSize);
        return Response.success("ok", data);
    }

    @PostMapping("/create")
    public Response<Story> createStory(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String subject,
            @RequestParam String taskType,
            @RequestParam(required = false) String taskData,
            @RequestParam(required = false) Boolean isLocked,
            @RequestParam(required = false) Integer listenCount,
            @RequestParam(required = false, defaultValue = "false") Boolean uploadAudio,
            @RequestParam(required = false) MultipartFile audioFile,
            @RequestParam(required = false) Long photoId
    ) {
        try {
            validateRequiredText(title, "title");
            validateRequiredText(description, "description");
            validateRequiredText(subject, "subject");
            validateTaskType(taskType);
            validateListenCount(listenCount);
            validateTaskData(taskData);

            String audioUrl = null;
            if (Boolean.TRUE.equals(uploadAudio)) {
                AudioValidationUtils.validateAudioFile(audioFile);
                String objectName = minioService.uploadFile(audioFile, audioFile.getOriginalFilename());
                audioUrl = minioService.generatePublicUrl(objectName);
            }

            Story story = new Story()
                    .setPhotoId(photoId)
                    .setTitle(title.trim())
                    .setDescription(description.trim())
                    .setSubject(subject.trim())
                    .setTaskType(taskType)
                    .setTaskData(taskData)
                    .setAudioUrl(audioUrl)
                    .setListenCount(listenCount)
                    .setIsLocked(Boolean.TRUE.equals(isLocked) ? 1 : 0);

            if (isLocked == null) {
                story.setIsLocked(1);
            }

            return Response.success("ok", storyService.createStory(story));
        } catch (FileException e) {
            return new Response<>(e.getCode(), e.getMessage(), null);
        }
    }

    @PutMapping("/update/{id}")
    public Response<Story> updateStory(@PathVariable Long id, @RequestBody Story update) {
        if (id == null || id < 1) {
            return Response.error("Invalid id");
        }
        if (update.getTaskType() != null) {
            validateTaskType(update.getTaskType());
        }
        if (update.getListenCount() != null) {
            validateListenCount(update.getListenCount());
        }
        if (update.getTaskData() != null) {
            validateTaskData(update.getTaskData());
        }

        Story saved = storyService.updateStory(id, update);
        if (saved == null) {
            return Response.error("Story not found");
        }
        return Response.success("ok", saved);
    }

    @DeleteMapping("/delete/{id}")
    public Response<Boolean> deleteStory(@PathVariable Long id) {
        if (id == null || id < 1) {
            return Response.error("Invalid id");
        }

        Story existing = storyService.getById(id);
        if (existing == null) {
            return Response.error("Story not found");
        }

        storyService.deleteStory(id);
        return Response.success("ok", true);
    }

    @GetMapping("/getByPhotoId/{photoId}")
    public Response<Story> getByPhotoId(@PathVariable Long photoId) {
        Story story = storyService.findByPhotoId(photoId);
        if (story == null) {
            return Response.error("No story found for photoId=" + photoId);
        }
        return Response.success(story);
    }

    @GetMapping("/getById/{id}")
    public Response<Story> getById(@PathVariable Long id) {
        if (id == null || id < 1) {
            return Response.error("Invalid id");
        }
        Story story = storyService.getById(id);
        if (story == null) {
            return Response.error("Story not found");
        }
        return Response.success(story);
    }

    @PostMapping("/incrementListenCount/{storyId}")
    public Response<Boolean> incrementListenCount(@PathVariable Long storyId) {
        storyService.incrementListenCount(storyId);
        return Response.success();
    }

    private int firstValid(Integer first, Integer second, Integer third, int defaultValue) {
        if (first != null) {
            return first;
        }
        if (second != null) {
            return second;
        }
        if (third != null) {
            return third;
        }
        return defaultValue;
    }

    private void validateRequiredText(String value, String fieldName) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
    }

    private void validateTaskType(String taskType) {
        if (!StringUtils.hasText(taskType) || !ALLOWED_TASK_TYPES.contains(taskType)) {
            throw new IllegalArgumentException("Invalid taskType, allowed: drawing|question|memory-match|photo-upload");
        }
    }

    private void validateListenCount(Integer listenCount) {
        if (listenCount != null && listenCount < 0) {
            throw new IllegalArgumentException("listenCount must be >= 0");
        }
    }

    private void validateTaskData(String taskData) {
        if (!StringUtils.hasText(taskData)) {
            return;
        }
        try {
            objectMapper.readTree(taskData);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("taskData must be valid JSON string");
        }
    }
}
