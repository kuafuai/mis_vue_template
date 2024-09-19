package com.kuafu.web.controller;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum LoginReverenceValue {
userInfo("userInfo","bug_info_all.get_user_info_list"),bug_info("bug_info","bug_info.get_select_list"),user_info("user_info","user_info.get_select_list"),bug_priority("bug_priority","bug_priority.get_select_list"),bug_status("bug_status","bug_status.get_select_list"),;
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
