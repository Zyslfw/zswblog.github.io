package com.zsw.dao;

import com.zsw.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag ,Long> {
    @Query("select tg from t_tag tg")
    List<Tag> findTop(Pageable pageable);
}
