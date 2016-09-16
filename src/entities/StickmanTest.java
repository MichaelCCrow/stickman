package entities;

import entities.platforms.Platform;
import entities.projectiles.Laser;
import entities.projectiles.Projectile;
import entities.projectiles.Spit;
import entities.weapons.Gun;
import entities.weapons.Unarmed;
import entities.weapons.Weapon;
import entities.weapons.WeaponFactory;
import entities.weapons.items.Item;
import entities.weapons.items.RifleItem;
import entities.weapons.Weapon.Direction;
import game.AudioManager;
import scenes.InventoryMenu;
import scenes.Menu;
import scenes.Scene;

import org.newdawn.slick.SlickException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class StickmanTest extends Entity {

	Gun gun; Gun firstGun = Gun.UNARMED;
	public static WeaponFactory wf;
	Weapon weapon;
	private LinkedList<Projectile> bullets;
	private static String texturePath = "res/PlayerImages/Stickman64x64.png";
	public boolean gravity = false;
	private Vector2f velocity;
	private float mass;
	public static LinkedList<Gun> inventory;
	
	public int health; 
	
	
	public StickmanTest(LinkedList<Projectile> bullets, int x, int y, int  w, int h) throws IOException, SlickException {
		super(texturePath, x,y,w,h);
		
		this.bullets = bullets;
		wf = new WeaponFactory();
		weapon = new Unarmed();
		gun = firstGun;

		loadAudio();
		velocity = new Vector2f(0,0);
		mass = 4;
		health = 100;
		
		inventory = new LinkedList<>();
		addToInventory(Gun.UNARMED);
		addToInventory(Gun.PISTOL);
	}
	
	public void loadAudio() throws IOException {
		am = AudioManager.getInstance();
		am.loadSample("shells", "res/Sounds/ShellsFalling.ogg");
		am.loadSample("equipSound", "res/Sounds/GunCocking.ogg");
		am.loadSample("hitwithspit", "res/Sounds/HitWithSpit.ogg");
	}
	
	public static void addToInventory(Gun g) {
		inventory.add(g);
		System.out.println(g.toString() + " added to inventory");
	}
	public static void seeInvList() {
		for (Gun gun : inventory) {
			System.out.println(gun.toString() + " is in inventory");
		}
	}
	
	public void reduceHealth(int h) {
		health -= h;
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
		
		if (other instanceof Spit) {
			am.play("hitwithspit");
			reduceHealth(((Spit) other).getDamage());
			other.deactivate();
		}
		
		if (other instanceof Laser) {
			am.play("hitwithspit");
			reduceHealth(((Laser) other).getDamage());
			other.deactivate();
		}
		
//		if (other instanceof Item) {
//			if (other.isActive()) {
//				am.play("equipSound");
////				inventory.add(Item.gun);
////				System.out.println(Item.gun.toString());
//			}
//			other.deactivate();
//		}
	}
	
	public boolean dead = false;
	public boolean dead() {
		dead = true; return dead;
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
				if (Weapon.isEquipped) {
					weapon.setDirection(Direction.RIGHT);
					texture = Direction.RIGHT.face();
				}
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				
				x -= delta / 2;
				if (Weapon.isEquipped) {
					weapon.setDirection(Direction.LEFT);
					texture = Direction.LEFT.face();
				}
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_UP) && velocity.getY()==0) { // y -= delta / 2;
					
				Vector2f.add(extraForce, new Vector2f(0f, -.4f), extraForce); // force is going up
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) { y += delta / 2; }
			
			// water gun functionality
			// if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			// if (Weapon.isEquipped) { weapon.shoot(bullets,x,y,w,h); }}
			
		// end keyboard inputs
			
			
		
		extraForce.scale(delta/mass);
		Vector2f.add(velocity, extraForce, velocity);
		x += velocity.getX()*delta;
		y += velocity.getY()*delta;
		
		// get the remaining Keyboard inputs
		getInput(x,y,w,h,delta);
		
		gravity = true;
		hitbox.setLocation(x, y);
		
		Display.setTitle("Health: " + health);
		if (health <= 0) {
			System.exit(1);
		}
		
	} // end update
	
	public void openMenu() {
		
		InventoryMenu invMenu = new InventoryMenu();
		for (Gun g : inventory) {
			invMenu.addItem(g.name(), g);
		}
		invMenu.updatePosition(hitbox.getX());
		invMenu.go();
		try {
			selectGunFromMenu(invMenu.getSelection());
			invMenu.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public void selectGunFromMenu(Gun selection) throws IOException {
		weapon = wf.equip(selection);
		texture = Direction.RIGHT.face();
		System.out.println(selection.name() + " equipped.");
	}
	
	boolean returnToMenu = false;
	public void returnToMenu(boolean b) {
		this.returnToMenu = b;
	}
	public boolean testReturnToMenu() {
		return returnToMenu;
	}
	
	public void getInput(int x, int y, int w, int h, float delta) throws IOException {
		
		while (Keyboard.next()) {
	        if (Keyboard.getEventKeyState()) {
	        	
	        	switch (Keyboard.getEventKey()) {
	            	
	        		case Keyboard.KEY_ESCAPE: System.exit(1); break;
	        		case Keyboard.KEY_0: System.exit(1); break;
	        	}
//
//	        		case Keyboard.KEY_A:
//	        			while (!inventory.contains(gun)) gun = gun.next();
//	        			if (inventory.contains(gun)) {	            		
//	        				weapon = wf.equip(gun);
//	        				texture = Direction.RIGHT.face();
//	        				gun = gun.next();
//	        			}
//	        			System.out.println(gun.name() + " equipped.");
//	        		  break;
//	        			
//	            	case Keyboard.KEY_SPACE:
//	            		System.out.println("Space Key Pressed");
//	            		weapon.shoot(bullets,x,y,w,h);        		
//	            	  break;
//	            	  
//	            	case Keyboard.KEY_M:	            		
//	            		System.out.println("M Key Pressed");
//	            		seeInvList();
//	            		openMenu().nextScene();
//	            	  break;
//	            }
	            if (Keyboard.getEventKey() == Keyboard.KEY_A) {
	            	
	            	gun = gun.next();
	            	while (!inventory.contains(gun)) gun = gun.next();
	            	
	            	if (inventory.contains(gun)) {	            		
	            		weapon = wf.equip(gun);
	            		texture = Direction.RIGHT.face();
	            		System.out.println(gun.name() + " equipped.");	            		
	            	}
	            }
	            
	            if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
	            	
	            	System.out.println("Space Key Pressed");
	            	weapon.shoot(bullets,x,y,w,h);
	            	
	            }
	            
	            if (Keyboard.getEventKey() == Keyboard.KEY_M) { 
	            	System.out.println("M Key Pressed");
	            	seeInvList();
	            	openMenu();
	            }
	            
	            
	    	    if (Keyboard.getEventKey() == Keyboard.KEY_Q) {
	    	        	System.out.println("Q Key Pressed");
	    	        	returnToMenu(true);
	    	        
	        	}

	            
	            if (Keyboard.getEventKey() == Keyboard.KEY_D) { System.out.println("D Key Pressed"); }
	            
	        } else {
	        	
	            if (Keyboard.getEventKey() == Keyboard.KEY_A) { System.out.println("A Key Released"); }
	            
	            if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {	
	            	if (gun==Gun.PISTOL) am.play("shells");
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