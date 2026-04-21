package com.example.linkfamily.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.linkfamily.Model.Entity.Story;

public interface StoryService extends IService<Story> {
    
    Story findByPhotoId(Long photoId);

    void incrementListenCount(Long storyId);
}
