package com.example.linkfamily.Controller;

import jakarta.validation.Valid;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.linkfamily.Model.Dto.PuzzleSyncEnvelope;

@Controller
public class PuzzleSyncController {

    private final SimpMessagingTemplate messagingTemplate;

    // 手动编写构造函数，Spring 会自动调用它并注入 Bean
    public PuzzleSyncController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/puzzle.join")
    public void join(@Valid @Payload PuzzleSyncEnvelope event) {
        broadcast(event);
    }

    @MessageMapping("/puzzle.start")
    public void start(@Valid @Payload PuzzleSyncEnvelope event) {
        // Optional validation: only allow actorRole=grandson
        broadcast(event);
    }

    @MessageMapping("/puzzle.move")
    public void move(@Valid @Payload PuzzleSyncEnvelope event) {
        // Optional validation: only allow actorRole=grandson
        broadcast(event);
    }

    @MessageMapping("/puzzle.complete")
    public void complete(@Valid @Payload PuzzleSyncEnvelope event) {
        broadcast(event);
    }

    @MessageMapping("/puzzle.help-request")
    public void helpRequest(@Valid @Payload PuzzleSyncEnvelope event) {
        // 逻辑：孙子(actorRole=grandson)向主题广播，老人会收到提示
        broadcast(event);
    }

    // --- 新增：处理老人接受并接管 ---
    @MessageMapping("/puzzle.help-accept")
    public void helpAccept(@Valid @Payload PuzzleSyncEnvelope event) {
        // 逻辑：老人(actorRole=grandparents)点击接受后广播
        // 此时前端会根据此事件切换控制权（老人变为能动，孙子变为观看）
        broadcast(event);
    }

    private void broadcast(PuzzleSyncEnvelope event) {
        String topic = "/topic/puzzle/" + event.getRoomId();
        messagingTemplate.convertAndSend(topic, event);
    }

}
