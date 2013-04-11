package com.dbrown.dev.neat.invasion.entity;

import java.util.Random;

import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;
import com.dbrown.dev.neat.invasion.level.Level;


public abstract class Entity {
	public int xa, ya;
	public Sprite sprite;
    public int x, y;
	protected boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	public int w = 15, h = 15;
	public String type;
	
	public void init(Level level) {
        this.level = level;
    }
	
	public void update(){
		
	}
	
	public boolean shot(Bullet bullet) {
        return false;
    }
	
	public void outOfBounds() {
        if (y < 0) remove();
        if (y > 419) remove();
    }
	
	public void render(Screen screen){
		
		
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
	
	public boolean isRemoved(){
		return removed;
	}

}
