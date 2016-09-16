package entities.weapons.items;

import entities.weapons.Gun;

public class UnarmedItem extends Item {

	private static String tpath = "res/Items/Hand.png";
	
	public UnarmedItem(int x, int y, int w, int h){
		super(tpath, x, y, w, h);
		this.gun = Gun.UNARMED;
	}
}
