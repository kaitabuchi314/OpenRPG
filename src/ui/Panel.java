package ui;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.Shader2D;

public class Panel extends Sprite2D {
	
	public ArrayList<Panel> subpanels = new ArrayList<Panel>();
	
	public Panel(Loader loader, String texturePath, Vector3f position, Vector3f size) {
		super(loader, texturePath, position, size);
	}
	
	@Override
	public void render(Camera cam, Renderer renderer, Shader2D mainShader) {
		for (Panel panel : subpanels) {
			panel.render(cam, renderer, mainShader);
		}
		super.render(cam, renderer, mainShader);
	}
	
	public void AddSubpanel(Panel subpanel) {
		subpanels.add(subpanel);
	}

}
