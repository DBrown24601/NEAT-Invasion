package com.dbrown.dev.neat.invasion.entity.mob;

import java.util.Random;

import com.dbrown.dev.neat.invasion.Game;
import com.dbrown.dev.neat.invasion.Sound;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;

public class Enemy extends Mob{
	
	public int points = 100;
	Random rn = new Random();
	private int r;
	
	
	public Enemy(int x, int y){
		this.x = x;
		this.y = y;
		this.type = "Enemy";
		this.sprite = Sprite.enemy;
	}
	
	public void update(){
		if(level.p.health>0){
			r = rn.nextInt(200);
			if(r == 99){
				shoot(x,y);
			}
			
			
		}
		//AI socket goes here
		//x++;
		//this.shoot(x, y);
		//y++;
		
		//TD: Smooth reentry
		//if(x>=300)x= -32;
		//if(y>=300/2*3)y= -32;
		
		//temp shooting mechanic
		
		
		
		
		
	}
	
	public boolean isTypeOf(String s){
		if(s.equals(type)){
			return true;
		}
		return false;
	}
	


	public void render(Screen screen){
		screen.renderEntity(x, y, this);
	}
	
	public void remove(){
		//remove from level code stuff
		Game.score+=points;
		Sound.edie.play();
		removed = true;
	}

}
