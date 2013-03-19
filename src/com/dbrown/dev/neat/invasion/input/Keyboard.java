package com.dbrown.dev.neat.invasion.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard  implements KeyListener {
	
	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right, fire, test;
	
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		fire = keys[KeyEvent.VK_SPACE];
		test = keys[KeyEvent.VK_L];
		
		
		
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			
		}
	}

	public void keyTyped(KeyEvent e) {
		
		
	}

	

}
