package com.dbrown.dev.neat.invasion.entity;

import com.dbrown.dev.neat.invasion.Game;
import com.dbrown.dev.neat.invasion.Sound;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;

public class Life extends Entity{

	
	public Life(int x, int y){
		sprite = Sprite.counter;
		this.x = x;
		this.y = y;
		this.type = "Life";
	}
	
	public void update(){
	
	}
	


	public void render(Screen screen){
		screen.renderEntity(x, y, this);
	}
	
	public boolean isTypeOf(String s){
		if(s.equals(type)){
			return true;
		}
		return false;
	}
	
	public void remove(){
		//remove from level code stuff

		removed = true;
	}
	
	
}
