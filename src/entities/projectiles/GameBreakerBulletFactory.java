package entities.projectiles;

import java.io.IOException;

public class GameBreakerBulletFactory implements ProjectileFactory {
	
	public Projectile newProjectile(int x, int y, int direction) throws IOException {
		return new GameBreakerBullet(x,y,direction);
	}
}
