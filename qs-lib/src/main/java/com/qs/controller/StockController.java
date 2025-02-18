package com.qs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qs.pojo.Book;
import com.qs.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("stock")
public class StockController {
    @Autowired
    private BookService bookService;

    @RequestMapping("listStock")
    public String listStock(@RequestParam(required = false,value = "pageNum",defaultValue = "1")Integer pageNum,
                            @RequestParam(required = false,value = "pageSize",defaultValue = "10")Integer pageSize, Model model, Book book){
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Book> qw=new QueryWrapper<>();
        if (book.getBname()!=null){
            qw.like("bname",book.getBname());
        }
        List<Book> list=bookService.list(qw);
        PageInfo<Book> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "stockList";

    }

    @RequestMapping("updateStock")
    @ResponseBody
    public String updateStock(Integer bookId,Integer stock){
        Book book=new Book();
        book.setId(bookId);
        book.setStock(stock);
        boolean b = bookService.updateById(book);
        if (b){
            return "OK";
        }else {
            return "error";
        }
    }
}
