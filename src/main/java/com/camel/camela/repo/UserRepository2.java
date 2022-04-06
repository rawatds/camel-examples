package com.camel.camela.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camel.camela.entity.User;

public interface UserRepository2 extends JpaRepository<User, Long>{

}
