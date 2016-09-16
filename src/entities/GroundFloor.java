package entities;
import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GroundFloor extends Entity {
//public class GroundFloor extends Background {
//	
//	public GroundFloor() {
//		
//		String p = "res/Background/Bricks.png";
//		
//		int y = (int) (SCREEN_HEIGHT - (SCREEN_HEIGHT * .15));
//		int w = SCREEN_WIDTH*2;
//		int h = (int) (SCREEN_HEIGHT * 0.95);
//		
//		setSampleTexture(p);
//		setX(0);
//		setY((int) (h-(w*.15)));
//		setW(w);
//		setH((int) (h*.25));
//		setN(10);
//		
////		repeatBackground();
//		
////		Vector2f v = new Vector2f(x, y);
//		
////		drawBackground(sampleTexture, v, )
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static String ground;
	private static String backdrop;
	public static float floorY, floorX;
	
	public GroundFloor(String texturePath, int x, int y, int w, int h) {
		super(texturePath, x, (int) (h-(w*.15)), w, (int) (h*.25));
		floorY = h-(w*.15f);
		floorX = x;
	}
	
	public static void setFX(float x) { floorX = x; } 
	public static float getFloorY() {
		return floorY;
	}
	public static float getFloorX() {
		return floorX;
	}
}
