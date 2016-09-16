package entities.weapons;

import java.io.IOException;
import java.util.LinkedList;

import entities.projectiles.Bullet;
import entities.projectiles.BulletFactory;
import entities.projectiles.Projectile;

public class Pistol extends Weapon {
	
	
	public Pistol() throws IOException {
		
		// setGun(Gun.PISTOL);
		
		this.rightPath = "res/PlayerImages/Pistol64x64.png";
		this.leftPath = "res/PlayerImages/PistolLeft64x64.png";
		
		String sound = "gunshot";
		String soundPath = "res/Sounds/gunshot.ogg";
		
		setLeft(leftPath);
		setRight(rightPath);
		
		setAudio(sound, soundPath);
		setAmmoFactory(new BulletFactory());
	}
	
}
