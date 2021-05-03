package com.crossasyst.snakesnladder.game;

/*import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.crossasyst.snakesnladder.player.Player;
import com.crossasyst.snakesnladder.room.Room;*/

public class Start {
	
	public static void main(String[] args) {
		//1st player creates room and has gameid = id
		//rest players joins room according to gameid created by 1st person
		
		/*
		 * Player p1 = new Player("P1"); Player p2 = new Player("P2"); Room room = new
		 * Room(p1); room.add(p2); Runnable runnable = () -> room.start(); new
		 * Thread(runnable).start();
		 * 
		 * if(p1 != null) p1 = null; if(p2 != null) p2 = null;
		 */
		
		
		
		//for multiple rooms
		GameServer server = new GameServer();
		Runnable r = () -> server.startServer();
		r.run();
		if(r!=null)
			r=null;
		System.gc();
	}

}
