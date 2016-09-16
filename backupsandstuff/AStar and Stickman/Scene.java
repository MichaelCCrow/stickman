import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public abstract class Scene
{
    public abstract boolean drawFrame(float delta) throws IOException, LWJGLException;

    protected Scene nextScene() { return this; }

    public Scene go() throws IOException, LWJGLException
    {
        long lastloop = (Sys.getTime()*1000 / Sys.getTimerResolution());


        boolean keepGoing = true;
        do
        {
            Display.sync(60);   // 60 FPS
            long now = (Sys.getTime()*1000 / Sys.getTimerResolution());
            long delta = now - lastloop;
            lastloop = now;

            keepGoing = drawFrame(delta);
            
            // UPDATE DISPLAY
            Display.update();
        } while (keepGoing && ! Display.isCloseRequested());

        
        return nextScene();
    }
}
