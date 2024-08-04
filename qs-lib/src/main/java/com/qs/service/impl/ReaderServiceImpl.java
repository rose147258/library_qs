package com.qs.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qs.mapper.ReadMapper;
import com.qs.pojo.Reader;
import com.qs.pojo.User;
import com.qs.service.ReaderService;
import org.springframework.stereotype.Service;

@Service
public class ReaderServiceImpl extends ServiceImpl<ReadMapper, Reader> implements ReaderService {

    @Override
    public boolean login(String userName, String userPwd) {
        QueryWrapper<Reader> qw=new QueryWrapper<>();
        qw.eq("rname",userName);
        Reader reader = this.baseMapper.selectOne(qw);
        if (reader==null){
            return false;
        }
        String s = DigestUtil.md5Hex(userPwd);
        if (s.equals(reader.getPassword())){
            return true;
        }else {
            return false;
        }
    }
}
