package com.dbrown.dev.neat.invasion.graphics;

import java.awt.Graphics;
import java.util.Random;

import com.dbrown.dev.neat.invasion.entity.Entity;
import com.dbrown.dev.neat.invasion.level.tile.Tile;


public class Screen {
	
	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 8;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int[] tiles = new int[MAP_SIZE*MAP_SIZE];
	
	public int xOffset, yOffset;
	
	public int tileSize = 5;
	
	private Random random = new Random();
	
	public Screen(int width, int height){
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		for (int i = 0; i < MAP_SIZE*MAP_SIZE; i++){
			tiles[i] = random.nextInt(0xffffff);
		}
	}
	
	public void clear(){
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = 0;
		}
		
	}
	
	String[] chars = {
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",
            ".,!?:;\"'+-=/\\< "
	};
	  
	public void drawString(String string, int x, int y) {
	string = string.toUpperCase();
	for (int i=0; i<string.length(); i++) {
	  char ch = string.charAt(i);
	  for (int ys=0; ys<chars.length; ys++) {
	      int xs = chars[ys].indexOf(ch);
	      if (xs>=0) {
	    	  System.out.println();
	          //g.drawImage(Art.guys[xs][ys+9], x+i*8, y, null);
	    	  //renderCharacter();
	      }
	  }
	}
	}
	
	/*public void render(int xOffset, int yOffset){
		
		for(int y = 0; y < height; y++){
			int yp = y + yOffset;
			if(yp < 0 || yp >= height) continue;
			for(int x = 0; x < width; x++){
				int xp = x + xOffset;
				if(xp < 0 || xp >= width) continue;
				pixels[x + y * width] = Sprite.space.pixels[(xp & 31) + (yp & 31) * Sprite.space.SIZE];

			}
		}
	}*/
	
	public void renderTile(int xp, int yp, Tile tile){
		xp -= xOffset;
		yp -= yOffset;
		
		for (int y = 0; y < tile.sprite.SIZE; y++){
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++){
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if ( xa < 0 ) xa = 0;
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}
	
	public void renderCharacter(int xp, int yp, Sprite sprite){
		for (int y = 0; y < 8; y++){
			int ya = y + yp;
			for (int x = 0; x < 8; x++){
				int xa = x + xp;
				if (xa < -8 || xa >= width || ya < 0 || ya >= height) break;
				if ( xa < 0 ) xa = 0;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.SIZE];
			}
		}
	}
	
	public void renderEntity(int xp, int yp, Entity e){
		for (int y = 0; y < e.sprite.SIZE; y++){
			int ya = y + yp;
			for (int x = 0; x < e.sprite.SIZE; x++){
				int xa = x + xp;
				if(xa < -e.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				int col = e.sprite.pixels[x+y*e.sprite.SIZE];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}
	
	
	public void setOffset (int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	
	
	public void renderPlayer(int xp, int yp, Sprite player){
		for(int y = 0; y < 32; y++){
			int ya = y + yp;
			//if(ya < 0 || ya >= height) break;
			for(int x = 0; x < 32; x++){
				int xa = x + xp;
				if(xa < -32 || xa >= width || ya < 0 || ya >= height) break;
				int col = player.pixels[x+y*32];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;

			}
		}
		
	}

}