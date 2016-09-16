package entities;
import java.io.IOException;
import java.util.LinkedList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
//import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entities.weapons.Gun;
import game.AudioManager;

public abstract class Entity {

	protected Rectangle hitbox, box, footbox;
	protected AudioManager am;
	protected static String audioPath;
	boolean active;
	protected Texture texture;
	protected float width_ratio, height_ratio;
	public static final int SCREEN_HEIGHT = Display.getHeight();
	public static final int SCREEN_WIDTH = Display.getWidth();
	public boolean movePlatforms = false;

	public Entity() {
		hitbox = new Rectangle(); // empty rectangle
		active = true;
	}
	public Entity(int x, int y, int w, int h) {
		hitbox = new Rectangle(x, y, w, h);
		active = true;
	}
	
	public Entity(String texturePath, int x, int y, int w, int h) {
		
		hitbox = new Rectangle(x, y, w, h);
		active = true;
		
		try {
			
			texture = TextureLoader.getTexture("PNG", 
					ResourceLoader.getResourceAsStream(texturePath));
			
			width_ratio = (1.0f) * texture.getImageWidth() / texture.getTextureWidth();
			height_ratio = (1.0f) * texture.getImageHeight() / texture.getTextureHeight();
			System.out.println("ImgHeight: " + texture.getImageHeight() + " ImgWidth: " + texture.getImageWidth());
			// box = new Rectangle(0, 0, w, (int) (w * (float) texture.getImageHeight() / texture.getImageWidth()));
			footbox = new Rectangle(0, y+h, w, 1);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("failed to load image");
			System.exit(-1);
		}
	}
	public Entity(Texture texture, int x, int y, int w, int h) {
		
		hitbox = new Rectangle(x, y, w, h);
		active = true;
			
			width_ratio = (1.0f) * texture.getImageWidth() / texture.getTextureWidth();
			height_ratio = (1.0f) * texture.getImageHeight() / texture.getTextureHeight();
			System.out.println("ImgHeight: " + texture.getImageHeight() + " ImgWidth: " + texture.getImageWidth());
			footbox = new Rectangle(0, y+h, w, 1);
			
	}
	

	public void init() {}

	public void destroy() {}
	
	public void drawAt(float x, float y, float dx, float dy) {}
	public void drawAt(int x, int y, int dx, int dy) {}

	public void update(float delta) throws IOException {}

	public void draw() {
		float x = hitbox.getX();
        float y = hitbox.getY();
        float w = hitbox.getWidth();
        float h = hitbox.getHeight();
		
		// make the loaded texture the active texture for the OpenGL context
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		// GL11.glColor3f(1,1,1); // interacts with color3f
		GL11.glColor3f(1, 1, 1); // interacts with color3f

		GL11.glBegin(GL11.GL_QUADS);

		// top-left of texture tied to top-left of box
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y);

		// top-right
		GL11.glTexCoord2f(width_ratio, 0);
		GL11.glVertex2f(x + w, y);

		// bottom-right
		GL11.glTexCoord2f(width_ratio, height_ratio);
		GL11.glVertex2f(x + w, y + h);

		// bottom-left
		GL11.glTexCoord2f(0, height_ratio);
		GL11.glVertex2f(x, y + h);

		GL11.glEnd();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public boolean testCollision(Entity other) {
		
		if (hitbox.intersects(other.hitbox)) {
			
			onCollision(other);
			return true;
			
		} else {
			
			return false;
			
		}
	}

	public boolean intersects(Entity other)
	{
        return hitbox.intersects(other.hitbox);
    }

    public Rectangle intersection(Entity other)
    {
        Rectangle rval = new Rectangle();
        return hitbox.intersection(other.hitbox, rval);
    }
	
	public void onCollision(Entity other) {}
	
	public boolean isOutOfBounds(float x, float y) {
		
		if (x < -100 || (x+(hitbox.getWidth()/2)) > SCREEN_WIDTH) return true;
		if (y < -100 || (y+(hitbox.getHeight()/2)) > SCREEN_HEIGHT*.9) return true;
		return false;
		
	}
	
	public void reduceHealth(int d) {}
	
	public void activate() {
		active = true;
	}
	
	public boolean isActive() {
		return active;
	}

	public void deactivate() {
		active = false;
	}

}
