package com.dbrown.dev.neat.invasion.level.tile;

import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;

public class Tile {
	
	public int x, y;
	public Sprite sprite;
	
	public static Tile space_1 = new SpaceTile(Sprite.space0);
	public static Tile space_2 = new SpaceTile(Sprite.space1);
	public static Tile space_3 = new SpaceTile(Sprite.space2);
	public static Tile space_4 = new SpaceTile(Sprite.space3);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	public Tile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen){
	}
	
	public boolean solid(){
		return false;
	}
}
