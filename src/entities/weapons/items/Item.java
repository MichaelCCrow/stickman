package entities.weapons.items;

import java.io.IOException;
import java.util.LinkedList;

import entities.Entity;
import entities.StickmanTest;
import entities.weapons.Gun;
import game.AudioManager;

public class Item extends Entity {

	static String soundPath = "res/Sounds/GunCocking.ogg";
	AudioManager am;
	public Gun gun;
	
	public Item(String tPath, int x, int y, int w, int h) {
		super(tPath, x, y, w, h);
		
		am = AudioManager.getInstance();
		try {
			am.loadSample("equip", soundPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isGameBreaker(Item it) {
		try {
			am.loadSample("gamebreaker", "res/Sounds/Dark_Laugh.ogg");
		} catch (IOException e) { e.printStackTrace(); }
		
		if (it instanceof GameBreakerItem) return true;
		else return false;
	}

	public void onCollision(Entity other) {
		if (this.isActive()) {
			if (isGameBreaker(this)) am.play("gamebreaker");
			else am.play("equip");
			StickmanTest.addToInventory(this.gun);
			System.out.println(this.gun.toString() + " picked up");
		}
		this.deactivate();
	}
	
}
