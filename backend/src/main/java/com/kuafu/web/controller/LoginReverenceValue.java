package com.kuafu.web.controller;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum LoginReverenceValue {
    None("none", "none");
    private String label;
    private String value;


    LoginReverenceValue(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static String getValue(String label) {
        for (LoginReverenceValue item : values()) {
            if (StringUtils.equalsIgnoreCase(item.getLabel(), label)) {
                return item.getValue();
            }
        }
        return null;
    }
}
