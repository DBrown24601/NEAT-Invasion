package com.dbrown.dev.neat.invasion.entity;

import com.dbrown.dev.neat.invasion.Game;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;




public class Bullet extends Entity{
	private int update = 0;
	private boolean playersBullet;
	
	public Bullet(int x, int y, boolean s){
		this.x = x+16;
		this.y = y;
        this.w = 1;
        this.h = 1;
        this.playersBullet = s;
        this.xa = 0;
        this.ya = 0;
        this.sprite = Sprite.laser;
        this.type = "Bullet";
	}
	
	public void update(){
		update++;
		if(!playersBullet){
			y+=8;
			
	            
			if (level.p.isHit(x, y, 15, 5)) { 
				if(level.p.shot(this)){
	                remove();
				}
            }
	        
			
			
			
		} else if (playersBullet){
			y-=8;
			
			java.util.List<Entity> entities = level.getHits(x, y, 8, 8);
			for (int i = 0; i < entities.size(); i++) {
	            Entity e = entities.get(i);

	            if (e.shot(this)) { 	
	                remove();
	            }
	        }
		}
		
        
        
	}
	
	public void render(Screen screen) {
        screen.renderEntity(x-4, y, this);
    }
	
	public boolean isTypeOf(String s){
		if(s.equals(type)){
			return true;
		}
		return false;
	}

}
