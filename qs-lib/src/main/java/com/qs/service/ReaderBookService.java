package com.qs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.qs.mapper.ReaderBookMapper;
import com.qs.pojo.MainMenu;
import com.qs.pojo.ReaderBook;

import java.util.List;

public interface ReaderBookService extends IService<ReaderBook>  {
    PageInfo<ReaderBook> listAll(String readerName);

    List<MainMenu> queryAll();

    PageInfo<ReaderBook> myBookList(Integer userId);
}
