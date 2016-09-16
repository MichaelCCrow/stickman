package entities.weapons;

import java.io.IOException;

import entities.weapons.Gun;
import game.AudioManager;

public class WeaponFactory {
	
	private String equipSound = "res/Sounds/GunCocking.ogg";
	private AudioManager gs = AudioManager.getInstance();
	Gun g;
	
	public WeaponFactory() {
		try {
			gs.loadSample("equip", equipSound);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Weapon equip(Gun gun) throws IOException {
		
		Weapon.isEquipped = true;
		gs.play("equip");
		
		switch(gun) {
			
			case UNARMED: 
				return new Unarmed();
			
			case PISTOL:
				return new Pistol();
				
			case RIFLE:
				return new Rifle();
				
			case BUTTER:
				return new ButterGun();
				
			case GAMEBREAKER:
				return new GameBreaker();
				
			  default: 
				return new Unarmed();
		}
	}

}
