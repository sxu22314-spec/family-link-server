package com.example.linkfamily.Model.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyPhotoVO {
    private Long id;
    private String photoUrl;
    private String title;
    private String theme;
    private LocalDateTime createTime;
    private LocalDate photoDate;
}
