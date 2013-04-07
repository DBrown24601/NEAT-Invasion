package com.dbrown.dev.neat.invasion.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
//http://www.youtube.com/watch?v=n1S11YByJUI

public class SpriteSheet {
	private String path;
	public final int SIZE;
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/space1.png", 64);
	public static SpriteSheet ship = new SpriteSheet("/textures/ship.png", 96);
	public static SpriteSheet bullet = new SpriteSheet("/textures/laser2.png", 8);
	public static SpriteSheet enemy = new SpriteSheet("/textures/enemy.png",32);
	public static SpriteSheet gui = new SpriteSheet("/textures/gui.png/",128);
	public static SpriteSheet title = new SpriteSheet("/textures/title.png/",256);
	
	public SpriteSheet(String path, int size){
		this.path = path;
		SIZE = size;
		pixels = new int[SIZE*SIZE];
		load();
	}
	
	
	
	private void load(){
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0,0,w,h,pixels,0,w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
