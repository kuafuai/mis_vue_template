package com.kuafu.web.controller;

import com.kuafu.web.entity.SelectVO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum LoginReverence {

    SalesStatus("销售状态", "SalesStatus"), SalesLead("客户线索", "SalesLead"), SalesLeadItem("客户项目", "SalesLeadItem"), UserInfo("跟进人信息", "UserInfo"), Partner("合作伙伴信息", "Partner"), WithdrawalTransactions("提现金额表", "WithdrawalTransactions"), AmountStatus("提现金额状态", "AmountStatus"), EndAmountStatus("中间入账状态", "EndAmountStatus"), Login("登录表", "Login"),
    ;
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
