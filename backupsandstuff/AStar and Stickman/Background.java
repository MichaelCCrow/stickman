import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Background extends Entity {
	
	private static String ground;
	private static String backdrop;
	public static float floorY;
	
	public Background(String texturePath, int x, int y, int w, int h) {
		super(texturePath, x, (int) (h-(w*.15)), w, (int) (h*.25));
		floorY = h-(w*.15f);
	}
	
	public static float getFloorY() {
		return floorY;
	}
}
