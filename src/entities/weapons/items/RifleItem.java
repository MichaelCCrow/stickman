package entities.weapons.items;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

import entities.Entity;
import entities.Stickman;
import entities.weapons.Gun;
import entities.weapons.Rifle;
import entities.weapons.Weapon;
import game.AudioManager;
import game.TextureManager;

public class RifleItem extends Item {
	
	private static String itemPath = "res/Items/RifleItemWide.png";
	
	public RifleItem(int x, int y, int w, int h) {
		super(itemPath, x, y, w, h);
		this.gun = Gun.RIFLE;
	}
	
	
}
