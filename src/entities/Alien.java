package entities;

import java.io.IOException;
import java.util.LinkedList;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import entities.projectiles.Projectile;
import entities.projectiles.ProjectileFactory;
import entities.projectiles.Spit;
import entities.projectiles.SpitFactory;
import game.AudioManager;

public class Alien extends Entity {
	
	static String tpath = "res/Enemies/AlienThing.png";
	
	protected boolean gravity = true;
	private Vector2f velocity;
	private float mass;
	private LinkedList<Projectile> spit;
	private ProjectileFactory pf;
	private int health;
	
	private float counter=0;
	private float fcount=0;
	private AudioManager am;
	
	public Alien(LinkedList<Projectile> spit, int x, int y) throws IOException {
		super(tpath, x, y, 100, 100);
		
		velocity = new Vector2f(0,0);
		mass = 4;
		health = 10;
		this.spit = spit;
		pf = new SpitFactory();
		am = AudioManager.getInstance();
		am.loadSample("spit", "res/Sounds/Spitting.ogg");
		am.loadSample("hit", "res/Sounds/Jab.ogg");
		am.loadSample("squish", "res/Sounds/Squish.ogg");
	}
	
	public void shoot(int x, int y, int dir) throws IOException {
		this.spit.add(pf.newProjectile(x, y, dir));
		am.play("spit");
	}
	
	public void onCollision(Entity other) {
//		if (other instanceof StickmanTest) {
//			other.reduceHealth(1);
//		}
		if (!(other instanceof Spit) && (other instanceof Projectile)) { 
			reduceHealth(((Projectile) other).getDamage());
			if (health > 0) am.play("hit", .8f);
			other.deactivate();
		}
	}

	public void reduceHealth(int h) {
		health -= h;
	}

	public void jump(Vector2f extraForce) {

		if (this.isActive() && velocity.getY()==0) {
			Vector2f.add(extraForce, new Vector2f(0f, -.4f), extraForce); // force is going up
		}
	}
	
	public int getX() {
		return hitbox.getX();
	}
	
	public void update(float delta) {
		int x = hitbox.getX();
		int y = hitbox.getY();
		int w = hitbox.getWidth();
		int h = hitbox.getHeight();
		counter+=delta;
		
        if (y < 0) {
            y = 0;
            velocity.setY(0);
        }
        if (y > Display.getHeight() - h) {
            y = (int) (Display.getHeight() - h);
            velocity.setY(0);
        }
        
		Vector2f extraForce = new Vector2f(0,0);
		
		// apply gravity
		if (gravity) {
			Vector2f.add(extraForce, 
					(Vector2f) new Vector2f(0, .005f).scale(delta/mass), 
					extraForce);
		}

		if (fcount < 1000) { fcount+=delta; }
		else if (fcount > 1000) {
			jump(extraForce);
		}
		
		if (counter > 1500) {
			counter=0;
			try { shoot(x,y,-1); }
			catch (IOException e) { throw new RuntimeException(e); }
		}
		
		extraForce.scale(delta/mass);
		Vector2f.add(velocity, extraForce, velocity);
		x += velocity.getX()*delta;
		y += velocity.getY()*delta;
		
		gravity = true;
		hitbox.setLocation(x, y);
		
		if (health <= 0) {
			am.play("squish");
			this.deactivate();
		}
		// System.out.println(health);
	}
}
