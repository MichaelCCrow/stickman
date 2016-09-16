package entities;

import java.util.LinkedList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import game.TextureManager;

public abstract class Background extends Entity {
	
	protected int x, y, w, h, n;
	protected Texture sampleTexture;
	
	public void setSampleTexture(String path) { sampleTexture = loadTexture(path); }
	public Texture getSampleTexture() { return sampleTexture; }
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setW(int w) {
		this.w = w;
	}
	
	public void setH(int h) {
		this.h = h;
	}
	
	public void setN(int n) {
		this.n = n;
	}
	public int getN() {
		return n;
	}
	
	public void setAll(Texture t, int x, int y, int w, int h, int n) {
//		setSampleTexture(path);
		setTexture(t);
		setX(x);
		setY(y);
		setW(w);
		setH(h);
		setN(n);
	}
	
	public void setTexture(Texture sampleTexture) {
		this.texture = sampleTexture;
	}
	
	public LinkedList<Background> llbg = new LinkedList<Background>();
	
	public void repeatBackground(int y, int w, int h, int n) {
		
		for (int i=-1; i<this.n; i++) {
			
			setAll(sampleTexture,SCREEN_WIDTH*i,y,w,h,n);
//			setX(SCREEN_WIDTH * i);
			setTexture(getSampleTexture());
			llbg.add(this);
		}
	}
	
	
	
	
	
	
	
	
	public void drawBackground(Texture texture, Vector2f position, Vector2f translation, Vector2f origin,Vector2f scale, float rotation)
    {
		texture.setTextureFilter(GL11.GL_NEAREST);
		
		texture.bind();
		
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		GL11.glTranslatef((int)position.x, (int)position.y, 0);
		GL11.glTranslatef(-(int)translation.x, -(int)translation.y, 0);
		GL11.glRotated(rotation, 0f, 0f, 1f);
		
		GL11.glScalef(scale.x, scale.y, 1);
		
		GL11.glTranslatef(-(int)origin.x, -(int)origin.y, 0);
		
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(0,0);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(texture.getTextureWidth(),0);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(texture.getTextureWidth(),texture.getTextureHeight());
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(0,texture.getTextureHeight());
		
		GL11.glEnd();
		
		GL11.glLoadIdentity();
    }
	
	
	
	
	
	// Helper method
	public Texture loadTexture(String path) {
		return new TextureManager(path).getTheTexture();
	}
}
