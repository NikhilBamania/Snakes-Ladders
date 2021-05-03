package com.crossasyst.snakesnladder.board;

import lombok.Getter;

@Getter
public class Board {
	private Snake [] snakes = null;
	private Ladder [] ladders = null;
	private final int start = 0;
	private final int destination = 100;
	
	public Board()
	{
		snakes = new Snake[5];
		ladders = new Ladder[5];
		UniqueNumberGenerator numberGenerator = new UniqueNumberGenerator();
		SnakeAndLadderCreator createSnakeNLadder = new SnakeAndLadderCreator(numberGenerator);
		for(int i=0; i<5; i++)
		{
			createSnakeNLadder.create();
			
			int snakeHead = createSnakeNLadder.getSnakeHead();
			int snakeTail = createSnakeNLadder.getSnakeTail();
			int ladderTop = createSnakeNLadder.getLadderTop();
			int ladderBottom = createSnakeNLadder.getLadderBottom();
			try {
				snakes[i] = new Snake(snakeHead, snakeTail);
				ladders[i] = new Ladder(ladderTop, ladderBottom);
			} catch (MinGreaterThanMaxException e) {
				e.printStackTrace();
			}
		}

		if(createSnakeNLadder != null)
		{
			createSnakeNLadder.close();
			createSnakeNLadder = null;
		}
		if(numberGenerator != null)
		{
			numberGenerator.close();
			numberGenerator = null;
		}
	}
	
	public void close()
	{
		if(snakes != null)
		{
			for(Snake snake : snakes)
				if(snake != null)
					snake = null;
			snakes = null;
		}
		if(ladders != null)
		{
			for(Ladder ladder : ladders)
				if(ladder != null)
					ladder = null;
			ladders = null;
		}
	}

}
