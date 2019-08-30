package com.ehr;
/**
 * NextLevel
 * @author sist
 *
 */
public enum Level {
	GOLD(3,null), SILVER(2,Level.GOLD), BASIC(1,Level.SILVER);
	
	private final int value;
	private final Level nextLevel;
	
	Level(int value, Level next){
		this.value = value;
		this.nextLevel = next;
	}
	
	public Level getNextLevel() {
		return nextLevel;
	}
	
	//DB Insert Data
	public int intValue() {
		return value;
	}
	
	public static Level valueOf(int value) {
		switch(value) {
		case 1: return BASIC;
		case 2: return SILVER;
		case 3: return GOLD;
		default: throw new AssertionError("UNKOWN VALUE : "+value);
		}
	}
}
