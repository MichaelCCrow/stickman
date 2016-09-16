package entities.weapons;

import java.io.IOException;
import java.util.LinkedList;

import org.lwjgl.Sys;

import entities.projectiles.ButterFactory;
import entities.projectiles.GameBreakerBulletFactory;
import entities.projectiles.Projectile;
import entities.weapons.Weapon.Direction;

public class GameBreaker extends Weapon {
	
	public static boolean shot;
	public GameBreaker() throws IOException {
		
		// setGun(Gun.GAMEBREAKER);
		shot = false;
		this.rightPath = "res/PlayerImages/GameBreaker.png";
		this.leftPath = "res/PlayerImages/GameBreakerLeft.png";
		
		String sound = "butter";
		String soundPath = "res/Sounds/Cartoon_Point.ogg";
		
		setLeft(leftPath);
		setRight(rightPath);
		
		setAudio(sound, soundPath);
		setAmmoFactory(new GameBreakerBulletFactory());
	}
	int counter=0;
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
			shot=true;
			
//			if (counter < 10000) {
//				counter += Sys.getTime()*1000/Sys.getTimerResolution(); 
//			} else if (counter > 10000) System.exit(1);	
		}
		
		
	}
	

}
