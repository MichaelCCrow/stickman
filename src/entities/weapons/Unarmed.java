package entities.weapons;

import java.io.IOException;
import java.util.LinkedList;

import entities.projectiles.Bullet;
import entities.projectiles.BulletFactory;
import entities.projectiles.Projectile;

public class Unarmed extends Weapon {
	
	
	public Unarmed() {
		
		// setGun(Gun.UNARMED);
		
		Weapon.isEquipped = false;
		
		this.rightPath = "res/PlayerImages/Stickman64x64.png";
		this.leftPath = rightPath;
		
		setLeft(leftPath);
		setRight(rightPath);
		
	}
	
}
