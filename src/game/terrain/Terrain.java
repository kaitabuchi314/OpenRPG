package game.terrain;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class Terrain extends Entity {
	
	public Terrain(Loader loader, Vector3f position, float size) {
		super(null, position, 0, 0, 0, size);
		RawModel terrainModel = OBJLoader.loadObjModel("Resources/terrain.obj", loader);
		ModelTexture terrainTexture = new ModelTexture(loader.loadTexture("Resources/grass.png"));
		TexturedModel terrainTexturedModel = new TexturedModel(terrainModel, terrainTexture);
		super.setModel(terrainTexturedModel);
	}
	
	public void render(Renderer renderer, StaticShader mainShader) {
		renderer.render(this, mainShader);
	}

}
