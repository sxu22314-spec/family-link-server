package com.example.linkfamily.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.linkfamily.Model.Entity.Story;

public interface StoryService extends IService<Story> {

    Story findByPhotoId(Long photoId);

    void incrementListenCount(Long storyId);

    Page<Story> listStories(int pageNum, int pageSize);

    Story createStory(Story story);

    Story updateStory(Long id, Story update);

    void deleteStory(Long id);
}