package com.dbrown.dev.neat.invasion.level;

import java.util.Random;

public class RandomLevel extends Level{
	
	

	public RandomLevel(int width, int height) {
		super(width, height);
		
	}
	
	protected void generateLevel(){
		Random r = new Random();
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				//System.out.println("X: Y: WIDTH: " + x+y*width);
				tiles[x + y * width] = r.nextInt(40);
			}
		}
	}

}
