package com.dbrown.dev.neat.invasion.entity.mob;

import com.dbrown.dev.neat.invasion.Sound;
import com.dbrown.dev.neat.invasion.entity.Bullet;
import com.dbrown.dev.neat.invasion.entity.Entity;

public abstract class Mob extends Entity {
	//protected Sprite sprite;
	public int health = 2;
	protected int dir = 0;
	protected boolean moving = false, up = false, left = false;
	
	public void move(int xa, int ya){
		//-1, 0, 1

		if (ya > 0){
			dir = 2;
			up = true;
		}
		if (ya < 0){ 
			dir = 0;
			up = false;
		}
		if (xa > 0){
			dir = 1;
			left = false;
		}
		if (xa < 0){
			dir = 3;
			left = true;
		}
		
		if(!collisionX()){
			
			x += xa * 2;
		} else {
			x += 0;
		}
		if(!collisionY()){
			
			y += ya * 2;
		} else {
			y += 0;
		}
		
		
		
		
	}
	
	public void shoot(int x, int y){
		Sound.shoot.play();
		if(this.isTypeOf("Player")){
			level.add(new Bullet(x,y,true));
		} else {
			level.add(new Bullet(x,y,false));
		}
		
		//Bullet bullet = new Bullet(x, y);
	}
	
	public void update(){

	}
	
	public int getX(){
		int x = 0;
		return x;
	}
	
	public int getY(){
		int y = 0;
		
		return y;
		
	}
	
	public boolean shot(Bullet bullet) {
		health--;
        if(health==0){
        	remove();
        } else {
        	Sound.hit.play();
        }
        return true;
    }
	
	public void render(){
	}
	
	public void remove(){
		//remove from level code stuff
		removed = true;
	}
	
	private boolean collisionX(){
		if(!left){
			if(x+1>268){
				return true;
			}
		}else{
			if(x-1<0){
				return true;
			}
		}
		
		return false;
	}
	private boolean collisionY(){
		if(!up){
			if(y-2<=0){
				return true;
			}
		} else{
			if(y+2>=419){
				return true;
			}
		}
				
		return false;
	}
	
	

}
