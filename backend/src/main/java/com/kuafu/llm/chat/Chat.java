package com.kuafu.llm.chat;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface Chat {

    void callApiStream(String query, String conversationId, String userId, SseEmitter sseEmitter);

    String callApiBlock(String query, String conversationId, String userId);
}
