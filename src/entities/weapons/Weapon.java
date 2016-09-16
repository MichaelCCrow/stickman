package entities.weapons;

import java.io.IOException;
import java.util.LinkedList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entities.Entity;
import entities.projectiles.Projectile;
import entities.projectiles.ProjectileFactory;
import game.AudioManager;
import game.TextureManager;

public abstract class Weapon extends Entity {
	
	
	protected ProjectileFactory pfactory;
	protected LinkedList<Projectile> ammo;
	
	protected String leftPath;
	protected String rightPath;
		
	protected static Texture leftTexture;
	protected static Texture rightTexture;

	protected static int intdir;
	
	protected AudioManager amGunSound;
	protected String gunNoise;
	
	public static boolean isEquipped;
	private Gun gun;

	public enum Direction {
		
		LEFT {
			
			public Texture face() {
				intdir = -1;
				return leftTexture;				
			}
		}, 
		
		RIGHT {
			
			public Texture face() {	
				intdir = 1;
				return rightTexture;
			}
		};
		
		public abstract Texture face();
	};
	Direction d;
	
	
	public Texture loadTexture(String path) {
		return new TextureManager(path).getTheTexture();
	}
	
	public void setLeft(String leftPath) {
		Weapon.leftTexture = loadTexture(leftPath);
	}
	public void setRight(String rightPath) {
		Weapon.rightTexture = loadTexture(rightPath);
	}
	
	public void setCurrent(Texture texture) {
		this.texture = texture;
	}
	
	public void setDirection(Direction d) {
		this.d = d;
		setCurrent(d.face());
	}
	public Direction getDirection() {
		return this.d;
	}
	
	public void setGun(Gun gun) { this.gun = gun; }
	public Gun getGun() { return this.gun; }
	
	
	public void setAudio(String sound, String path) throws IOException {		
		this.amGunSound = AudioManager.getInstance();
		this.gunNoise = sound;
		amGunSound.loadSample(sound, path);
	}
	
	public void setAmmoFactory(ProjectileFactory pfactory) {
		this.pfactory = pfactory;
	}
	
	public void shoot(LinkedList<Projectile> ammo, int xx, int yy, int w, int h) throws IOException {
		
		this.ammo = ammo;
		int x = (int) (xx+(w-15));
		int y = (int) (yy+(h*.3f));
		
		if (d == Direction.LEFT) {
			x = xx-5;
		}
		
		if (isEquipped) {
			ammo.add(pfactory.newProjectile(x, y, intdir));
			amGunSound.play(gunNoise);
		}
		
	}
	
}
