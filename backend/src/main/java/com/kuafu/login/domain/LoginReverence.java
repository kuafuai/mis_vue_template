package com.kuafu.login.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum LoginReverence {

        SalesStatus("销售状态","SalesStatus"),
        SalesLead("客户线索","SalesLead"),
        SalesLeadItem("客户项目","SalesLeadItem"),
        UserInfo("跟进人信息","UserInfo"),
        Partner("合作伙伴信息","Partner"),
        WithdrawalTransactions("提现金额表","WithdrawalTransactions"),
        AmountStatus("提现金额状态","AmountStatus"),
    ;


    private String label;
    private String value;

    LoginReverence(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public static List<SelectVo> all() {
        final LoginReverence[] values = LoginReverence.values();
        return Arrays.stream(values).map(r -> {
            return new SelectVo(r.getValue(), r.getLabel());
        }).collect(Collectors.toList());
    }
}
