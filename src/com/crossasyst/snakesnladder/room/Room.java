package com.crossasyst.snakesnladder.room;

import java.util.ArrayList;
import java.util.List;

import com.crossasyst.snakesnladder.Logger.GameLogger;
import com.crossasyst.snakesnladder.board.Board;
import com.crossasyst.snakesnladder.board.Ladder;
import com.crossasyst.snakesnladder.board.Snake;
import com.crossasyst.snakesnladder.dice.Dice;
import com.crossasyst.snakesnladder.player.Player;

public class Room {
	private int roomId = 0;

	private List<Player> players = null;		//order of Players
	//private List<String>  moveLog = new ArrayList<String>();		//play log
	private Board board = null;
	private Dice dice = null;
	private Boolean finish = false;
	private Boolean snakeBite = false;
	private Boolean play = false;
	private GameLogger logger = null;
	private String log;
	
	
	//creating room object
	public Room(Player player)
	{
		logger = GameLogger.getGameLogger();
		players = new ArrayList<Player>();
		this.roomId = player.getId();
		add(player);
		
		//logging
		log = player.getName() + " : Created Room " + roomId;
		logger.log("Trace", log);
	}
	
	//add players to the room to play together
	public void add(Player player)
	{
		this.players.add(player);
		
		//logging
		if(players.size()>1)
		{
			log = player.getName() + " : Entered in Room " + roomId;
			logger.log("Trace", log);;
		}
	}
	
	//call this method to start the game
	public void start()
	{
		log = "Waiting Inside Room " + this.getRoomId();
		logger.log("Info", log);
		synchronized (play) {
			while(!play)
			{
				try {
					play.wait(5000);
				} catch (InterruptedException e) {
					logger.log("Error", e.getMessage());
				}
				
			}
		}
		
		if(players.size() < 2)
		{
			log = "Not Enough Players :: Exiting Room" + roomId;
			logger.log("Warn", log);
			close();
			return;
		}
		
		board = new Board();
		logger.log("Trace", "Board Created");
		
		dice = Dice.getDice();
		logger.log("Trace", "Dice Created");
		
		play();

		close();
		
	}

	private void play()
	{

		Ladder[] ladders = board.getLadders();
		Snake[] snakes = board.getSnakes();
		do
		{
			for(Player player : players)
			{
				int total = 0;
				int roll = 0;
				List<Integer> steps = new ArrayList<>();
				
				int count6 = 0;
				boolean changePosition = false;

				steps.clear();
				
				for(; count6<3; count6++)
				{
					changePosition = false;
					roll = dice.roll();
					steps.add(roll);
					total += roll;
					
					log = player.getName() + " : " + String.valueOf(roll);
					logger.log("Info", log);
					
					//getting of the blocks
					if(player.getPosition() == -1)
					{
						if(roll != 6)
						{
							count6 = 2;
							
							log = player.getName() + " : waiting for 6";
							logger.log("Info", log);
							
							continue;
						}
						// getting player to Start Position
						else if(roll == 6)
						{
							log = player.getName() + " : Allowed to Begin from 0";
							logger.log("Info", log);
							player.setPosition(board.getStart());
							steps.clear();
							total = 0;
							continue;
						}
					}
					
					//check for 3 6's in a row
					if(steps.size()==3 && roll == 6)
					{
						log = player.getName() + " : turn Forfited 3 6's in a row";
						logger.log("Info", log);
						break;
					}
					
					if(roll != 6)
					{
						count6 = 2;
						changePosition = true;
					}
					
					
					  //Checking Snakes And Ladders
					for(int prop=0; prop<5; prop++)
					{ 
						//Checking Ladder
						Ladder ladder = ladders[prop];
						if(ladder.getBottom() == (player.getPosition() + total))
						{
							changePosition(player, steps);
							steps.clear();
							
							log = player.getName() +" found Ladder at " + ladder.getBottom() + " to " + ladder.getTop();
							logger.log("INFO", log);
							steps.add(ladder.getTop() - player.getPosition());
							changePosition(player, steps);
							
							count6 = 0;
							steps.clear();
							changePosition = false;
							total = 0;
							break;
						}
					  
						//Checking Snake
						Snake snake = snakes[prop];
						if(snake.getHead() == (player.getPosition() + total))
						{
							changePosition(player, steps);
							steps.clear();
							
							log = player.getName() +" found Snake at " + snake.getHead() + " to " + snake.getTail();
							logger.log("INFO", log);
							steps.add(player.getPosition() - snake.getTail());
							
							snakeBite = true;
							
							changePosition(player, steps);
							
							snakeBite = false;
							steps.clear();
							count6 = 2;
							changePosition = false;
							total = 0;
							break;
						}
					}
				}
				if(changePosition)
					changePosition(player, steps);
				
				if(finish)
					return;
			}
		}while(!finish);
	}
	
	private void changePosition(Player player, List<Integer> steps)
	{
		for(int roll : steps)
		{
			if(snakeBite || ((player.getPosition() + roll) <= 100))
			{
				log = " moved from " + player.getPosition();
				
				if(snakeBite)
				{
					player.setPosition(player.getPosition() - roll);
					snakeBite = false;
				}
				else
					player.setPosition(player.getPosition() + roll);
				
				log += " to " + player.getPosition();
				log = player.getName() + " : " + log;
				logger.log("Debug", log);
				
				if(!snakeBite && checkWin(player))
				{
					log = "Existing Room " + roomId;
					logger.log("Trace", log);
					finish = true;
					return;
				}
			}
		}
	}

	private boolean checkWin(Player player) {
		if(player.getPosition() == 100)
		{
			log = player.getName() + " Won";
			logger.log("Fatal", log);
			return true;
		}
		return false;
	}
	

	public int getRoomId() {
		return roomId;
	}

	public void setPlay(Boolean play) {
		synchronized (play)
		{
			log = "Starting room "+ this.getRoomId();
			logger.log("Trace", log);
			this.play = play;
			play.notifyAll();
		}
		
	}
	
	private void close() {
		//close
		if(players != null)
		{
			for(Player player : players)
				if(player != null)
					player = null;
			players = null;
		}
			
		/*
		 * if(moveLog != null) { moveLog.clear(); moveLog = null; }
		 */
		
		if(board != null)
		{
			board.close();
			board = null;
		}
		if(dice != null)
		{
			dice.close();
			dice = null;
		}
		System.gc();
	}
}
