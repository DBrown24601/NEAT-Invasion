package com.dbrown.dev.neat.invasion;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.anji.neat.Evolver;
import com.anji.util.Properties;
import com.dbrown.dev.neat.invasion.entity.mob.Player;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.input.Keyboard;
import com.dbrown.dev.neat.invasion.level.GameOverLevel;
import com.dbrown.dev.neat.invasion.level.Level;
import com.dbrown.dev.neat.invasion.level.RandomLevel;
import com.dbrown.dev.neat.invasion.level.TitleLevel;
import com.dbrown.dev.neat.invasion.neat.runAI;


public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	
	public static int width = 300;
	public static int height = width / 2 * 3;
	public static int scale = 5;
	
	public static int[] enemyFitnessEval = new int[14];
	
	public volatile static boolean deathPause = false; //Restricts the AI upon player death until respawn
	public volatile static int numReset = 0; //increments by 2 each time a unit returns to origin
	
	public volatile static boolean levelReady = false;

	public static String title = "NEAT Invasion";
	
	public static String version = "build# ";
	public static int score;
	
	public String outputTop = "ID/GEN,1,2,3,4,5,6,7,8,9,10,11,12,13,14,AVERAGE";
	
	public volatile static boolean levelStart = false;
	
	private Thread thread;
	public static Object lockTarget;
	public static Object e1;
	private JFrame frame;
	private Keyboard key;
	public volatile Level level;
	public int levelNum = 0;
	public volatile Player player;
	public boolean running = false;
	private String msg;
	public int updates;
	private Screen screen;
	public volatile double delta = 0;
	public long timer = 0, lTimer = 0;
	//public GameEnemyManager gam;
	public double sum;
	public Evolver evolver;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game() throws Exception {
		while(screenHeight < height*scale){
			scale--;
		}
		
		//ANJI initialization
		Properties props = new Properties( "neatConfig/enemy.properties" );
		evolver = new Evolver();
		lockTarget = new Object();
		e1 = new Object();
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		score = 0;
		screen = new Screen(width, height, scale);
		frame = new JFrame();
		key = new Keyboard();
		level = new TitleLevel(64, 64, player);
		player = new Player(width/2,height*7/8,key);
		player.init(level);
		evolver.init( props, level, true);
		level.setEvolver(evolver);
		
		addKeyListener(key);
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try{
			thread.join();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void delay(int time){ //in seconds
		Player.disable();
		//render();
		long t = System.currentTimeMillis();
		int seconds = time;
		while(seconds > 0){
			if(System.currentTimeMillis() - t > 1000){
				t += 1000;
				seconds--;
			}
		}
		Player.enable();
		
	}
	
	public void newGame(){
		Player.gameOver = false;
		player = new Player(width/2,height*7/8,key);
		level = new TitleLevel(64,64,player);
		//render();
		//delay(2);
		levelNum = 0;
		player.init(level);
		
	}
	
	public void newLevel(){
		
		player = new Player(width/2,height*7/8,key);
		level = new RandomLevel(64,64,player);
		levelStart = true;
		levelNum++;
		//render();
		//delay(2);
		player.init(level);
		
	}
	
	public void respawn(){
		//player = new Player(width/2,height*7/8,key);
		//player.init(level);
		//player.h = 2;
		//Player.enable();
		player.setX(width/2);
		player.setY(height*7/8);
		Player.enable();
		player.respawn();
	}
	
	public void gameOver(){
		//delay(2);
		Player.gameOver = true;
		Player.menu = 5;
		player = new Player(width/2,height*7/8,key);
		level = new GameOverLevel(64,64,player);
		player.init(level);
		Player.lives=1;
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		delta = 0;
		int frames = 0;
		updates = 0;
		requestFocus();
		while(running){
			long now = System.nanoTime();
			delta += (now-lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				
				if(Player.menuSelect==1){
					newLevel();
					Runnable r = new runAI(this, evolver, level, lockTarget, e1);
					new Thread(r).start();
					Player.lives = 3;
					Player.menuSelect=0;
				} else if(Player.menuSelect == 4){
					System.exit(0);
				} else if(Player.menuSelect == 5){
					newGame();
				}
				
				if(level.enemies==0){
					sum = 0;
					for(int i = 0; i<14; i++)
						sum += enemyFitnessEval[i];
					
					sum = sum/14;
					System.out.println("GENERATION AVERAGE FITNESS: " + sum + "-----------------------");
					
					synchronized(lockTarget){
						levelStart = false;
						try {
							lockTarget.wait(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					levelReady = false;
					newLevel();
				}
				
				if(player.isRemoved()){
					if(!deathPause){
						respawn();
						numReset = 0;
					}
				}
				
				if(numReset == level.enemies*2){
					deathPause = false;
				}
				
				if(Player.lives<0){
					gameOver();
				}
				
				//LOGGING STUFF
				
				update();
				updates++;
				delta--;
				
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				
				frame.setTitle(title +" | " + version + " | " + updates + " ups " + frames + " fps");
				updates = 0;
				frames = 0;
				if(timer>0){
					timer--;
				}
			}
		}
		stop();
		
	}
	
	public void update(){
		key.update();
		player.update();
		if(!Player.pause){
			level.update();
		}
	}
	
	
	
	
	protected boolean win = false;
	String msg2 = "To be added";
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		level.render(width/2,height*2/3, screen);
		level.renderEntities(screen);
		player.render(screen);
		
		for (int i = 0; i < pixels.length; i++){
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana",0,12));
		//DEBUG START
	
		msg = "P1 = X: " + player.x + "  Y: " + player.y;
		//g.drawString(msg, 15, 15);
		//g.drawString("Num of lives: " + Player.lives, 15, 27);
		//g.drawString("deathReset: " + deathPause, 15, 27);
		//g.drawString("numReset: " + numReset, 15, 39);
		//g.drawString("Num of enemies: " + level.enemies, 15, 39);
		//g.drawString("Num of entities: " + level.entities.size(), 15, 51);
		
		for(int j = 0; j < level.entities.size(); j++){
			if(level.entities.get(j).isTypeOf("Enemy")){
				g.drawString("f: " + level.entities.get(j).fitnessScore + " id: " + level.entities.get(j).id, level.entities.get(j).x * scale, level.entities.get(j).y * scale);
				g.drawString("x: " + level.entities.get(j).xDistToPlayer() + " y: " + level.entities.get(j).yDistToPlayer(), level.entities.get(j).x*scale, level.entities.get(j).y*scale+15);
			}
		}
		
		//g.drawString("h: " + player.health, player.x*scale, player.y*scale);
		
		g.drawString("Score: " + score, (width * 2/3)*scale, 15);
		g.drawString("Level: " + levelNum, (width * 2/3)*scale, 30);
		g.drawString("Last Av. Fit: " + sum, (width * 2/3)*scale, 45);
		if(Player.menuSelect==2||Player.menuSelect==3){
			g.setFont(new Font("Verdana",0,16));
			g.drawString(msg2, width*1/3, height*2/3*scale);
		}
		
		if(Player.pause){
			g.drawString("PAUSED", width/2*scale-15, height/2*scale);
		}
		
		//DEBUG END
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) throws Exception{
		boolean reset;
		
		if(args.length>0){
			reset = true;
		}
		
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(title +" | " + version +" | " + "Getting info...");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}
	
	

}
