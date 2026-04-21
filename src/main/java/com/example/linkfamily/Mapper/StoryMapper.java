package com.example.linkfamily.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.linkfamily.Model.Entity.Story;

@Mapper
public interface StoryMapper extends BaseMapper<Story> {
    @Select("""
        SELECT id, photo_id, title, description, subject,
               audio_url, cover_image_url, listen_count, is_locked,
               created_at, updated_at
        FROM tb_story
        WHERE photo_id = #{photoId}
        ORDER BY id DESC
        LIMIT 1
    """)
    Story findByPhotoId(@Param("photoId") Long photoId);

    @Update("UPDATE tb_story SET listen_count = listen_count + 1 WHERE id = #{storyId}")
    int incrementListenCount(@Param("storyId") Long storyId);
}
