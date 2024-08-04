package com.qs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.qs.mapper.ReaderBookMapper;
import com.qs.pojo.MainMenu;
import com.qs.pojo.ReaderBook;
import com.qs.service.ReaderBookService;
import com.qs.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderBookServiceImpl extends ServiceImpl<ReaderBookMapper, ReaderBook> implements ReaderBookService{

    @Autowired
    private ReaderBookMapper    readerBookMapper;
    @Override
    public PageInfo<ReaderBook> listAll(String readerName) {
    List<ReaderBook>list=readerBookMapper.listAll(readerName);
    return new PageInfo<>(list);
    }

    @Override
    public List<MainMenu> queryAll() {
        return readerBookMapper.queryAll();
    }

    @Override
    public PageInfo<ReaderBook> myBookList(Integer userId) {
        List<ReaderBook> list=readerBookMapper.myBookList(userId);
        return new PageInfo<>(list);
    }
}
