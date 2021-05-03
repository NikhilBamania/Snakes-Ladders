package com.crossasyst.snakesnladder.board;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Ladder {
	private int top;
	private int bottom;
	
	public Ladder(int top, int bottom) throws MinGreaterThanMaxException
	{
		if(top < bottom)
			throw new MinGreaterThanMaxException();
		this.top = top;
		this.bottom = bottom;
	}
}
