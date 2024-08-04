package com.qs.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qs.pojo.Book;
import com.qs.service.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("book")
public class BookController {
    @Value("${location}")
    private String location;

    @Autowired
    private BookService bookService;

    @RequestMapping("listBook")
    public String listBook(@RequestParam(required = false, value = "pageNum", defaultValue = "1") Integer pageNum,

                           @RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize, Model model, Book book) {
//        这两个检查是多余的，因为@RequestParam已经处理了默认值
//        if (pageNum < 0 || pageNum.equals("") || pageNum == null) {
//            pageNum = 1;
//        }
//        if (pageSize < 0 || pageSize.equals("") || pageSize == null) {
//            pageSize = 10;
//        }

        PageHelper.startPage(pageNum, pageSize);

        // 构建查询条件
        QueryWrapper<Book> qw = new QueryWrapper<>();
        if (book.getBname() != null) {
            qw.like("bname", book.getBname());
        }

        List<Book> list = bookService.list(qw);
        PageInfo<Book> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        return "bookList";
    }


    @RequestMapping("preSaveBook")
    public String preSaveBook(){
        return  "bookSave";
    }

    @RequestMapping("saveBook")
    public String saveBook(Book book, MultipartFile file){
        transFile(book,file);
        bookService.save(book);
        return "redirect:/book/listBook";
    }

    private void transFile(Book book, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix=originalFilename.substring(index);
        String prefix=System.nanoTime()+"";
        String path=prefix+suffix;
        File file1=new File(location);
        if (!file1.exists()){
            file1.mkdir();
        }
        File file2 = new File(file1, path);
        try {
            file.transferTo(file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        book.setBimage(path);
    }


    @RequestMapping("delbook/{id}")
    public String delbook(@PathVariable Integer id){
        boolean b = bookService.removeById(id);
        return "redirect:/book/listBook";
    }


    @RequestMapping("preUpdateBook/{id}")
    public String preUpdateBook(@PathVariable Integer id,Model model){
        Book book = bookService.getById(id);
        model.addAttribute("book",book);
        return "bookUpdate";
    }

    @RequestMapping("updateBook")
    public String updateBook(Book book){
        boolean b = bookService.updateById(book);
        return  "redirect:/book/listBook";
    }


    @RequestMapping("batchDeleteBook")
    @ResponseBody  //返回JSON类数据
    public String batchDeleteBook(String idList){
        String[] split = StrUtil.split(idList, ",");
        List<Integer> list=new ArrayList<>();
        for (String s : split) {
            if (!s.isEmpty()){
                int i = Integer.parseInt(s);
                list.add(i);
            }
        }
        boolean b = bookService.removeByIds(list);
        if (b){
            return "OK";
        }else {
            return "error";
        }
    }


}