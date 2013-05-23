package com.dbrown.dev.neat.invasion.entity.mob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.anji.integration.Activator;
import com.dbrown.dev.neat.invasion.Game;
import com.dbrown.dev.neat.invasion.Sound;
import com.dbrown.dev.neat.invasion.entity.Entity;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;
import com.dbrown.dev.neat.invasion.neat.EnemyFitnessFunction;
//import org.neat4j.neat.ga.core.Chromosome;

public class Enemy extends Mob{
	
	//Chromosome chrom;
	public int points = 100;
	Random rn = new Random();
	
	private double[] output = {0.0,0.0,0.0};
	private int cdtimer = 10;
	private int delta;
	boolean s = false;
	private int xOrigin, yOrigin;
	private boolean xResetLimit = false;
	private boolean yResetLimit = false;
	
	//EnemyFitnessFunction eval = new EnemyFitnessFunction();
	public List<Entity> input = new ArrayList<Entity>();
	
	
	//1 point per second alive
	//5 points per shot
	//200 per hit

	
	public Enemy(int x, int y, int id){
		this.x = x;
		this.y = y;
		this.delta = 60;
		this.type = "Enemy";
		this.sprite = Sprite.enemy;
		this.fitnessScore = 1;
		this.id = id;
		this.xOrigin = x;
		this.yOrigin = y;
	}
	
	
	
	
	public Enemy() {
		//empty intentionally
	}
	
	public void learnPlayer(Player player){
		p = player;
	}


	//To be used by the fitness function ------------------
	public void setFF(EnemyFitnessFunction ffT){
		ff = ffT;
	}
	
	public void setActivator(Activator activator){
		a = activator;
	}
	
	public void setXNEAT(int xd){
		if(!Game.deathPause){
			xa = xd;
		} else {
			if( xOrigin > x+1 ){
				xa = 1;
			} else if (xOrigin < x-1 ){
				xa = -1;
			} else {
				xa = 0;
				if(!xResetLimit){
					Game.numReset++;
					xResetLimit = true;
				}
			}
			
		}
	}
	
	public void setYNEAT(int yd){
		if(!Game.deathPause){
			ya = yd;
		} else {
			if( yOrigin > y+1 ){
				ya = 1;
			} else if (yOrigin < y-1 ){
				ya = -1;
			} else {
				ya = 0;
				if(!yResetLimit){
					Game.numReset++;
					yResetLimit = true;
				}
			}
		}
	}
	
	public void fireNEAT(){
		if(!Game.deathPause){
			s = true;
		}
	}
	
	public int getFitness(){
		return finalFitness;
	}
	
	//-----------------------------------------------------
	
	
	
	
	public void update(){
		//int xa = 0, ya = 0; 
		if(Player.enable){
			delta--;
			if(delta == 0){
				fitnessScore+=2;
				delta = 60;
			}
			
			if(!Game.deathPause){
				xResetLimit = false;
				yResetLimit = false;
			}
			
			if(xa != 0 || ya != 0){
				if(xa > 0&&x > 265){
					
				}
				
				
				move(xa, ya);
			}
			
			
			if(cdtimer > 0) cdtimer--;
			
			if (s&&cdtimer==0){
				shoot(x, y);
				cdtimer = 25;
				fitnessScore+=2;
				s = false;
				//fitnessScore+=0;
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
		finalFitness = fitnessScore;
		Game.enemyFitnessEval[id] = finalFitness;
		System.out.println("ENEMY " + id + " FITNESS: " + finalFitness + "----------------------------------------------");
		Game.score+=points;
		Sound.edie.play();
		removed = true;
	}
	
	
	
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
			
			x += xa;
		}
		if(!collisionY()){
			
			y += ya;
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

}
