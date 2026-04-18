package com.example.linkfamily.Model.Entity;

import java.time.LocalDate;
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
@TableName("tb_photo")
public class Photo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("title")
    private String title;

    @TableField("theme")
    private String theme;

    @TableField("image_url")
    private String imageUrl;

    @TableField("audio_url")
    private String audioUrl;

    @TableField("type")
    private Integer type;

    @TableField("is_locked")
    private Integer isLocked;

    @TableField("photo_date")
    private LocalDate photoDate;

    @TableField("create_time")
    private LocalDateTime createTime;
}
