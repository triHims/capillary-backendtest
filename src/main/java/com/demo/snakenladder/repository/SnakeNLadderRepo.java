package com.demo.snakenladder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.snakenladder.Entity.PlayerObj;

public interface SnakeNLadderRepo extends JpaRepository<PlayerObj,String> {
		Optional<PlayerObj> getByPlayerName(String playername);
}
