package com.kuafu.web.controller;

import com.kuafu.web.entity.SelectVO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum LoginReverence {

industry("行业表","industry"),personnelScale("人员规模","personnelScale"),customerHierarchy("客户分层","customerHierarchy"),leadStatus("线索状态","leadStatus"),customerStage("客户阶段","customerStage"),opportunityStatus("商机状态","opportunityStatus"),salesman("销售人员","salesman"),customer("客户表","customer"),visitManagement("客户拜访","visitManagement"),businessOpportunity("商机管理","businessOpportunity"),login("登录表","login"),;
    private String label;
    private String value;

    LoginReverence(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static List<SelectVO> all() {
        final LoginReverence[] values = LoginReverence.values();
        return Arrays.stream(values).map(r -> {
            return new SelectVO(r.getValue(), r.getLabel());
        }).collect(Collectors.toList());
    }
}
