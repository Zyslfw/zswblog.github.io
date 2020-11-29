package com.zsw.service;

import com.zsw.po.Blog;
import com.zsw.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);//根据id查询
    Blog getAndConvert(Long id);//获取并转换，用于前端的文档展示
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);//分页查询
    Page<Blog> listBlog(Long tagId, Pageable pageable);//根据tagId进行分页查询
    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlog(String query, Pageable pageable);//搜索表单参数query
    List<Blog> listNewBlogTop(Integer size);//最新博客内容
    Map<String,List<Blog>> archiveBlog();
    Long countBlog();
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);


}
