package com.dfoucs.gateway.service;

import com.dfoucs.gateway.vo.User;
import org.springframework.stereotype.Service;


public interface UserService {

    /**
     * 新增一个用户
     * @param name
     * @param age
     */
    void create(String name, Integer age);
    /**
     * 根据name删除一个用户高
     * @param name
     */
    void deleteByName(String name);
    /**
     * 获取用户总量
     */
    Integer getAllUsers();
    /**
     * 删除所有用户
     */
    void deleteAllUsers();

    User login(String username, String password);
}
