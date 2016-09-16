package game;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;

import java.io.IOException;

import org.lwjgl.LWJGLException;

import scenes.*;


import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.LWJGLException;


public class Game {

    public static void main(String[] args) throws LWJGLException, IOException, SlickException {

        initGL(1024, 768);

        Menu gameMenu = new Menu();
        gameMenu.addItem("LevelOne", new LevelOne());
        gameMenu.addItem("AStarTest", new AStarTest());
        gameMenu.addSpecial("Exit", Menu.DO_EXIT);

        Scene currScene = gameMenu;

        while ( currScene.go()  )
        {
             // if nextScene() returns null (the default) reload the menu
            currScene = currScene.nextScene();

            if (currScene == null)
            {
                currScene = gameMenu;
            }

            System.out.println("Changing Scene: " + currScene);
        }


        Display.destroy();
        AudioManager.getInstance().destroy();
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
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);         

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


//public class Game {
//	private static AudioManager am;
//
//    public static void main(String[] args) throws LWJGLException, IOException, SlickException {
//    	
//
//        initGL(800, 600);
//        // am = AudioManager.getInstance();
//        // am.loadSample("fighttosurvive", "res/Sounds/Bloodsport - Fight to Survive.ogg");
//        // am.play("fighttosurvive", .4f);
//        // new LevelOne().go();
//        // am.destroy();
//        
////        Menu gameMenu = new Menu();
////        gameMenu.addItem("Scene1", new LevelOne());
////        gameMenu.addSpecial("Exit", Menu.DO_EXIT);
////        
////        Scene currScene = gameMenu;
//        
//        new AStarTest().go();
//    }