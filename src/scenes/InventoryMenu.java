package scenes;

import java.util.List;
import java.util.LinkedList;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import java.awt.Font;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import entities.StickmanTest;
import entities.weapons.Gun;
import game.AudioManager;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import java.io.IOException;

public class InventoryMenu extends Scene {

	public Gun selection;
	public int xpos;
    // a menu item: label and associated Scene to jump to
    private static class Item
    {
        public String label;
//        public Scene  scene;
        public Gun gun;
        public Item(String label, Gun s)
        {
            this.label = label;
            this.gun = s;
        }

    }

    public static final int DO_EXIT=0;

    private static class Special extends Item
    {
    	public Gun gun;
        public Special(String label, Gun gun)
        {
            super(label, null);
            this.gun = gun;
        }
    }


    // these menu items
    private List<Item> items;

    // currently selected items
    private int currItem;


    public InventoryMenu()
    {
        items = new LinkedList<>();

        try {
        	AudioManager.getInstance().loadSample("select", "res/Sounds/Dry_Fire_Gun.ogg");
        	AudioManager.getInstance().loadSample("choose", "res/Sounds/shotgun-old_school.ogg");
        } catch (IOException e) {
        	throw new RuntimeException(e);
        }
    }

    // reset menu
    public void clear()
    {
        items.clear();
    }

    public void addItem(String label, Gun s)
    {
        items.add(new Item(label,s));
    }
    
    public void addSpecial(String label, Gun tag)
    {
        items.add(new Special(label, tag));
    }


    public Scene nextScene()
    {
    	return null;
    }

    public Gun getSelection() {
    	return selection;
    }
    public void updatePosition(int xpos) {
    	this.xpos = xpos;
    }

    public boolean drawFrame(float delta)
    {

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(.6f, .1f, 0, 0);
        

        // process keyboard input
        while (Keyboard.next())
        {
            if (Keyboard.getEventKeyState()) // key was pressed
            {
                switch (Keyboard.getEventKey())
                {
                 case Keyboard.KEY_DOWN:
                     currItem = (currItem + 1) % items.size();

                     AudioManager.getInstance().play("select");
                     

                     break;

                 case Keyboard.KEY_UP:

                     currItem--;

                     if (currItem < 0)
                     {
                         currItem += items.size(); // go to end
                     }

                     AudioManager.getInstance().play("select");

                     break;

                 case Keyboard.KEY_RETURN:

                     Item item = items.get(currItem);

                     this.selection = item.gun;
                     exit();

                     GL11.glClearColor(1, 1, 1, 1);
                     
                     AudioManager.getInstance().play("choose");

                     return false;

                }
            }
        }


        float spacing = Display.getHeight()/(items.size()+3);
        float offset = 2*spacing;
        
        float correctMenuX = xpos - (Display.getWidth()/8);
        
        entities.weapons.items.Item it = null;
        String path = "";
        for (int i=0; i<items.size(); i++)
        {
            if (i == currItem) { 
            	offset += 10;
            	GL11.glPushMatrix();
            	GL11.glScalef(1.2f, 1f, 0);
            }
            else {
            	offset=offset;
            }
            switch (items.get(i).gun) {
            	case UNARMED: it = new entities.weapons.items.UnarmedItem((int)correctMenuX, (int)offset, 150, 150); it.draw(); break;
            	case PISTOL: it = new entities.weapons.items.PistolItem((int)correctMenuX, (int)offset, 150, 150); it.draw(); break;
            	case RIFLE:	 it = new entities.weapons.items.RifleItem((int)correctMenuX, (int)offset, 140, 75); it.draw(); break;
            	case BUTTER: it = new entities.weapons.items.ButterGunItem((int)correctMenuX, (int)offset, 200, (200/3)); it.draw(); break;
            	case GAMEBREAKER: it = new entities.weapons.items.GameBreakerItem((int)correctMenuX, (int)offset, 150, 75); it.draw(); break;
            	default:
            		break;
            }
            GL11.glPopMatrix();
            
            offset += spacing;
        }
        
        
//        TrueTypeFont menuFont = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), true);
        TrueTypeFont menuFont = new TrueTypeFont(new Font("Stencil", Font.BOLD, 32), true);
        float spacing1 = Display.getHeight()/(items.size()+4);
        float offset1 = 2*spacing1;
        
        for (int i=0; i<items.size(); i++)
        {
        	if (i == currItem) { 
        		menuFont.drawString(correctMenuX, offset1+10, items.get(i).label, Color.green);
        	}
        	else {
        		menuFont.drawString(correctMenuX, offset1, items.get(i).label);
        	}
        	offset1 += spacing1;
        }
        // font binds a texture, so let's turn it off..
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);


        return true;
    }


}
