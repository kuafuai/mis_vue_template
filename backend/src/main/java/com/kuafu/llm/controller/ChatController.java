package com.kuafu.llm.controller;

import com.kuafu.llm.chat.Chat;
import com.kuafu.llm.config.LLMStartBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("chat")
@ConditionalOnProperty(prefix = "llm", name = "enable", havingValue = "true")
public class ChatController {

    @Autowired
    private LLMStartBusiness llmStartBusiness;

    @Autowired
    private Chat chat;

    @Value("${llm.chat.default_prompt}")
    private String defaultPrompt;

    /**
     * 对话流
     *
     * @param chatRequest
     * @return
     */
    @PostMapping("")
    public SseEmitter stream(@RequestBody ChatRequest chatRequest) {
        // 用于创建一个 SSE 连接对象
        SseEmitter emitter = new SseEmitter(3600000L);
        List<String> search = llmStartBusiness.search(chatRequest.getQuery());

        log.info("embedding search: {}", search);

        StringBuilder context = new StringBuilder("context is \n");
        for (String s : search) {
            context.append(s).append("\n");
        }

        log.info("content : {}", context);

        chat.callApiStream(chatRequest.getQuery() + "\n" + context,
                chatRequest.getConversionId(), chatRequest.getUserId(), emitter);

        // 在后台线程中模拟实时数据
        return emitter;
    }

}
