package com.qs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qs.pojo.Reader;

public interface ReaderService extends IService<Reader> {
    boolean login(String userName,  String userPwd);
}
