package com.crossasyst.snakesnladder.board;

public class SnakeAndLadderCreator {

	private int snakeHead;
	private int snakeTail;
	private int ladderTop;
	private int ladderBottom;
	
	//max should range from 5-98
	private final int upperMax = 98;	
	private final int lowerMax = 14;
	
	//min should range from 3-95
	private final int upperMin = 87;
	private final int lowerMin = 3;
	
	private final int maxDifference = 75;
	private final int minDifference = 10;
	
	UniqueNumberGenerator numberGenerator = null;
	
	public SnakeAndLadderCreator(UniqueNumberGenerator numberGenerator)
	{
		this.numberGenerator = numberGenerator;
	}
	
	public void create()
	{	
		//initialize snakeHead
		do {
			snakeHead = numberGenerator.getNumber();
		}while(isAbove(snakeHead));
		
		//initialize snakeTail
		do {
			snakeTail = numberGenerator.getNumber();
		}while(isBelow(snakeTail) || minMaxChecker(snakeHead, snakeTail));
		
		//initialize ladderTop
		do {
			ladderTop = numberGenerator.getNumber();
		}while(isAbove(ladderTop));
		
		//initialize snakeTail
		do {
			ladderBottom = numberGenerator.getNumber();
		}while(isBelow(ladderBottom) || minMaxChecker(ladderTop, ladderBottom));
		
	}
	
	private boolean minMaxChecker(int max, int min) {
		if(max < min || (max-min < minDifference) || (max-min > maxDifference))
		{
			numberGenerator.remove(min);
			return true;
		}
		return false;
	}

	private boolean isAbove(int number)
	{
		if(number > upperMax || number < lowerMax)
		{
			numberGenerator.remove(number);
			return true;
		}
		return false;
	}
	
	private boolean isBelow(int number)
	{
		if(number > upperMin || number < lowerMin)
		{
			numberGenerator.remove(number);
			return true;
		}
		return false;
	}
	
	public int getSnakeHead() {
		return snakeHead;
	}

	public int getSnakeTail() {
		return snakeTail;
	}

	public int getLadderTop() {
		return ladderTop;
	}

	public int getLadderBottom() {
		return ladderBottom;
	}
	
	public void close()
	{
		if(numberGenerator != null)
		{
			numberGenerator.close();
			numberGenerator = null;
		}
	}

}
