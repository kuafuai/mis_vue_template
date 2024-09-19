package com.kuafu.web.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseLoginVo   {
    private String defaultUserName;
    private String defaultPassword;
    private String defaultPhone;
}
