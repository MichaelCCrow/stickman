package entities.projectiles;
import java.io.IOException;

public class ButterFactory implements ProjectileFactory {

	public Projectile newProjectile(int x, int y, int direction) throws IOException {
		return new Butter(x,y,direction);
	}
}
