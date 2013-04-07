package com.dbrown.dev.neat.invasion.graphics;

public class Sprite {
	
	public final int SIZE;
	private int x,y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	public static Sprite space0 = new Sprite(32, 0, 0, SpriteSheet.tiles);
	public static Sprite space1 = new Sprite(32, 1, 0, SpriteSheet.tiles);
	public static Sprite space2 = new Sprite(32, 1, 1, SpriteSheet.tiles);
	public static Sprite space3 = new Sprite(32, 0, 1, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(32, 0x8506A9);
	
	public static Sprite idle = new Sprite(32, 0, 0, SpriteSheet.ship);
	public static Sprite right = new Sprite(32, 2, 0, SpriteSheet.ship);
	public static Sprite left = new Sprite(32, 1, 0, SpriteSheet.ship);
	public static Sprite stop = new Sprite(32, 0, 2, SpriteSheet.ship);
	
	public static Sprite idle1 = new Sprite(32, 0, 1, SpriteSheet.ship);
	public static Sprite right1 = new Sprite(32, 2, 1, SpriteSheet.ship);
	public static Sprite left1 = new Sprite(32, 1, 1, SpriteSheet.ship);
	public static Sprite stop1 = new Sprite(32, 1, 2, SpriteSheet.ship);
	
	public static Sprite laser = new Sprite(8, 0, 0, SpriteSheet.bullet);
	
	public static Sprite enemy = new Sprite(32, 0, 0, SpriteSheet.enemy);
	/*
	public static Sprite guiULC = new Sprite(8, 0, 3, SpriteSheet.enemy);
	public static Sprite guiUM = new Sprite(8, 1, 3, SpriteSheet.enemy);
	public static Sprite guiURC = new Sprite(8, 2, 3, SpriteSheet.enemy);
	
	public static Sprite guiL = new Sprite(8, 0, 4, SpriteSheet.enemy);
	public static Sprite guiM = new Sprite(8, 1, 4, SpriteSheet.enemy);
	public static Sprite guiR = new Sprite(8, 2, 4, SpriteSheet.enemy);
	
	public static Sprite guiLLC = new Sprite(8, 0, 5, SpriteSheet.enemy);
	public static Sprite guiLM = new Sprite(8, 1, 5, SpriteSheet.enemy);
	public static Sprite guiLRC = new Sprite(8, 2, 5, SpriteSheet.enemy);
	*/
	
	public static Sprite zero = new Sprite(8, 0, 2, SpriteSheet.gui);
	public static Sprite one = new Sprite(8, 1, 2, SpriteSheet.gui);
	public static Sprite two = new Sprite(8, 2, 2, SpriteSheet.gui);
	public static Sprite three = new Sprite(8, 3, 2, SpriteSheet.gui);
	public static Sprite four = new Sprite(8, 4, 2, SpriteSheet.gui);
	public static Sprite five = new Sprite(8, 5, 2, SpriteSheet.gui);
	public static Sprite six = new Sprite(8, 6, 2, SpriteSheet.gui);
	public static Sprite seven = new Sprite(8, 7, 2, SpriteSheet.gui);
	public static Sprite eight = new Sprite(8, 8, 2, SpriteSheet.gui);
	public static Sprite nine = new Sprite(8, 9, 2, SpriteSheet.gui);
	
	public static Sprite counter = new Sprite(8, 3, 3, SpriteSheet.gui);
	
	public static Sprite L1 = new Sprite(64,0,0, SpriteSheet.title);
	public static Sprite L2 = new Sprite(64,1,0, SpriteSheet.title);
	public static Sprite L3 = new Sprite(64,2,0, SpriteSheet.title);
	public static Sprite L4 = new Sprite(64,3,0, SpriteSheet.title);
	
	public static Sprite cursor = new Sprite(8,0,8, SpriteSheet.title);
	
	public static Sprite start1 = new Sprite(16,0,5, SpriteSheet.title);
	public static Sprite start2 = new Sprite(16,1,5, SpriteSheet.title);
	public static Sprite start3 = new Sprite(16,2,5, SpriteSheet.title);
	
	public static Sprite op1 = new Sprite(16,0,6, SpriteSheet.title);
	public static Sprite op2 = new Sprite(16,1,6, SpriteSheet.title);
	public static Sprite op3 = new Sprite(16,2,6, SpriteSheet.title);
	public static Sprite op4 = new Sprite(16,3,6, SpriteSheet.title);
	
	public static Sprite edit1 = new Sprite(16,0,7, SpriteSheet.title);
	public static Sprite edit2 = new Sprite(16,1,7, SpriteSheet.title);
	
	public static Sprite exit1 = new Sprite(16,0,8, SpriteSheet.title);
	public static Sprite exit2 = new Sprite(16,1,8, SpriteSheet.title);

	

	
	public Sprite(int size, int color){
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	public Sprite(int size, int x, int y, SpriteSheet sheet){
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	private void setColor(int color){
		for (int i = 0; i < SIZE*SIZE; i++){
			pixels[i] = color;
		}
	}
	
	private void load(){
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
		
	}
	

}
