package com.demo.snakenladder.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.snakenladder.Entity.PlayerObj;
import com.demo.snakenladder.service.SnakeNLadderService;

@RestController
@RequestMapping("snakeNladder")
public class BoardController{
	
	@Autowired
	private SnakeNLadderService snakeNLadderService;
	
	@GetMapping(value="/{playerId}")
	@ResponseBody
	public ResponseEntity<PlayerObj> getCurrentPlayer(@PathVariable("playerId") String playerId){
		
		return ResponseEntity.ok(snakeNLadderService.getPlayerDetails(playerId.toLowerCase()));
	}
	@PostMapping(value="/{playerId}")
	public ResponseEntity<String> commandFn(@PathVariable("playerId") String playerId, @RequestBody String body){
		if("START".compareTo(body)==0) {
			return snakeNLadderService.resetPlayer(playerId.toLowerCase())==true?
					ResponseEntity.ok("READY"):ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("ERROR");
			
		}else {
			
			try {
				Integer move = Integer.valueOf(body);
				if(move<0 || move>6) {
					throw new NumberFormatException();
				}
				int position = snakeNLadderService.moveSnake(playerId.toLowerCase(), move);
				String response;
				if(position == 100)
				{
					response= String.format("%s wins",playerId);
				}else {
					response = String.format("next_position: %d", position);
				}
				
				 
				return ResponseEntity.ok(response);
			} catch(NumberFormatException ex) {
				String response = String.format("The body : %s \n Is Invalid",body);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}
	}
	
}