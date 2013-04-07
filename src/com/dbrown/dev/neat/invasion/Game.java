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

import com.dbrown.dev.neat.invasion.entity.mob.Player;
import com.dbrown.dev.neat.invasion.graphics.Screen;
import com.dbrown.dev.neat.invasion.input.Keyboard;
import com.dbrown.dev.neat.invasion.level.Level;
import com.dbrown.dev.neat.invasion.level.RandomLevel;
import com.dbrown.dev.neat.invasion.level.TitleLevel;

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
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		while(screenHeight < height*scale){
			scale--;
		}
		
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		score = 0;
		screen = new Screen(width, height, scale);
		frame = new JFrame();
		key = new Keyboard();
		level = new TitleLevel(64, 64, player);
		player = new Player(width/2,height*7/8,key);
		//level = new RandomLevel(64,64, player);
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
	
	public void newLevel(){
		level = new RandomLevel(64,64,player);
		player = new Player(width/2,height*7/8,key);
		player.init(level);
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
			}
		}
		stop();
		
	}
	
	public void update(){
		key.update();
		player.update();
		level.update();
	}
	
	protected boolean win = false;
	
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
		g.drawString("Num of lives: " + Player.lives, 15, 27);
		//g.drawString("Num of enemies: " + level.enemies, 15, 27);
		
		g.drawString("Score: " + score, (width * 2/3)*scale, 15);
		//sine wave code
		//g.drawString(msg, 160 - msg.length() * 3, 140 - 3 - (int) (Math.abs(Math.sin(updates * 0.1) * 5)));

		//DEBUG END
		
		
		//if(input.enter) win = true;
		if(level.enemies == 0||player.isRemoved()){
			//win = true;
		}
		
		
		
		if(win){
			newLevel();
			player.health = 2;
			win = false;
		}
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
