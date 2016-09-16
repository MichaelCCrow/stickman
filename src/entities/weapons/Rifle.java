package entities.weapons;

import java.io.IOException;
import java.util.LinkedList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import entities.projectiles.LaserFactory;
import entities.projectiles.Projectile;
import game.TextureManager;

public class Rifle extends Weapon {
	
	public Rifle() throws IOException {
		
		// setGun(Gun.RIFLE);
		
		this.rightPath = "res/PlayerImages/Rifle64x64.png";
		this.leftPath = "res/PlayerImages/RifleLeft64x64.png";
		
		String sound = "rifle";
		String soundPath = "res/Sounds/RifleSound.ogg";
		
		setLeft(leftPath);
		setRight(rightPath);
		
		setAudio(sound, soundPath);
		setAmmoFactory(new LaserFactory());
	}
}
