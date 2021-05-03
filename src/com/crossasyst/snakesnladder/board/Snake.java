package com.crossasyst.snakesnladder.board;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Snake {
	private int head;
	private int tail;
	
	public Snake(int head, int tail) throws MinGreaterThanMaxException
	{
		if(head < tail)
			throw new MinGreaterThanMaxException();
		this.head = head;
		this.tail = tail;
	}
}
