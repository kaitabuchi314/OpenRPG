package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);
		
		DisplayMode d = Display.getDesktopDisplayMode();

		try {
			//Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setDisplayModeAndFullscreen(d);

			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Our First Display");
			Display.setResizable(true);
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, d.getWidth(), d.getHeight());
		
	}
	
	public static void updateDisplay() {
		
		Display.sync(FPS_CAP);
		Display.update();
		if (Display.wasResized()) {
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}
		
	}
	
	public static void closeDisplay() {
		
		Display.destroy();
		
	}
	
}
