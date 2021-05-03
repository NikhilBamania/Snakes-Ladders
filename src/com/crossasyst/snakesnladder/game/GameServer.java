package com.crossasyst.snakesnladder.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.crossasyst.snakesnladder.Logger.GameLogger;
import com.crossasyst.snakesnladder.player.Player;
import com.crossasyst.snakesnladder.room.Room;


public class GameServer {
	
	private Map<Room, Boolean> roomsTracker = null;
	private Scanner sc = null;
	private GameLogger logger = null;
	private String log;
	
	public GameServer()
	{
		logger = GameLogger.getGameLogger();
		roomsTracker = new HashMap<Room, Boolean>();
		sc = new Scanner(System.in);
		
	}

	public void startServer() {

		boolean gameRunner = true;

		while (gameRunner)
		{
			log = "1. Register Player \t 2. Exit";
			logger.log("Debug", log);
			
			int in = sc.nextInt();
			log = "input : " + in;
			logger.log("Debug", log);
			
			switch (in) {
			case 1:
				break;
				
			case 2: 
				exit();
				return;
			default:
				log = "Please enter valid options - '1' or '2'";
				logger.log("Warn", log);
			}
			
			log = "Enter your name: ";
			logger.log("Debug", log);
			
			String name = sc.next();
			logger.log("Debug", "input : " + name);
			
			Player player = new Player(name);
			log = player.getName() + " Registered";
			logger.log("Trace", log);

			log = "1. Create Room \t 2. Join Room \t 3. Exit";
			logger.log("Debug", log);
			
			in = sc.nextInt();
			logger.log("Debug", "Input : "+in);

			switch (in) {
			case 1: // create room
				Room room = new Room(player);
				roomsTracker.put(room, true);
				Runnable runnable = () -> room.start();
				Thread thread = new Thread(runnable);
				thread.start();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					logger.log("Error", e.getMessage());
				}
				break;

			case 2: // join
				boolean roomFound = false;
				logger.log("Debug" ,"Enter the Room ID : ");
				
				int userRoomId = sc.nextInt();
				logger.log("Debug", "Input : " + userRoomId);
				
				for (Map.Entry<Room, Boolean> entry : roomsTracker.entrySet())
				{
					int roomId = entry.getKey().getRoomId();
					if (roomId == userRoomId)
					{
						roomFound = true;
						if (entry.getValue())
						{	
							entry.getKey().add(player);
							log = "1. Start Game \t 2. Continue";
							logger.log("Debug", log);
							
							in = sc.nextInt();
							logger.log("Debug", "Input : "+log);
							
							switch (in) {
							case 1:
								roomsTracker.put(entry.getKey(), false);
								entry.getKey().setPlay(true);
								try {
									Thread.sleep(7000);
								} catch (InterruptedException e) {
									logger.log("Error", e.getMessage());
								}
								break;
								
							case 2:
								break;

							default:
								logger.log("Warn", "Please enter valid options - '1' or '2'");
							}
							break;
						}
						logger.log("Warn", "Sorry! Game already started");

					}
				}
				if(!roomFound)
					logger.log("Warn", "No Room Found");
				break;

			case 3: // exit
				exit();
				return;

			default:
				logger.log("Warn", "Please enter valid options - '1' or '2' or '3'");
			}

			log = "Open rooms :";
			for (Map.Entry<Room, Boolean> entry : roomsTracker.entrySet()) {
				if (entry.getValue() == true)
					log += (" " + entry.getKey().getRoomId());
			logger.log("Debug", log);
			}
		}
	}

	private void exit() {
		logger.log("Trace", "Program Ended");
		if (roomsTracker != null)
			roomsTracker = null;
		sc.close();
	}

}
