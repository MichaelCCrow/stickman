package entities.weapons.items;

import entities.weapons.Gun;

public class GameBreakerItem extends Item {

	private static String tpath = "res/Items/GameBreakerItem.png";
	
	public GameBreakerItem(int x, int y, int w, int h) {
		super(tpath, x, y, w, h);
		this.gun = Gun.GAMEBREAKER;
	}
}
