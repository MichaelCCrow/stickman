package entities.weapons;

import java.io.IOException;
import java.util.LinkedList;

import entities.projectiles.ButterFactory;
import entities.projectiles.Projectile;
import entities.weapons.Weapon.Direction;

public class ButterGun extends Weapon {

	public ButterGun() throws IOException {
		
		// setGun(Gun.BUTTER);
		
		this.rightPath = "res/PlayerImages/ButterGun64x64.png";
		this.leftPath = "res/PlayerImages/ButterGunLeft64x64.png";
		
		String sound = "butter";
		String soundPath = "res/Sounds/Cartoon_Point.ogg";
		
		setLeft(leftPath);
		setRight(rightPath);
		
		setAudio(sound, soundPath);
		setAmmoFactory(new ButterFactory());
	}
	
	public void shoot(LinkedList<Projectile> ammo, int xx, int yy, int w, int h) throws IOException {
		
		this.ammo = ammo;
		int x = (int) (xx+(w-5));
		int y = (int) (yy+(h*.3f));
		
		if (d == Direction.LEFT) {
			x = xx-35;
		}
		
		if (isEquipped) {
			ammo.add(pfactory.newProjectile(x, y, intdir));
			amGunSound.play(gunNoise);
		}
		
	}
}
