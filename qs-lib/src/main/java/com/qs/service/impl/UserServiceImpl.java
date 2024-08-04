package com.qs.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qs.mapper.UserMapper;
import com.qs.pojo.User;
import com.qs.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public boolean login(String userName, String userPwd) {
        QueryWrapper<User>qw=new QueryWrapper<>();
        qw.eq("username",userName);
        User user = this.baseMapper.selectOne(qw);
//        System.out.println(user);
        if (user==null){
            return false;
        }
        String s = DigestUtil.md5Hex(userPwd);
//        System.out.println(user.getPassword());
        if (s.equals(user.getPassword())){
            return true;
        }else {
            return false;
        }
    }
}