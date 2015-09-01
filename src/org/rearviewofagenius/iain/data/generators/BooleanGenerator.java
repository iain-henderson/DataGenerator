package org.rearviewofagenius.iain.data.generators;

public class BooleanGenerator extends Generator {
	public BooleanGenerator(Integer rows){
		super(Integer.valueOf(queueDepth), rows);
	}

	public String generate(){
		return Boolean.toString(nextBoolean());
	}

	public String toString(){
		return "Boolean Generator";
	}
}
