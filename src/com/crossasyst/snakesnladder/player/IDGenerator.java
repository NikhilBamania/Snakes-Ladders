package com.crossasyst.snakesnladder.player;

import java.util.HashSet;
import java.util.Set;

//Singleton class
public class IDGenerator {
	
	private Set<Integer> idContainer = new HashSet<Integer>();
	private static IDGenerator iDGenerator = null;
	
	public static IDGenerator getIDGenerator()
	{
		if(iDGenerator == null)
			iDGenerator = new IDGenerator();
		return iDGenerator;
	}
	
 	synchronized public int generateID(String name)
	{
		int id = name.hashCode();
		while(idContainer.contains(id))
			id++;
		idContainer.add(id);
		return id;
	}

}
