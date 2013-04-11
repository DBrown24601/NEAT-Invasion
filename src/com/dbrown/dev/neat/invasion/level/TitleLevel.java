package com.dbrown.dev.neat.invasion.level;

import java.util.Random;

import com.dbrown.dev.neat.invasion.Game;
import com.dbrown.dev.neat.invasion.entity.Entity;
import com.dbrown.dev.neat.invasion.entity.mob.Player;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;


public class TitleLevel extends Level{
	public int tick;
	public int menu;

	public TitleLevel(int width, int height, Player p) {
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
		tick++;
		if(tick > 500) tick = 0;
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
		//Logo
		screen.renderCharacter(width*1/6*2, height*1/2*3 - 3 - (int) (Math.abs(Math.sin(tick * 0.1) * 5)), Sprite.L1);
		screen.renderCharacter(width*1/6*2+(64*1), height*1/2*3 - 3 - (int) (Math.abs(Math.sin(tick * 0.1) * 5)), Sprite.L2);
		screen.renderCharacter(width*1/6*2+(64*2), height*1/2*3 - 3 - (int) (Math.abs(Math.sin(tick * 0.1) * 5)), Sprite.L3);
		screen.renderCharacter(width*1/6*2+(64*3), height*1/2*3 - 3 - (int) (Math.abs(Math.sin(tick * 0.1) * 5)), Sprite.L4);
		
		//Menu options
		//Start
		screen.renderCharacter(width/2*3, height*3, Sprite.start1);
		screen.renderCharacter(width/2*3+16, height*3, Sprite.start2);
		screen.renderCharacter(width/2*3+32, height*3, Sprite.start3);
		//Option
		screen.renderCharacter(width/2*3, height*3+16, Sprite.op1);
		screen.renderCharacter(width/2*3+16, height*3+16, Sprite.op2);
		screen.renderCharacter(width/2*3+32, height*3+16, Sprite.op3);
		screen.renderCharacter(width/2*3+48, height*3+16, Sprite.op4);
		//Edit
		screen.renderCharacter(width/2*3, height*3+32, Sprite.edit1);
		screen.renderCharacter(width/2*3+16, height*3+32, Sprite.edit2);
		//Exit
		screen.renderCharacter(width/2*3, height*3+48, Sprite.exit1);
		screen.renderCharacter(width/2*3+16, height*3+48, Sprite.exit2);
		
		
		//Cursor
		screen.renderCharacter(width/2*3-16, height*3+1+(16*Player.menu), Sprite.cursor);
		
		for (int i = entities.size() - 1; i >= 0; i--) {
            Entity e = entities.get(i);
            e.render(screen);
        }
		
	}
	
	
	

}
