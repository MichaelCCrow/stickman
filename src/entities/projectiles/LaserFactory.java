package entities.projectiles;
import java.io.IOException;

public class LaserFactory implements ProjectileFactory {
	
	public Projectile newProjectile(int x, int y, int direction) throws IOException {
		return new Laser(x,y,direction);
	}
}
