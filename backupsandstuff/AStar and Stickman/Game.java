import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;

import java.io.IOException;

import org.lwjgl.LWJGLException;


public class Game {
	private static AudioManager am;

    public static void main(String[] args) throws LWJGLException, IOException, SlickException {
    	

        initGL(800, 600);
        // am = AudioManager.getInstance();
        // am.loadSample("fighttosurvive", "res/Sounds/Bloodsport - Fight to Survive.ogg");
        // am.play("fighttosurvive", .4f);
        // new LevelOne().go();
        // am.destroy();
        
        new AStarTest().go();
    }


    public static void initGL(int width, int height) throws LWJGLException
    {
        // open window of appropriate size
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.create();
        Display.setVSyncEnabled(true);
        
        // enable 2D textures
        GL11.glEnable(GL11.GL_TEXTURE_2D);              
     
        // set "clear" color to black
//         GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);         
        GL11.glClearColor(0.0f, 0.0f,0.0f, 0.0f);         

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
         
        // set viewport to entire window
        GL11.glViewport(0,0,width,height);
         
        // set up orthographic projectionr
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        // GLU.gluPerspective(90f, 1.333f, 2f, -2f);
        // GL11.glTranslated(0, 0, -500);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
}
