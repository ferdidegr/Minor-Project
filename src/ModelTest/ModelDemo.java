package ModelTest;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 * A LWJGL port of the awesome MineFront Pre-ALPHA 0.02 Controls: W/UP = forward; A/LEFT = strafe left; D/RIGHT = strafe
 * right; S/DOWN = backward; SPACE = fly up; SHIFT = fly down; CONTROL = move faster; TAB = move slower; Q = increase
 * walking speed; Z = decrease walking speed; O = increase mouse speed; L = decrease mouse speed; C = reset position
 *
 * @author Oskar Veerhoek, Yan Chernikov
 */
public class ModelDemo {

    /** Defines if the application is resizable. */
    private static final boolean resizable = true;
    /*
* Defines if the application is running. Set to false to terminate the
* program.
*/
    private static volatile boolean running = true;
    /** The position of the player as a 3D vector (xyz). */
    private static Vector3f position = new Vector3f(0, 0, 0);
    /**
     * The rotation of the axis (where to the player looks). The X component stands for the rotation along the x-axis,
     * where 0 is dead ahead, 180 is backwards, and 360 is automically set to 0 (dead ahead). The value must be between
     * (including) 0 and 360. The Y component stands for the rotation along the y-axis, where 0 is looking straight
     * ahead, -90 is straight up, and 90 is straight down. The value must be between (including) -90 and 90.
     */
    private static Vector3f rotation = new Vector3f(0, 0, 0);
    /** The minimal distance from the camera where objects are rendered. */
    private static final float zNear = 0.3f;
    /**
     * The width and length of the floor and ceiling. Don't put anything above 1000, or OpenGL will start to freak out,
     * though.
     */
    private static final int gridSize = 10;
    /**
     * The size of tiles, where 0.5 is the standard size. Increasing the size by results in smaller tiles, and vice
     * versa.
     */
    private static final float tileSize = 0.20f;
    /** The maximal distance from the camera where objects are rendered. */
    private static final float zFar = 20f;
    /** The distance where fog starts appearing. */
    private static final float fogNear = 9f;
    /** The distance where the fog stops appearing (fully black here) */
    private static final float fogFar = 13f;
    /** The color of the fog in rgba. */
    private static final Color fogColor = new Color(0f, 0f, 0f, 1f);
    /** Defines if the application utilizes full-screen. */
    private static final boolean fullscreen = false;
    /** Defines the walking speed, where 10 is the standard. */
    private static int walkingSpeed = 10;
    /** Defines the mouse speed. */
    private static int mouseSpeed = 2;
    /** Defines if the application utilizes vertical synchronization (eliminates screen tearing; caps fps to 60fps) */
    private static final boolean vsync = true;
    /** Defines if the applications prints its frames-per-second to the console. */
    private static final boolean printFPS = false;
    /** Defines the maximum angle at which the player can look up. */
    private static final int maxLookUp = 85;
    /** Defines the minimum angle at which the player can look down. */
    private static final int maxLookDown = -85;
    /** The height of the ceiling. */
    private static final float ceilingHeight = 10;
    /** The height of the floor. */
    private static final float floorHeight = -1;
    /** Defines the field of view. */
    private static final int fov = 68;
    private static int fps;
    private static long lastFPS;
    private static long lastFrame;

    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private static int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }

    private static void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            if (printFPS) {
                System.out.println("FPS: " + fps);
            }
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public static void main(String[] args) {
        try {
            if (fullscreen) {
                Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
            } else {
                Display.setResizable(resizable);
                Display.setDisplayMode(new DisplayMode(800, 600));
            }
            Display.setTitle("Minefront Pre-Alpha 0.02 LWJGL Port");
            Display.setVSyncEnabled(vsync);
            Display.create();
        } catch (LWJGLException ex) {
            ex.printStackTrace();
            Display.destroy();
            System.exit(1);
        }

        if (fullscreen) {
            Mouse.setGrabbed(true);
        } else {
            Mouse.setGrabbed(false);
        }

        if (!GLContext.getCapabilities().OpenGL11) {
            System.err.println("Your OpenGL version doesn't support the required functionality.");
            Display.destroy();
            System.exit(1);
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glEnable(GL_DEPTH_TEST);

        int objectDisplayList = glGenLists(1);
        glNewList(objectDisplayList, GL_COMPILE);
        {
            Model m = null;
            try {
            	m = OBJLoader.loadModel(new File("res/bunny.obj"));
            } catch (FileNotFoundException e) {
            	e.printStackTrace();
            	Display.destroy();
            	System.exit(1);
            } catch (IOException e) {
            	e.printStackTrace();
            	Display.destroy();
            	System.exit(1);
            }
            glBegin(GL_TRIANGLES);
            for (Face face : m.faces) {
            	
            }
            glEnd();
        }
        glEndList();

        getDelta();
        lastFPS = getTime();

        while (running) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            int delta = getDelta();
           
            glCallList(objectDisplayList);

            glLoadIdentity();
            glRotatef(rotation.x, 1, 0, 0);
            glRotatef(rotation.y, 0, 1, 0);
            glRotatef(rotation.z, 0, 0, 1);
            glTranslatef(position.x, position.y, position.z);

        }

        glDeleteLists(objectDisplayList, 1);
        Display.destroy();
        System.exit(0);
    }
}