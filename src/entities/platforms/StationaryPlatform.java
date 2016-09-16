package entities.platforms;
import java.util.Random;

import org.lwjgl.opengl.GL11;

public class StationaryPlatform extends Platform {
	
	public StationaryPlatform(int x, int y, int w, int h) {
		super(x,y,w,h);
		
		randomize();
		setColor(r,g,b);
		
	}
}
