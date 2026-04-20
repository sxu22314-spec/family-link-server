package com.example.linkfamily.Model.Dto;

public enum PuzzleEventType {
    JOIN,
    START,
    MOVE,
    COMPLETE,
    HELP_REQUEST, // 孙子发送：帮帮我
    HELP_ACCEPT   // 老人发送：我来接管

}
