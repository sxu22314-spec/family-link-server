package com.example.linkfamily.Model.Dto;

import lombok.Data;

@Data
public class UpdateLockedRequest {
    private String id;       // 对应前端传来的 id
    private Integer isLocked; // 对应前端传来的 isLocked
}