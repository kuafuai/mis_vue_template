package com.kuafu.llm.model;

import lombok.Data;

import java.util.List;

@Data
public class RemoteVectorRequest {
    private String collectionName;
    private String type;
    private List<String> text;
}
