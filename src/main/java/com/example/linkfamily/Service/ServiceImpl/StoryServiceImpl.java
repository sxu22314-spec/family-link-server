package com.example.linkfamily.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.linkfamily.Mapper.StoryMapper;
import com.example.linkfamily.Model.Entity.Story;
import com.example.linkfamily.Service.StoryService;

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
    
}
