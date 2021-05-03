package com.crossasyst.snakesnladder.dice;

import java.util.Random;

public class Dice {
	private static Dice dice = null;
	
	public static Dice getDice()
	{
		if(dice == null)
			dice = new Dice();
		return dice;
	}
	
	public int roll()
	{
		Random random = new Random();
		int number = random.nextInt(6)+1;
		return number;
	}
	
	public void close()
	{
		if(dice != null)
			dice = null;
	}

}
