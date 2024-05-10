package com.kuafu.login.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kuafu.common.login.SecurityUtils;
import com.kuafu.common.util.SpringUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.common.util.WrapperFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "login", name = "enable")
public class LoginBusinessService {

    @Value("${login.table}")
    private String login_table;

    // 电话
    @Value("${login.select.table:table}")
    private String select_table;
    @Value("${login.select.column:phone_number}")
    private String select_table_column;

    // openid
    @Value("${login.openid.table:table}")
    private String openid_table;
    @Value("${login.openid.column:wechat_login_id}")
    private String openid_table_column;


    public Object getCurrentUser() {
        IService iService = SpringUtils.getBean(login_table);
        return iService.getById(SecurityUtils.getUserId());
    }

    /**
     * 查询对象
     *
     * @param key
     * @return
     */
    public Object getUserBySelectKey(Object key) {
        String table = login_table;
        if (StringUtils.isNotEmpty(select_table)) {
            table = select_table;
        }
        return getUser(table, select_table_column, key);
    }

    /**
     * 查询对象的ID
     *
     * @param key
     * @return
     */
    public Long getUserIdBySelectKey(Object key) {
        String table = login_table;
        if (StringUtils.isNotEmpty(select_table)) {
            table = select_table;
        }
        return getId(getUser(table, select_table_column, key));
    }

    public Long getUserIdByOpenId(Object key) {
        String table = login_table;
        if (StringUtils.isNotEmpty(openid_table)) {
            table = openid_table;
        }
        return getId(getUser(table, openid_table_column, key));
    }

    public void updateOpenIdById(Object userId, Object openId) {
        String table = login_table;
        if (StringUtils.isNotEmpty(openid_table)) {
            table = openid_table;
        }
        IService iService = SpringUtils.getBean(table);
        UpdateWrapper<?> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("id", userId)
                .set(openid_table_column, openId);
        iService.update(updateWrapper);
    }


    /**
     * 获取对象 passwd
     *
     * @param obj
     * @return
     */
    public String password(Object obj) {
        Object value = WrapperFactory.getPassword(obj, "");
        if (value != null) {
            return value.toString();
        } else {
            return "";
        }
    }

    /**
     * 获取 对象的 ID
     *
     * @param obj
     * @return
     */
    public Long getId(Object obj) {
        if (obj != null) {
            Object id = WrapperFactory.getTableModelId(obj);
            if (id instanceof Integer) {
                return ((Integer) id).longValue();
            } else {
                return (Long) id;
            }
        } else {
            return null;
        }
    }


    private Object getUser(String table, String key, Object value) {
        IService iService = SpringUtils.getBean(table);
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(key, value);
        return iService.getOne(queryWrapper);
    }


}
