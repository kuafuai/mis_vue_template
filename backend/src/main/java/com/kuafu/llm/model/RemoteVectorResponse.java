package com.kuafu.llm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
public class RemoteVectorResponse {
    private int code;
    private List<List<String>> result;
    private String msg;
}
