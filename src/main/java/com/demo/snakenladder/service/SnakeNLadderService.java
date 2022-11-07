package com.demo.snakenladder.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.snakenladder.Entity.BoardObj;
import com.demo.snakenladder.Entity.PlayerObj;
import com.demo.snakenladder.repository.BoardStoreRepo;
import com.demo.snakenladder.repository.SnakeNLadderRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SnakeNLadderService {
	private static HashMap<Integer, Integer> snakeMapper;
	private static HashMap<Integer, Integer> ladderMapper;

	@Autowired
	private SnakeNLadderRepo snakeNLadderRepo;

	@Autowired
	private BoardStoreRepo boardStoreRepo;

	@Autowired
	ObjectMapper mapper;

	public PlayerObj getPlayerDetails(String playerId) {

		PlayerObj returnObj = snakeNLadderRepo.getByPlayerName(playerId).orElse(new PlayerObj());
		return returnObj;
	}

	public boolean resetPlayer(String playerId) {

		Optional<PlayerObj> returnObj = snakeNLadderRepo.getByPlayerName(playerId);
		if (!returnObj.isPresent())
			return false;

		PlayerObj obj = returnObj.get();

		obj.lastPosition = 1;
		snakeNLadderRepo.save(obj);

		return true;
	}

	public int moveSnake(String playerId, int moveCount) {
		if (snakeMapper == null || ladderMapper == null) {
			fillMappers();
		}
		Optional<PlayerObj> returnObj = snakeNLadderRepo.getByPlayerName(playerId);
		if (!returnObj.isPresent())
			throw new RuntimeException("Player Not found");

		PlayerObj obj = returnObj.get();
		int currentPosition = obj.lastPosition;
		if (currentPosition == 100)
			return 100;

		int newPosition = currentPosition + moveCount;

		// If at new position both ladder and snake exist choose ladder then choose
		// snake if exists;
		int finalPosition = ladderMapper.getOrDefault(newPosition, newPosition);
		finalPosition = snakeMapper.getOrDefault(finalPosition, finalPosition);

		obj.lastPosition = finalPosition;
		snakeNLadderRepo.save(obj);

		return finalPosition;

	}

	void fillMappers() {
		String boardData = boardStoreRepo.findAll().get(0).boardData;
		try {
			Map<String, Object> jsonMap = mapper.readValue(boardData, new TypeReference<Map<String, Object>>() {
			});

			List<List<Integer>> snakeJumpList = (List<List<Integer>>) jsonMap.get("snake");
			// new snakeMapper
			snakeMapper = new HashMap<>();
			for (List<Integer> ls : snakeJumpList) {
				snakeMapper.put(ls.get(0), ls.get(1));
			}

			// map ladder

			List<List<Integer>> ladderJumpList = (List<List<Integer>>) jsonMap.get("ladder");
			ladderMapper = new HashMap<>();
			for (List<Integer> ls : ladderJumpList) {
				ladderMapper.put(ls.get(0), ls.get(1));
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean setLocation(String playerId, Integer location) {
		if (location >= 1 && location <= 100) {
			Optional<PlayerObj> returnObj = snakeNLadderRepo.getByPlayerName(playerId);
			if (!returnObj.isPresent())
				throw new RuntimeException("Player Not found");
			PlayerObj obj = returnObj.get();
			obj.lastPosition = location;
			snakeNLadderRepo.save(obj);

			return true;
		} else {

			throw new RuntimeException("Location error ");
		}
	}
}
