package com.spring.butch.repository;

import com.spring.butch.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<PostEntity, Long> {

}
