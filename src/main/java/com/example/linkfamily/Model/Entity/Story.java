package com.example.linkfamily.Model.Entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("tb_story")
public class Story {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("photo_id")
    private Long photoId;

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("subject")
    private String subject;

    @TableField("audio_url")
    private String audioUrl;

    @TableField("cover_image_url")
    private String coverImageUrl;

    @TableField("listen_count")
    private Integer listenCount;

    @TableField("is_locked")
    private Integer isLocked;

    @TableField("task_type")
    private String taskType;

    @TableField("task_data")
    private String taskData;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}