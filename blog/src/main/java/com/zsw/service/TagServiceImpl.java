package com.zsw.service;

import com.zsw.NotFoundException;
import com.zsw.dao.TagRepository;
import com.zsw.po.Tag;
import com.zsw.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{

    private TagRepository tagRepository;
    @Autowired
    private void setTagRepository(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {//根据idlist获取到一组tag对象
        return tagRepository.findAllById(convertToList(ids));
    }
    //字符串转换为数组
    public List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null){//非空判断
            //转化为数组
            String[] idarray = ids.split(",");
            for (int i = 0 ;i< idarray.length;i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }


    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getOne(id);
        if (t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }


    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(getTag(id));

    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blog.size");
        Pageable pageable = PageRequest.of(0,size);
        return tagRepository.findTop(pageable);
    }
}
