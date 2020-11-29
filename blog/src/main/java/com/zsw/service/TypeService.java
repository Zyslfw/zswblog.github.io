package com.zsw.service;

import com.zsw.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();//获取到所有的types

    List<Type> listTypeTop(Integer size);//标签列表从大到小排序

    Type updateType(Long id,Type type);

    void deleteType(Long id);
}
