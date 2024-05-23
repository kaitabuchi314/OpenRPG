package game.ui;

import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.Shader2D;
import textures.ModelTexture;

public class Sprite2D extends Entity {

	Vector3f imageSize;
	
	public Sprite2D(Loader loader, String texturePath, Vector3f position, Vector3f size) {
		super(null, position, 0, 0, 0, 1);
		
		RawModel model = OBJLoader.loadObjModel("Resources/sprite.obj", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture(texturePath));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		setModel(texturedModel);
		this.imageSize = size;
	}
	
	public void render(Camera cam, Renderer renderer, Shader2D mainShader) {
		mainShader.loadSize(imageSize);
		mainShader.loadPosition(getPosition());
		renderer.render2D(cam,this, mainShader);
	}

}
