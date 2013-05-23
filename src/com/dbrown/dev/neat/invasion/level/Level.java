package com.dbrown.dev.neat.invasion.level;

import java.util.ArrayList;
import java.util.List;

import com.anji.neat.Evolver;
import com.dbrown.dev.neat.invasion.Game;
import com.dbrown.dev.neat.invasion.entity.Entity;
import com.dbrown.dev.neat.invasion.entity.Life;
import com.dbrown.dev.neat.invasion.entity.mob.Enemy;
import com.dbrown.dev.neat.invasion.entity.mob.Player;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.graphics.Sprite;
import com.dbrown.dev.neat.invasion.level.tile.Tile;
//import org.neat4j.core.AIConfig;
//import org.neat4j.core.InitialisationFailedException;
//import org.neat4j.neat.core.NEATLoader;
//import com.dbrown.dev.neat.invasion.NEAT.GameEnemyManager;


public class Level {
	
	protected int width, height;
	public static int eNum = 0;
	public List<Entity> entities = new ArrayList<Entity>();
	public Player p;
	public int enemies;
	protected int[] tiles;
	boolean title;
	public Evolver evo;
	
	public boolean ready = false;
	
	public Level(int width, int height, Player p, boolean title)	{
		this.width = width;
		this.height = height;
		this.p = p;
		tiles = new int[width * height];
		//System.out.println("WOO: " + width*height);
		generateLevel();
		Entity e;
		
		//lives GUI until GUI pipeline is complete
		
		

		if(!title){
			Player.enable();
			
			
			for(int j = 0; j < Player.lives; j++){
				//add to GUI arraylist first, then remove X for each life lost
				e = new Life(j*10 + 10, 15);
				add(e);
			}
			
			for(int i = 0; i < 7; i++){
				e = new Enemy(i*42+8,30, i);
				Game.enemyFitnessEval[i] = 0;
				add(e);
				enemies++;
				e = new Enemy(i*42+8,70, i + 7);
				Game.enemyFitnessEval[i+7] = 0;
				add(e);
				enemies++;
				
			}
			Game.levelStart = true;

		} else {
			Player.disable();
			enemies++;
		}
	}

	public Level(String path){
		loadLevel(path);
	}
	
	public void setEvolver(Evolver e){
		evo = e;
		
		ready = true;
	}
	
	public void init(){
		
	}
	
	protected void generateLevel(){
		
	}
	
	public int getClosestX(String type, int xe){
		int xP = 1000;
		for(int k = 0; k < entities.size(); k++){
			if(entities.get(k).isTypeOf(type)){
				if(xP>entities.get(k).x){
					xP = entities.get(k).x;
				}
			}
		}
		if(xP==1000){
			xP=0;
		}
		return xP;
	}
	
	public int getClosestY(String type, int ye){
		int yP = 1000;
		for(int k = 0; k < entities.size(); k++){
			if(entities.get(k).isTypeOf(type)){
				if(yP>entities.get(k).x){
					yP = entities.get(k).x;
				}
			}
		}
		if(yP==1000){
			yP=0;
		}
		return yP;
	}
	
	public void add(Entity e) {
        entities.add(e);
        e.init(this);
    }
	
	public Entity getNum(){
		Entity e = entities.get(eNum);
		eNum++;
		return e;
	}
	
	protected void loadLevel(String path){
	}
	
	public void update(){
		if(entities.size()>0){
			for(int i = 0; i < entities.size(); i++){
				Entity e = entities.get(i);
				if(!e.isRemoved()){
					e.update();
				}
				if(e.isRemoved()){
					entities.remove(i--);
					if(e.sprite==Sprite.enemy){
						enemies--;
					}
				}else{
					e.outOfBounds();
				}
			}
			
			
		}
		
		
		
		
		//enemy AI etc
	}
	
    private List<Entity> hits = new ArrayList<Entity>();

	public List<Entity> getHits(double xc, double yc, double w, double h) {
		hits.clear();
		for (int i = 0; i < entities.size(); i++) {
			//shrink hitbox to compensate for sprite size
			Entity e = entities.get(i);
			double xx0 = e.x+13;
			double yy0 = e.y+10;
			double xx1 = e.x + 10 + e.w;
			double yy1 = e.y + 10 + e.h;
			if (xx0 > xc + w || yy0 > yc + h || xx1 < xc || yy1 < yc) continue;
			
			hits.add(e);
		}
		
		return hits;
	}

	
	
	public void renderEntities(Screen screen){
		if(entities.size()>0){
			for(int i = 0; i < entities.size(); i++){
				if(entities.get(i).isTypeOf("Life")){
					
					//for(int j = 0; j < Player.lives; j++){
					//	entities.get(i+j).render(screen);
					//}
					//i = i + Player.totalLives;
				}
				entities.get(i).render(screen);
			}
		}
	}
	
	
	
	public void render(int xScroll, int yScroll, Screen screen){
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 5;
		int x1 = (xScroll + screen.width + 32) >> 5;
		int y0 = yScroll >> 5;
		int y1 = (yScroll + screen.height + 32) >> 5;
		for (int y = y0; y < y1; y++){
			for (int x = x0; x < x1; x++){
				getTile(x,y).render(x, y, screen);
			}
		}
		for (int i = entities.size() - 1; i >= 0; i--) {
            Entity e = entities.get(i);
            e.render(screen);
        }
		
	}
	
	public Tile getTile(int x, int y){
		if(x<0||y<0||x>=width||y>=height) return Tile.voidTile;
		if (tiles[x + y * width] == 0) return Tile.space_4;
		if (tiles[x + y * width] >= 1&&tiles[x + y * width] < 5) return Tile.space_2;
		if (tiles[x + y * width] >= 5&&tiles[x + y * width] < 30) return Tile.space_1;
		if (tiles[x + y * width] <= 30) return Tile.space_3;
		//if (tiles[x + y * width] == 0) return Tile.voidTile;
		return Tile.voidTile;
	}
	
	
	

}
