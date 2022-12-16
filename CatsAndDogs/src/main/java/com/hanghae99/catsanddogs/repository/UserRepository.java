package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
