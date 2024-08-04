package com.qs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qs.mapper.TypeMapper;
import com.qs.pojo.Type;
import com.qs.service.TypeService;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>  implements TypeService {
}
