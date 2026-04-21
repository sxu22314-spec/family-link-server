package com.example.linkfamily.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyPhotoRequest {
    
    private String title;
    
    private String theme;
    
    private LocalDate photoDate;
}
