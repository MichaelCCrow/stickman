package entities.projectiles;
import java.io.IOException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class Bullet extends Projectile {

	protected static final int WIDTH=20;
    protected static final int HEIGHT=10;
    private static final float SPEED=.8f;
    public static final int DAMAGE = 4;
	
	public Bullet(int x, int y, int direction) throws IOException {
		super(x, y, WIDTH, HEIGHT, direction);
		
		velocity = new Vector2f(direction*SPEED, -.002f);
		audioPath = "gunshot";
		setColor(1,0,0); // red
		
		setDamage(DAMAGE);
	}	
}
