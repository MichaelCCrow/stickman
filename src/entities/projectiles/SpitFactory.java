package entities.projectiles;

import java.io.IOException;

public class SpitFactory implements ProjectileFactory {
	
	public Projectile newProjectile(int x, int y, int direction) throws IOException {
		return new Spit(x, y, direction);
	}

}
