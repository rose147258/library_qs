package com.qs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qs.pojo.User;

public interface UserService extends IService<User> {
    boolean login(String userName, String userPwd);
}
