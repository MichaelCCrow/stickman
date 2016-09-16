package game;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureManager {

	private Texture texture;
	private float width_ratio, height_ratio;
	
	public TextureManager(String tPath) {
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tPath));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		width_ratio = (1.0f) * texture.getImageWidth() / texture.getTextureWidth();
		height_ratio = (1.0f) * texture.getImageHeight() / texture.getTextureHeight();
	}
	
	public Texture getTheTexture() {
		return texture;
	}
	
	public void drawAt(float x, float y, float w, float h) {
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y);

		GL11.glTexCoord2f(width_ratio, 0);
		GL11.glVertex2f(x + w, y);

		GL11.glTexCoord2f(width_ratio, height_ratio);
		GL11.glVertex2f(x + w, y + h);

		GL11.glTexCoord2f(0, height_ratio);
		GL11.glVertex2f(x, y + h);

		GL11.glEnd();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
}
