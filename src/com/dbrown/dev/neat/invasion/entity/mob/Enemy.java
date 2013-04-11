package com.dbrown.dev.neat.invasion.entity.mob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.neat4j.neat.ga.core.Chromosome;

import com.dbrown.dev.neat.invasion.Game;
import com.dbrown.dev.neat.invasion.Sound;
import com.dbrown.dev.neat.invasion.entity.Entity;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;

public class Enemy extends Mob{
	
	Chromosome chrom;
	public int points = 100;
	Random rn = new Random();
	private int r;
	private double[] output = {0.0,0.0,0.0};
	private int cdtimer = 10;
	boolean s = false;
	//EnemyFitnessFunction eval = new EnemyFitnessFunction();
	public List<Entity> input = new ArrayList<Entity>();
	private int fitnessScore;
	
	public Enemy(int x, int y){
		this.x = x;
		this.y = y;
		this.type = "Enemy";
		this.sprite = Sprite.enemy;
	}
	
	
	//To be used by the fitness function outputs-----------
	public void setXNEAT(int x){
		xa = x;
	}
	
	public void setYNEAT(int y){
		ya = y;
	}
	
	public void fireNEAT(){
		s = true;
	}
	//-----------------------------------------------------
	
	
	public void update(){
		int xa = 0, ya = 0; 
		if(level.p.health>0){
			
			
			if(xa != 0 || ya != 0){
				if(xa > 0&&x > 265){
					
				}
				
				
				move(xa, ya);
			}
			
			if(output[2]>0){
				s=true;
			}
			
			if(cdtimer > 0) cdtimer--;
			
			if (s&&cdtimer==0){
				shoot(x, y);
				cdtimer = 25;
			}
		}
	}
	
	public boolean isTypeOf(String s){
		if(s.equals(type)){
			return true;
		}
		return false;
	}
	
	//to be used by the fitness function inputs---------------
	
	public int xDistToPlayer(){
		int xP = level.p.getX();
		return xP - x;
	}
	
	public int yDistToPlayer(){
		int yP = level.p.getY();
		return yP - y;
	}
	
	public int getSpeedX(){
		return xa;
	}
	
	public int getSpeedY(){
		return ya;
	}
	
	public boolean canFire(){
		if(cdtimer>0){
			return false;
		} else {
			return true;
		}
	}
	
	public int getClosestBulletX(){
		return 0 - x;
	}
	
	public int getClosestBullety(){
		return 0 - y;
	}
	
	public int getClosestAllyX(){
		return 0 - x;
	}
	
	public int getClosestAllyY(){
		return 0 - y;
	}
	//--------------------------------------------------------


	public void render(Screen screen){
		screen.renderEntity(x, y, this);
	}
	
	public void remove(){
		//remove from level code stuff
		Game.score+=points;
		Sound.edie.play();
		removed = true;
	}
	
	
	/*
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
		}
		if(!collisionY()){
			
			y += ya * 2;
		}		
	}
	
	
	private boolean collisionX(){
		if(!left){
			if(x+1>268){
				x = 0;
			}
		}else{
			if(x-1<0){
				x = 268;
			}
		}
		
		return false;
	}
	private boolean collisionY(){
		if(!up){
			if(y-2<=0){
				y = 419;
			}
		} else{
			if(y+2>=419){
				y = 0;
			}
		}
				
		return false;
	}
*/
}
