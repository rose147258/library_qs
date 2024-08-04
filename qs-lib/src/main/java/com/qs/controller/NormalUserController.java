package com.qs.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qs.pojo.Book;
import com.qs.pojo.Reader;
import com.qs.pojo.ReaderBook;
import com.qs.service.BookService;
import com.qs.service.ReaderBookService;
import com.qs.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("normalUser")
public class NormalUserController {
    @Autowired
    private ReaderBookService readerBookService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReaderService readerService;

    @RequestMapping("myBookList")
    public String myBookList(@RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10", value = "pageSize") Integer pageSize, Model model, HttpSession session) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Book> qw = new QueryWrapper<>();
        Integer userId = (Integer) session.getAttribute("userId");
        PageInfo<ReaderBook> pageInfo = readerBookService.myBookList(userId);
        model.addAttribute("pageInfo", pageInfo);
        return "myBookList";
    }

    @RequestMapping("returnBook/{id}")
    public String returnBook(@PathVariable Integer id, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        ReaderBook readerBook = new ReaderBook();
        readerBook.setStatus(1);
        QueryWrapper<ReaderBook> qw = new QueryWrapper<>();
        qw.eq("rid", userId);
        qw.eq("bid", id);
        boolean update = readerBookService.update(readerBook, qw);
        Book book = bookService.getById(id);
        book.setStock(book.getStock() + 1);
        bookService.updateById(book);
        return "redirect:/normalUser/myBookList";
    }

    @RequestMapping("listBook")
    public String listBook(@RequestParam(required = false, value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize, Model model, Book book) {
        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<Book> qw = new QueryWrapper<>();
        if (book.getBname() != null) {
            qw.like("bname", book.getBname());
        }

        List<Book> list = bookService.list(qw);
        PageInfo<Book> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        return "normalUserBookList";
    }

    @RequestMapping("borrowBook/{id}")
    public String borrowBook(@PathVariable Integer id, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        ReaderBook readerBook = new ReaderBook();
        readerBook.setBid(id);
        readerBook.setRid(userId);
        readerBookService.save(readerBook);
        Book byId = bookService.getById(id);
        byId.setStock(byId.getStock() - 1);
        bookService.updateById(byId);
        return "redirect:/normalUser/myBookList";
    }

    @RequestMapping("recommendBookList")
    public String recommendBookList(@RequestParam(required = false, value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize, Model model, HttpSession session) {
        Integer userId =(Integer) session.getAttribute("userId");
        Reader reader = readerService.getById(userId);
        String type = reader.getType();
        String[] split = StrUtil.split(type, ",");
        List<String>list=new ArrayList<>();
        for (String s : split) {
            if (!s.isEmpty()){
                list.add(s);
            }
        }
        QueryWrapper<Book>qw=new QueryWrapper<>();
        qw.in("type",list);
        List<Book> list1 = bookService.list(qw);
        PageInfo<Book>pageInfo=new PageInfo<>(list1);
        model.addAttribute("pageInfo",pageInfo);
        return "recommendBookList";
    }
}
