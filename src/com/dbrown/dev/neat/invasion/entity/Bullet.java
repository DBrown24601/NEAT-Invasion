package com.dbrown.dev.neat.invasion.entity;

import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;




public class Bullet extends Entity{
	private int update = 0;
	
	public Bullet(int x, int y){
		this.x = x+16;
		this.y = y;
        this.w = 1;
        this.h = 1;
        this.xa = 0;
        this.ya = 0;
        this.sprite = Sprite.laser;
        this.type = "Bullet";
	}
	
	public void update(){
		update++;
		y-=8;
        java.util.List<Entity> entities = level.getHits( x, y, 8, 8);
		for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);

            if (e.shot(this)) { 	
                remove();
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
