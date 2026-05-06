package com.example.linkfamily.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.linkfamily.Mapper.StoryMapper;
import com.example.linkfamily.Model.Entity.Story;
import com.example.linkfamily.Service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class StoryServiceImpl extends ServiceImpl<StoryMapper, Story> implements StoryService {

    @Autowired
    private StoryMapper storyMapper;

    @Override
    public Story findByPhotoId(Long photoId) {
        return storyMapper.findByPhotoId(photoId);
    }

    @Override
    public void incrementListenCount(Long storyId) {
        storyMapper.incrementListenCount(storyId);
    }

    @Override
    public Page<Story> listStories(int pageNum, int pageSize) {
        Page<Story> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Story> query = new LambdaQueryWrapper<Story>()
                .orderByDesc(Story::getCreatedAt);
        return this.page(page, query);
    }

    @Override
    public Story createStory(Story story) {
        if (!StringUtils.hasText(story.getSubject())) {
            story.setSubject(story.getTitle());
        }
        if (story.getListenCount() == null || story.getListenCount() < 0) {
            story.setListenCount(0);
        }
        if (story.getIsLocked() == null) {
            story.setIsLocked(1);
        }
        this.save(story);
        return this.getById(story.getId());
    }

    @Override
    public Story updateStory(Long id, Story update) {
        Story existing = this.getById(id);
        if (existing == null) {
            return null;
        }

        LambdaUpdateWrapper<Story> wrapper = new LambdaUpdateWrapper<Story>().eq(Story::getId, id);

        if (update.getPhotoId() != null) {
            wrapper.set(Story::getPhotoId, update.getPhotoId());
        }
        if (StringUtils.hasText(update.getTitle())) {
            wrapper.set(Story::getTitle, update.getTitle());
        }
        if (StringUtils.hasText(update.getDescription())) {
            wrapper.set(Story::getDescription, update.getDescription());
        }
        if (StringUtils.hasText(update.getSubject())) {
            wrapper.set(Story::getSubject, update.getSubject());
        }
        if (StringUtils.hasText(update.getAudioUrl())) {
            wrapper.set(Story::getAudioUrl, update.getAudioUrl());
        }
        if (StringUtils.hasText(update.getCoverImageUrl())) {
            wrapper.set(Story::getCoverImageUrl, update.getCoverImageUrl());
        }
        if (update.getListenCount() != null) {
            wrapper.set(Story::getListenCount, update.getListenCount());
        }
        if (update.getIsLocked() != null) {
            wrapper.set(Story::getIsLocked, update.getIsLocked());
        }
        if (StringUtils.hasText(update.getTaskType())) {
            wrapper.set(Story::getTaskType, update.getTaskType());
        }
        if (update.getTaskData() != null) {
            wrapper.set(Story::getTaskData, update.getTaskData());
        }

        this.update(wrapper);
        return this.getById(id);
    }

    @Override
    public void deleteStory(Long id) {
        this.removeById(id);
    }
}