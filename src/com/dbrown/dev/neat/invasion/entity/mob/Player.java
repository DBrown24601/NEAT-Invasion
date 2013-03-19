package com.dbrown.dev.neat.invasion.entity.mob;

import com.dbrown.dev.neat.invasion.Sound;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;
import com.dbrown.dev.neat.invasion.input.Keyboard;

public class Player extends Mob{
	public static int totalLives = 3;
	public static int lives = 3;
	private Keyboard input;
	private Sprite sprite;
	private int frame = 0;
	private int iter = 7;
	private int cdtimer = 0, testTimer = 0;
	
	
	public Player(Keyboard input){
		this.input = input;
		sprite = Sprite.idle;
	}
	
	public Player(int x, int y, Keyboard input){
		this.x = x - sprite.idle.SIZE/2;
		this.y = y;
		this.input = input;
		sprite = Sprite.idle;
	}
	
	public void update(){
		int xa = 0, ya = 0; 
		boolean s = false, t = false;
		if(testTimer>0){
			testTimer--;
		}
		//VV handles animation VV
		if(frame<10)frame++;
		else frame = 0;
		//handles movement
		if (input.up) ya--;
		if (input.down) ya++;
		if (input.left) xa--;
		if (input.right) xa++;
		//enables firing could be more succinct
		if(input.fire) s = true;
		//timer for fire delay
		if(cdtimer > 0) cdtimer--;
		//fire delay
		if (s&&cdtimer==0){
			shoot(x, y);
			cdtimer = 25;
		}
		
		if(xa != 0 || ya != 0){
			//passes to move to determine direction
			move(xa, ya);
		}else {
			dir = 0;
		}
		
		if(input.test) t = true;
		
		if(t&&testTimer==0){
			Sound.die.play();
			lives--;
			testTimer = 10;
		}
	}
	
	
	public void render(Screen screen){
		if(dir==0){
			sprite = Sprite.idle;
			if(frame>iter){
				sprite = Sprite.idle1;
			}
		}
		if(dir==2){
			sprite = Sprite.stop;
			if(frame>iter){
				sprite = Sprite.stop1;
			}
		}
		if(dir==1){
			sprite = Sprite.right;
			if(frame>iter){
				sprite = Sprite.right1;
			}
		}
		if(dir==3){
			sprite = Sprite.left;
			if(frame>iter){
				sprite = Sprite.left1;
			}
		}
		screen.renderPlayer(x, y, sprite);
	}
	
	
}
