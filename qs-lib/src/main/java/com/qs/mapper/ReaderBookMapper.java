package com.qs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qs.pojo.MainMenu;
import com.qs.pojo.ReaderBook;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderBookMapper extends BaseMapper<ReaderBook> {
    List<ReaderBook> listAll(String readerName);

    List<MainMenu> queryAll();

    List<ReaderBook> myBookList(Integer userId);
}
