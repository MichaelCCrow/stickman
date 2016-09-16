package entities;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entities.projectiles.Butter;
import entities.projectiles.Laser;
import entities.projectiles.LaserFactory;
import entities.projectiles.Projectile;
import entities.projectiles.ProjectileFactory;
import entities.projectiles.Spit;
import game.AudioManager;
import game.TextureManager;

public class TanisBoss extends Entity {

	private enum DIR { RIGHT, LEFT, UP, DOWN }
	DIR current;
	
	private LinkedList<Projectile> lasers;
	private ProjectileFactory pf;
	private int health;
	
	private float counter=0;
	private float hitTimer=0;
	private int shotCounter=0;
	
	private AudioManager am;
	private Texture hitTexture, regTexture;
	
	private int sx, sy;
	
	public TanisBoss(LinkedList<Projectile> lasers, int x, int y) {
		super("res/Enemies/TanisBoss.png", x, y, 300, 400);
		this.lasers = lasers;
		current = DIR.RIGHT;
		sx=x; sy=y;
		regTexture = loadTexture("res/Enemies/TanisBoss.png");
		hitTexture = loadTexture("res/Enemies/TanisHit.png");
		
		health = 250;
		pf = new LaserFactory();
		am = AudioManager.getInstance();
		try {
			am.loadSample("blaster", "res/Sounds/Blaster.ogg");
			am.loadSample("grunt", "res/Sounds/MaleGrunt.ogg");
			am.loadSample("die", "res/Sounds/DyingRobot.ogg");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}
	
	private Texture loadTexture(String path) {
		try {
			return TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private boolean isHit = false;
	public void onCollision(Entity other) {
//		if (other instanceof StickmanTest) {
//			other.reduceHealth(10);
//		}
		if (other instanceof Projectile && !(other instanceof Laser)) { 
			if (other instanceof Butter) {
				reduceHealth(((Butter) other).getDamage()*5);
			} else {
				reduceHealth(((Projectile) other).getDamage()/2);
			}
			if (health > 0) am.play("grunt", .8f);
			other.deactivate();
			if (isHit) isHit = false;
			else isHit = true;
		}
	}
	
	public void reduceHealth(int h) {
		health -= h;
	}
	
	public void shoot(int x, int y, int dir) {
		try {
			lasers.add(pf.newProjectile(x, y, dir));
			am.play("blaster");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	public void traverse(int x, int y) {
		switch (current) {
			case RIGHT:
				if (x+(Display.getWidth()) <= sx + (Display.getWidth()+hitbox.getWidth())) {
					x+=5;
					hitbox.setX(x);
				}
				else if (!isHit) current = DIR.DOWN;
				break;
			case DOWN:
				if (y+hitbox.getHeight() <= sy + Display.getHeight()) {
					y+=5;
					hitbox.setY(y);
				}
				else if (!isHit) current = DIR.LEFT;
				break;
			case LEFT:
				if (x >= sx) {
					x-=5;
					hitbox.setX(x);
				}
				else if (!isHit) current = DIR.UP;
				break;
			case UP:
				if (y >= sy - (Display.getHeight()/5)) {
					y-=5;
					hitbox.setY(y);
				}
				else if (!isHit) current = DIR.RIGHT;
				break;
		}
	}
	
	public void update(float delta) {
		int x = hitbox.getX();
		int y = hitbox.getY();
		int w = hitbox.getWidth();
		int h = hitbox.getHeight();
		
		traverse(x,y);
		
		if (health <= 0) {
			counter += delta;
			if (counter > 3000) {
				counter = 0;
				am.play("die");
				this.deactivate();
			}
		}
		if (isHit) {
			hitTimer += delta;
			texture = hitTexture;
			if (hitTimer > 500) {
				isHit = false;
				hitTimer=0;
				texture = regTexture;
			}
		}
		shotCounter += delta;
		if (shotCounter >= 2500) {
			shoot(x, y+(h/2), -1);
			shotCounter = 0;
		}
	}
	
	public int getX() {
		return hitbox.getX();
	}
}
