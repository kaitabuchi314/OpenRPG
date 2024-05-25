package game.terrain;

import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Heightmap;
import toolbox.HeightmapToRawModel;
import toolbox.Maths;

public class Terrain extends Entity {
	
	public float width;
	public float height;
	public float[] vertices;
	RawModel terrainModel;
	Heightmap h;
	public Terrain(Loader loader, Vector3f position, float size) {
		super(null, position, 0, 0, 0, size);
		terrainModel = HeightmapToRawModel.convertFromImage("Resources/heightmap.png");
		ModelTexture terrainTexture = new ModelTexture(loader.loadTexture("Resources/ground.png"));
		TexturedModel terrainTexturedModel = new TexturedModel(terrainModel, terrainTexture);
		super.setModel(terrainTexturedModel);
		width = 512;
		height = 512;
		vertices = terrainModel.vertices;
		try {
			h = new Heightmap("Resources/collisionmap.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Renderer renderer, StaticShader mainShader) {
		mainShader.loadRepeatScale(7f);

		renderer.render(this, mainShader);
	}
	
	public float getHeightAtPoint(float x, float y) {
		return h.getHeightAt((int)Maths.map(x, -500, 500, -width, width), (int)Maths.map(y, -500, 500, -height, height));
	}
}
