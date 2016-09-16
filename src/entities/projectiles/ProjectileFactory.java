package entities.projectiles;
import java.io.IOException;

public interface ProjectileFactory {
	
	Projectile newProjectile(int x, int y, int direction) throws IOException;

}
