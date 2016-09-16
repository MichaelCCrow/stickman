package entities.weapons.items;

import entities.weapons.Gun;

public class ButterGunItem extends Item {

	private static String tpath = "res/Items/ButterGunItemWide.png";
	
	public ButterGunItem(int x, int y, int w, int h) {
		super(tpath, x, y, w, h);
		this.gun = Gun.BUTTER;
		
	}
}
