package entities.weapons.items;

import entities.weapons.Gun;

public class PistolItem extends Item {

	private static String tpath = "res/Items/PistolItem.png";
	
	public PistolItem(int x, int y, int w, int h) {
		super(tpath, x, y, w, h);
		this.gun = Gun.PISTOL;
	}
}
