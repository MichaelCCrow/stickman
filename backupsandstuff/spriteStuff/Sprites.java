//package spriteStuff;
//import java.io.IOException;
//
//import org.lwjgl.opengl.GL11;
//import org.newdawn.slick.opengl.Texture;
//import org.newdawn.slick.opengl.TextureLoader;
//import org.newdawn.slick.util.ResourceLoader;
//
//import Entity;
//
//public class Sprites extends Entity {
//	
//	Texture spriteSheet;
//	Texture[][] sprites;
//	Texture sprite;
//	float x0=0;
//	float x1=0;
//	float y0=0;
//	float y1=0;
//	
//	public Sprites(float x, float y, float w, float h) throws IOException {
//		super(x,y,w,h);
//		loadSprites();
//	}
//	
//	public void loadSprites() throws IOException {
//		spriteSheet = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/explosion.png"));
//		parseSprites(spriteSheet);
//	}
//	
//	public void parseSprites(Texture t) {
//
//		for (int i=0; i<24; i++) {
//			for (int j=0; j<24; j++) {
//				x0 = i / 24;
//				x0 = (i+1) / 24;
//				y0 = j / 24;
//				y0 = (j+1) / 24;
//				
//				sprites[i][j] = t;
//				Texture sprite = sprites[i][j];
//				draw();
//			}
//		}
//	}
//	
//	public void setTexture(Texture t) {
//		
//	}
//	
//	public Texture getSprite(Texture[][] t) {
//		return sprite;
//		
//	}
//	
//	public void draw() {
//		
//		float x = hitbox.getX();
//        float y = hitbox.getY();
//        float w = hitbox.getWidth();
//        float h = hitbox.getHeight();
//		
//		GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.getTextureID());
//		GL11.glBegin(GL11.GL_QUADS);
//		{
//		  GL11.glTexCoord2f(x0, y0);
//		  GL11.glVertex2f(x, y);
//		  
//		  GL11.glTexCoord2f(x1, y0);
//		  GL11.glVertex2f(x + w, y);
//		  
//		  GL11.glTexCoord2f(x1, y1);
//		  GL11.glVertex2f(x + w, y + h);
//		  
//		  GL11.glTexCoord2f(x0, y1);
//		  GL11.glVertex2f(x, y + h);			  
//		}
//		GL11.glEnd();
//		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
//	}
//	
//	
//	// in Stickman.update()
//		int c=0; int d=0;
////	s.getSprite(c%5, c/5).draw();
////	if (d++ %  10 ==0)
////	{
////		c++;
////		c = c%25;
////	}
//
//}
