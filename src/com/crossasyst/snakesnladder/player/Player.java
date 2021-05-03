package com.crossasyst.snakesnladder.player;

import lombok.Getter;

@Getter
public class Player {
	private int id;
	private String name;
	private int position;
	
	public Player(String name)
	{
		this.name = name;
		this.position = -1;
		IDGenerator idGenerator = IDGenerator.getIDGenerator();
		this.id = idGenerator.generateID(name);
		if(idGenerator != null)
			idGenerator = null;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
}
