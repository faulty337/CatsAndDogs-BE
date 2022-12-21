package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.Comment;
import com.hanghae99.catsanddogs.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying
    @Query("delete from Comment c where c.id in :ids")
    void deleteAllByIdIn(@Param("ids") List<Long> ids);


}
