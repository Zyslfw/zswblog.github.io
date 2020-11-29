package com.zsw.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "t_type")
@Table
public class Type {

    @Id
    @GeneratedValue
    private  long id;//主键
    private String name;

    @OneToMany(mappedBy = "type")//type被维护
    private List<Blog> blogs = new ArrayList<>();

    public Type() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String tname) {
        this.name = tname;
    }


    /*List<Blog> set get*/
    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';


    }
}
