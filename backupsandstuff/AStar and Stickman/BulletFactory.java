import java.io.IOException;

public class BulletFactory implements ProjectileFactory {
	
	public Projectile newProjectile(int x, int y, int direction) throws IOException {
		return new Bullet(x,y,direction);
	}
}
