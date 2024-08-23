package com.kuafu.web.controller;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum LoginReverenceValue {
    leadStatus("leadStatus", "visit_management_all.get_lead_status_list"),
    customerStage("customerStage", "business_opportunity_all.get_customer_stage_list"),
    visitManagement("visitManagement", "business_opportunity_all.get_visit_management_list"),
    customerHierarchy("customerHierarchy", "customer_all.get_customer_hierarchy_list"),
    personnelScale("personnelScale", "customer_all.get_personnel_scale_list"),
    industry("industry", "customer_all.get_industry_list"),
    salesman("salesman", "business_opportunity_all.get_salesman_list"),
    opportunityStatus("opportunityStatus", "business_opportunity_all.get_opportunity_status_list"),
    customer("customer", "business_opportunity_all.get_customer_list"),
    ;
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
