package com.example.linkfamily.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.linkfamily.Model.Entity.Story;
import com.example.linkfamily.Response.Response;
import com.example.linkfamily.Service.StoryService;

@RestController
@RequestMapping("/story")
@CrossOrigin(origins = "*")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @GetMapping("/getByPhotoId/{photoId}")
    public Response<Story> getByPhotoId(@PathVariable Long photoId) {
        Story story = storyService.findByPhotoId(photoId);
        if (story == null) {
            return Response.error("No story found for photoId=" + photoId);
        }
        return Response.success(story);
    }

    @PostMapping("/incrementListenCount/{storyId}")
    public Response<Boolean> incrementListenCount(@PathVariable Long storyId) {
        storyService.incrementListenCount(storyId);
        return Response.success();
    }
}
