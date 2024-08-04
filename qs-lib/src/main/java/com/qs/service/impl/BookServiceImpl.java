package com.qs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qs.mapper.BookMapper;
import com.qs.pojo.Book;
import com.qs.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    @Autowired
    private BookMapper bookMapper;

    public List<Book> selectBooks(int pageNum, int pageSize, Book book){
//        PageHelper.startPage(pageNum, pageSize);
//        QueryWrapper<Book> qw = new QueryWrapper<>();
//        System.out.println(book);
//        if (book.getBname()!=null){
//            qw.like("bname",book.getBname());
//        }
//        Page<Book> page = new Page<>(pageNum, pageSize);
//        IPage<Book> bookIPage = bookMapper.selectPage(page, qw);
//        return bookIPage.getRecords();

        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<Book> qw = new QueryWrapper<>();
        if (book.getBname() != null) {
            qw.like("bname", book.getBname());
        }

        // 使用 baseMapper 的 selectList 方法，捕获结果并包装到 PageInfo 中
        List<Book> books = bookMapper.selectList(qw);
        PageInfo<Book> pageInfo = new PageInfo<>(books);

        return pageInfo.getList();
    }
}
