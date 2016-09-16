import org.lwjgl.opengl.GL11;

public class DestinationBox extends Box {

	public void drawAt(float x, float y, float dx, float dy) {

		GL11.glColor3f(1, 0, 0);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+dx,y);
        GL11.glVertex2f(x+dx,y+dy);
        GL11.glVertex2f(x,y+dy);

        GL11.glEnd();            

	}
}
