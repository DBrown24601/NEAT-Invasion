package com.dbrown.dev.neat.invasion.entity.mob;

import com.dbrown.dev.neat.invasion.Game;
import com.dbrown.dev.neat.invasion.Sound;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;
import com.dbrown.dev.neat.invasion.input.Keyboard;

public class Player extends Mob{
	public static int totalLives = 5;
	public static int lives = 5;
	public static int menu = 0;
	public static boolean enable = true;
	public static int menuSelect = 0;
	public static boolean pause = false;
	public static boolean gameOver = false;
	
	private int delta = 60;
	private Keyboard input;
	private Sprite sprite;
	private int frame = 0;
	private int iter = 7;
	private int cdtimer = 10, testTimer = 0, pauseTimer = 0;
	private int inv = 0;

	
	
	public Player(Keyboard input){
		this.input = input;
		sprite = Sprite.idle;
		this.type = "Player";
		this.health = 10;
	}
	
	public Player(int x, int y, Keyboard input){
		this.x = x - sprite.idle.SIZE/2;
		this.y = y;
		this.input = input;
		sprite = Sprite.idle;
		this.type = "Player";
		this.health = 10;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
		
	}
	//potentially move these to mob or entity?
	public void setX(int xT){
		x = xT - sprite.idle.SIZE/2;
	}
	
	public void setY(int yT){
		y = yT;
	}
	
	

	
	public boolean isHit(double xc, double yc, double w, double h) {
		//shrink hitbox to compensate for sprite size
		double xx0 = x+15;
		double yy0 = y+10;
		double xx1 = x + 15 + w;
		double yy1 = y + 10 + h;
		if (xx0 > xc + w || yy0 > yc + h || xx1 < xc || yy1 < yc) return false;
		
		return true;
		
	}
	
	public void update(){
		if(testTimer>0){
			testTimer--;
		}
		
		delta--;
		
		if(delta==0) {
			delta = 60;
			if(inv > 0){
				inv--;
			}
			
		}
		
		if(enable&&!gameOver){
			int xa = 0, ya = 0; 
			boolean s = false, t = false;
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
			
			
		}else if (!enable&&!gameOver){
			if(testTimer==0){
				if (input.up) menuInc(true);
				if (input.down) menuInc(false);
				if (input.enter||input.fire) menuSelect();
			}
			
			
		} else if(!enable&&gameOver){
			if (input.enter||input.fire) menuSelect();
		}
		
	}
	
	public void pause(){
		if(pause){
			pause = false;
			enable = true;
		}else{
			pause = true;
			enable = false;
		}
	}
	
	public void menuSelect(){
		if(menu==0){
			menuSelect = 1;
		} else if(menu==1){
			menuSelect = 2;
		} else if(menu==2){
			menuSelect = 3;
		} else if(menu==3){
			menuSelect = 4;
		}
		
		
	}
	
	public void menuInc(boolean up){
		testTimer = 10;
		Sound.select.play();
		if(up){
			if(menu==0){
				menu=3;
			} else {
				menu--;
			}
			
			
		}else{
			if(menu==3){
				menu=0;
			} else {
				menu++; 
			}
			
		}
		
	}

	
	public void remove(){
		lives--;
		Game.deathPause = true;
		Game.score-=100;
		Sound.die.play();
		removed = true;
	}
	
	public boolean isEnabled(){
		return enable;
	}
	
	public void respawn(){
		removed = false;
		health = 10;
	}
	
	public static void enable(){
		enable = true;
	}
	
	public static void disable(){
		enable = false;
	}
	
	
	public void render(Screen screen){


		if(enable){
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
			if(!removed){
				screen.renderPlayer(x, y, sprite);
			}
			
		}
		
	}
	
	
	
	
}
