package com.example.linkfamily.Model.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PuzzlePhotoVO {
    private String id;
    private String title;
    private String theme;
    private String imageUrl;
}
