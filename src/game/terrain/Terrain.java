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
	
	Entity[] terrains;
	public Terrain(Loader loader, Vector3f position, float singleScale, int size) {
		super(null, position, 0, 0, 0, singleScale);
		RawModel terrainModel = OBJLoader.loadObjModel("Resources/terrain.obj", loader);
		ModelTexture terrainTexture = new ModelTexture(loader.loadTexture("Resources/grass.png"));
		TexturedModel terrainTexturedModel = new TexturedModel(terrainModel, terrainTexture);
		super.setModel(terrainTexturedModel);
		terrains = new Entity[size];
	}
	
	public void render(Renderer renderer, StaticShader mainShader) {
		renderer.render(this, mainShader);
		position = new Vector3f(1,0,0);
		renderer.render(this, mainShader);
	}

}
