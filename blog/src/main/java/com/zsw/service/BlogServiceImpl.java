package com.zsw.service;

import com.zsw.NotFoundException;
import com.zsw.dao.BlogRepository;
import com.zsw.po.Blog;
import com.zsw.po.Tag;
import com.zsw.po.Type;
import com.zsw.util.MarkdownUtils;
import com.zsw.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{


    private BlogRepository blogRepository;
    @Autowired
    private void setBlogRepository(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }     /*处理动态查询
            * root--查询对象
            * CriteriaQuery<?>--查询条件容器
            CriteriaBuilder--设置具体条件表达式
            * */
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        if (blog == null){
            throw new  NotFoundException("不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();//获取文章内容
        /*MarkdownUtils.markdownToHtmlExtensions(content);//对内容进行处理*/
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));//将处理后的内容给到content
        /*浏览次数累加*/
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();//组合查询条件判断
                if (!"".equals(blog.getTitle()) && blog.getTitle() !=null){//title非空判断 不为空and不是空字符串
                    predicates.add(cb.like(root.<String>get("title"),"%"+ blog.getTitle()+"%"));
                }

                if (blog.getTypeId()!=null){//type非空判断 不是空字符串and不为空
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));//相等判断
                }
                if (blog.isRecommend()){
                    /*boolean Type argument cannot be of primitive type*/
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));//相等判断

                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));//转成数组
                return null;
            }
        },pageable);
    }
/*关联查询*/
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);//等价于where join.get("id")=tagId
            }
        },pageable);
    }


    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public List<Blog> listNewBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findYearsList();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year : years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }


    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null){//新增博客时
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);//初始浏览次数为0
        }else {
            blog.setUpdateTime(new Date());//不是新增
        }

        return blogRepository.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if (b == null){
            throw new NotFoundException("博客不存在");
        }
        BeanUtils.copyProperties(blog,b);
        return blogRepository.save(b);
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(getBlog(id));

    }
}
