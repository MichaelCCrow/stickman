package entities;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entities.platforms.Platform;
import entities.projectiles.BulletFactory;
import entities.projectiles.ButterFactory;
import entities.projectiles.LaserFactory;
import entities.projectiles.Projectile;
import entities.projectiles.ProjectileFactory;
import game.AudioManager;

import org.newdawn.slick.SlickException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import java.io.IOException;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Stickman extends Entity {

	private enum Gun { PISTOL, RIFLE, BUTTER }; Gun gun; Gun firstGun = Gun.BUTTER; 
	private enum Direction { LEFT, RIGHT }; Direction dir = Direction.RIGHT;
	private LinkedList<Projectile> bullets;
	int direction;
	private Texture pistolRight, pistolLeft, rifleRight, rifleLeft, butterRight, butterLeft; 
	String pr = "res/PlayerImages/Pistol64x64.png"; 	String pl = "res/PlayerImages/PistolLeft64x64.png"; 
	String rr = "res/PlayerImages/Rifle64x64.png";		String rl = "res/PlayerImages/RifleLeft64x64.png";
	String br = "res/PlayerImages/ButterGun64x64.png"; 	String bl = "res/PlayerImages/ButterGunLeft64x64.png";
	private static String texturePath = "res/PlayerImages/Stickman64x64.png";
	private boolean gunEquipped = false; 
	public boolean gravity = false;
	private Vector2f velocity;
	private float mass;
	private ProjectileFactory pfactory;	
	boolean o = true; // Tests orientation: true=right, false=left -> REQUIRED for Projectile origins.
	
	
	public Stickman(LinkedList<Projectile> bullets, int x, int y, int  w, int h) throws IOException, SlickException {
		super(texturePath, x,y,w,h);
		this.bullets = bullets;
		this.direction = 1;
		loadTextures(); loadAudio();
		velocity = new Vector2f(0,0);
		mass = 4;
		gun = firstGun;
	}
	
	public Texture loadTexture(String tPath) throws IOException {
		return TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tPath));
	}
	public void loadTextures() throws IOException {
		pistolRight = loadTexture(pr);	pistolLeft = loadTexture(pl);
		rifleRight =  loadTexture(rr);	rifleLeft =  loadTexture(rl);
		butterRight = loadTexture(br);	butterLeft = loadTexture(bl);
	}
	public void loadAudio() throws IOException {
		am = AudioManager.getInstance();
		am.loadSample("gunshot", "res/Sounds/gunshot.ogg");
		am.loadSample("blaster", "res/Sounds/Blaster.ogg");
		am.loadSample("shells", "res/Sounds/ShellsFalling.ogg");
		am.loadSample("equip", "res/Sounds/GunCocking.ogg");
		am.loadSample("butter", "res/Sounds/Cartoon_Point.ogg");
	}
	
	private void equippedGun() { gunEquipped = true; }
	private boolean gunIsEquipped() { return gunEquipped; }
	
	public void equip(Gun gun) {
		switch(gun) {
			case PISTOL:	texture = pistolRight;
							pfactory = new BulletFactory(); 
							break;
							
			case RIFLE:		texture = rifleRight;
							pfactory = new LaserFactory();
							break;
							
			case BUTTER:	texture = butterRight;
							pfactory = new ButterFactory();
							break;
		}
		am.play("equip");
		equippedGun();
	}
	
	public void switchGun() {
    	switch (gun) {
    		case PISTOL:	gun = Gun.RIFLE; break;
    		case RIFLE: 	gun = Gun.BUTTER; break;
    		case BUTTER:	gun = Gun.PISTOL; break;
    		
    		default:	pfactory = new BulletFactory(); 
    					gun = Gun.PISTOL; 
    					break;
    	}	
    	equip(gun);
	}
	
	public void switchDirection() {
		switch (dir) {
			case LEFT: 
				switch (gun) {
					case PISTOL: texture = pistolLeft; break;
					case RIFLE:  texture = rifleLeft; break;
					case BUTTER: texture = butterLeft; break;
				} break;
			case RIGHT:
				switch (gun) {
					case PISTOL: texture = pistolRight; break;
					case RIFLE:  texture = rifleRight; break;
					case BUTTER: texture = butterRight; break;
				} break;
		}		
	}
	
	
	public void onCollision(Entity other) {
		
		if (other instanceof Platform) {

            Rectangle overlap = intersection(other);
            float x =footbox.getX();
            float y =footbox.getY();
            double width = overlap.getWidth();
            double height = overlap.getHeight();
            
            // if (height > width)
                // x-=width;
                // velocity.setX(0); 
            
            if (velocity.getY() > 0) {
            	
                y -= height;
                velocity.setY(0);
            }
            gravity = false;
            footbox.setLocation((int)x,(int)y);
        }
	}
	
	
	public void update(float delta) throws IOException {
		int x = hitbox.getX();
		int y = hitbox.getY();
		int w = hitbox.getWidth();
		int h = hitbox.getHeight();
		
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
		
		// begin keyboard inputs
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				
				x += delta / 2;
				direction = 1; dir = Direction.RIGHT;
				if (gunIsEquipped()) { switchDirection(); }
				o=true;
				
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				
				x -= delta / 2;
				direction = -1; dir = Direction.LEFT;
				if (gunIsEquipped()) { switchDirection(); }
				o=false;
				
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_UP) && velocity.getY()==0) { // y -= delta / 2;
					
					Vector2f.add(extraForce, new Vector2f(0f, -.3f), extraForce); // force is going up
					
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) { y += delta / 2; }
			
			// water gun functionality
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				if (gunIsEquipped()) {
					if (o){	// bullets.add(pfactory.newProjectile((int) (x+(w-43)), (int) (y+(h*.44f)), direction));
						if (gun != Gun.BUTTER) {
							bullets.add(pfactory.newProjectile((int) (x+(w-15)), (int) (y+(h*.3f)), direction));
						} else {
							bullets.add(pfactory.newProjectile((int) (x+(w-5)), (int) (y+(h*.3f)), direction));						
						}
						
					} else { // bullets.add(pfactory.newProjectile((int) (x+24), (int) (y+(h*.44f)), direction));
						if (gun != Gun.BUTTER) {
							bullets.add(pfactory.newProjectile((int) (x-5), (int) (y+(h*.3f)), direction));
						} else {
							bullets.add(pfactory.newProjectile((int) (x-35), (int) (y+(h*.3f)), direction));						
						}
					}
				}
			}
		// end keyboard inputs
			
			
		
		extraForce.scale(delta/mass);
		Vector2f.add(velocity, extraForce, velocity);
		x += velocity.getX()*delta;
		y += velocity.getY()*delta;
		
		// get the remaining Keyboard inputs
		getInput(x,y,w,h,delta);
		gravity = true;
		hitbox.setLocation(x, y);
		
	} // end update
	
	
	
	public void getInput(float x, float y, float w, float h, float delta) {
		
		while (Keyboard.next()) {
	        if (Keyboard.getEventKeyState()) {
	        	
	            if (Keyboard.getEventKey() == Keyboard.KEY_A) {

	            	System.out.println(gun.name() + " equipped."); 
	            	
	            	switchGun();
	            	
	            	o = true; equippedGun(); direction = 1;
	            	
	            	am.play("equip");
	            	
	            }
	            
	            if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
	            	System.out.println("Space Key Pressed");
	            	if (gunIsEquipped()) {
	            	if (o) { // facing right
						try {
							
							if (gun != Gun.BUTTER) {
								bullets.add(pfactory.newProjectile((int) (x+(w-15)), (int) (y+(h*.3f)), direction));
							} else {
								bullets.add(pfactory.newProjectile((int) (x+(w-5)), (int) (y+(h*.3f)), direction));						
							}
							
						} catch (IOException e) {
							e.printStackTrace();
						} 
					} 
	            	else { // facing left
						try {
							
							if (gun != Gun.BUTTER) {
								bullets.add(pfactory.newProjectile((int) (x-5), (int) (y+(h*.3f)), direction));
							} else {
								bullets.add(pfactory.newProjectile((int) (x-35), (int) (y+(h*.3f)), direction));						
							}
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
	            	am.play(audioPath, 0.8f);        		
	            }}
	            
	            if (Keyboard.getEventKey() == Keyboard.KEY_D) { System.out.println("D Key Pressed"); }
	        } else {
	            if (Keyboard.getEventKey() == Keyboard.KEY_A) { System.out.println("A Key Released"); }
	            
	            if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {	
	            	System.out.println("Space Key Released");
	            	if (gunIsEquipped() && gun==Gun.PISTOL) {
	            		am.play("shells");	            		
	            	}
	            }
	            
	            if (Keyboard.getEventKey() == Keyboard.KEY_D) { System.out.println("D Key Released"); }
	            
	            
	        } // end else
	    } // end while
	} // end getInput()

	public int getX() {
		return hitbox.getX();
	}
	public int getY() {
		return hitbox.getY();
	}
} // end Stickman