package com.demo.snakenladder.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.snakenladder.Entity.BoardObj;
public interface BoardStoreRepo extends JpaRepository<BoardObj,Integer> {
	
}