package com.dbrown.dev.neat.invasion.level;

import java.util.Random;

import com.dbrown.dev.neat.invasion.Game;
import com.dbrown.dev.neat.invasion.entity.Entity;
import com.dbrown.dev.neat.invasion.entity.mob.Player;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;

public class GameOverLevel extends Level{
	public int tick;
	public int menu;

	public GameOverLevel(int width, int height, Player p) {
		super(width, height, p, true);
		tick = 0;
		menu = 0;
	}

	protected void generateLevel(){
		Random r = new Random();
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				tiles[x + y * width] = r.nextInt(40);
			}
		}
	}
	
	public void update(){
	}
	
	public void render(int xScroll, int yScroll, Screen screen){
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 5;
		int x1 = (xScroll + screen.width + 6) >> 5;
		int y0 = yScroll >> 5;
		int y1 = (yScroll + screen.height + 32) >> 5;
		for (int y = y0; y < y1; y++){
			for (int x = x0; x < x1; x++){
				getTile(x,y).render(x, y, screen);
			}
		}
		//Game Over
		screen.renderCharacter(Game.width/2-35+0, Game.height/2, Sprite.game1);
		screen.renderCharacter(Game.width/2-35+16, Game.height/2, Sprite.game2);
		screen.renderCharacter(Game.width/2-35+32, Game.height/2, Sprite.game3);
		screen.renderCharacter(Game.width/2-35+48, Game.height/2, Sprite.game4);
		screen.renderCharacter(Game.width/2-35+64, Game.height/2, Sprite.game5);
		
		for (int i = entities.size() - 1; i >= 0; i--) {
            Entity e = entities.get(i);
            e.render(screen);
        }
		
	}
	
	
	

}