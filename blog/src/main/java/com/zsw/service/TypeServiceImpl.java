package com.zsw.service;

import com.zsw.NotFoundException;
import com.zsw.dao.TypeRepository;
import com.zsw.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{

    //注入
    private TypeRepository typeRepository;
    @Autowired
    private void setTypeRepository(TypeRepository typeRepository){
        this.typeRepository =typeRepository;

    }

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }
    @Transactional
    @Override
    public Type getType(Long id) {
        /*return typeRepository.findOne(id)报错*/
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }
/*type按数目（List<blog>）大小由高到低排序*/
    @Override
    public List<Type> listTypeTop(Integer size) {
        /*Sort sort = new Sort(Sort.Direction.DESC,"blog.size");这里的写法已不再支持*/
        Sort sort = Sort.by(Sort.Direction.DESC,"blog.size");
        Pageable pageable = PageRequest.of(0,size);
        return typeRepository.findTop(pageable);
    }


    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getOne(id);
        if (t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);

        return typeRepository.save(t);
    }


    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.delete(getType(id));

    }
}
