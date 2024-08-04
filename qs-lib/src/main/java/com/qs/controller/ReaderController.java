package com.qs.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qs.pojo.Reader;
import com.qs.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("reader")
public class ReaderController {
    @Value("${location}")
    private String location;

    @Autowired
    private ReaderService readerService;

    @RequestMapping("listReader")
    public String listReader(@RequestParam(required = false, value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize, Model model, Reader reader) {

        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Reader> qw = new QueryWrapper<>();
        if (reader.getRname() != null) {
            qw.like("rname", reader.getRname());
        }

        List<Reader> list = readerService.list(qw);
        PageInfo<Reader> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);

        return "readerList";


    }

    @RequestMapping("preSaveReader")
    public String preSaveReader() {
        return "readerSave";
    }

    @RequestMapping("saveReader")
    private String saveReader(Reader reader, MultipartFile file) {
        transFile(reader, file);
        readerService.save(reader);
        return "redirect:/reader/listReader";

    }

    private void transFile(Reader reader, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String prefix = System.nanoTime() + "";
        String path = prefix + suffix;
        File file1 = new File(location);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        File file2 = new File(file1, path);
        try {
            file.transferTo(file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader.setRimage(path);
    }


    @RequestMapping("delReader/{id}")
    public String delReader(@PathVariable Integer id) {
        boolean b = readerService.removeById(id);
        return "redirect:/reader/listReader";

    }

    @RequestMapping("preUpdateReader/{id}")
    public String preUpdateReader(@PathVariable Integer id, Model model) {
        Reader reader = readerService.getById(id);
        model.addAttribute("reader", reader);
        return "readerUpdate";
    }

    @RequestMapping("updateReader")
    public String updateReader(Reader reader) {
        boolean update = readerService.updateById(reader);
        return "redirect:/reader/listReader";
    }

    @RequestMapping("batchDeleteReader")
    @ResponseBody
    public String batchDeleteReader(String idList) {
        String[] split = StrUtil.split(idList, ",");
        List<Integer> list = new ArrayList<>();
        for (String s : split) {
            if (!s.isEmpty()) {
                int i = Integer.parseInt(s);
                list.add(i);
            }
        }
        boolean b = readerService.removeByIds(list);
        if (b) {
            return "OK";
        } else {
            return "error";
        }

    }
}
