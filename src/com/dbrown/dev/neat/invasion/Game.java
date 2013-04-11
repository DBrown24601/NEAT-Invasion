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

import com.dbrown.dev.neat.invasion.NEAT.GameEnemyManager;
import com.dbrown.dev.neat.invasion.entity.mob.Player;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.input.Keyboard;
import com.dbrown.dev.neat.invasion.level.GameOverLevel;
import com.dbrown.dev.neat.invasion.level.Level;
import com.dbrown.dev.neat.invasion.level.RandomLevel;
import com.dbrown.dev.neat.invasion.level.TitleLevel;

import org.neat4j.core.AIConfig;
import org.neat4j.core.InitialisationFailedException;
import org.neat4j.neat.core.NEATLoader;

//WORK COMPUTER AVERAGE 60 UPS 965 FPS

//~~~~~~~~~~~~~~~~~TODO~~~~~~~~~~~~~~~~~
//Basic player/bullet collision[HIGH]
//BASIC AI[CORE]
//IMPLEMENT STREAMLINE SUGGESTED ON /R/CHERNO [LOW]
//SMOOTH REPEAT[LOW]

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	
	public static int width = 300;
	public static int height = width / 2 * 3;
	public static int scale = 5;

	public static String title = "NEAT Invasion";
	
	public static String version = "build# ";
	public static int score;
	
	
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	public Player player;
	private boolean running = false;
	private String msg = "";
	public int updates;
	private Screen screen;
	public long timer = 0, lTimer = 0;
	
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		while(screenHeight < height*scale){
			scale--;
		}
		
		GameEnemyManager gam = new GameEnemyManager();
		AIConfig config = new NEATLoader().loadConfig("neat_invaders.ga");
		try {
			gam.initialise(config);
		} catch (InitialisationFailedException e1) {
			e1.printStackTrace();
		}
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		score = 0;
		screen = new Screen(width, height, scale);
		frame = new JFrame();
		key = new Keyboard();
		level = new TitleLevel(64, 64, player);
		player = new Player(width/2,height*7/8,key);
		player.init(level);
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
		
		player = new Player(width/2,height*7/8,key);
		level = new TitleLevel(64,64,player);
		//render();
		//delay(2);
		player.init(level);
		
	}
	
	public void newLevel(){
		
		player = new Player(width/2,height*7/8,key);
		level = new RandomLevel(64,64,player);
		//render();
		//delay(2);
		player.init(level);
		
	}
	
	public void respawn(){
		player = new Player(width/2,height*7/8,key);
		player.init(level);
		Player.enable();
	}
	
	public void gameOver(){
		//delay(2);
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
		double delta = 0;
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
					Player.lives = 3;
					Player.menuSelect=0;
				} else if(Player.menuSelect == 4){
					System.exit(0);
				} else if(Player.menuSelect == 5){
					
				}
				
				if(level.enemies==0){
					newLevel();
				}
				
				if(player.isRemoved()){
					respawn();
				}
				
				if(Player.lives<0){
					gameOver();
				}
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
	String msg2 = "To be added in the form of an XML document";
	
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
		//g.drawString("Num of enemies: " + level.enemies, 15, 39);
		//g.drawString("Num of entities: " + level.entities.size(), 15, 51);
		g.drawString("Score: " + score, (width * 2/3)*scale, 15);
		
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
	
	public static void main(String[] args){
		
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
