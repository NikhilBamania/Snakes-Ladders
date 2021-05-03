package com.crossasyst.snakesnladder.board;

import java.util.HashSet;
import java.util.Set;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UniqueNumberGenerator {
	
	private Set<Integer> container = new HashSet<Integer>();
	
	public int getNumber()
	{
		int number;
		do {
			number = (int)(Math.random() * 100);
			number %= 100;
		}while(container.contains(number));
		container.add(number);
		return number;
	}
	
	public void remove(int number)
	{
		container.remove(number);
	}
	
	public void close()
	{
		if(container != null)
		{
			container.clear();
			container = null;
		}
	}

}
