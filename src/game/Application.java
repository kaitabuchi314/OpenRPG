package game;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import game.terrain.Terrain;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class Application {
	
	Loader loader;
	StaticShader shader;
	Renderer renderer;
	Light light;
	Entity entity;
	FollowCamera camera;
	Player player;
	Terrain terrain;
	public void run() {
		init();
		loop();
		exit();
	}

	private void init() {
		DisplayManager.createDisplay();
		
		loader = new Loader();
		
		shader = new StaticShader();
		
		shader.setAmbientLight(0.2f);
		
		renderer = new Renderer(shader);
		
		
		light = new Light(new Vector3f(2000,2000,2000), new Vector3f(2f,2f,2f));
		
		shader.addLight(light);
		
		RawModel model = OBJLoader.loadObjModel("Resources/stall.obj", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("Resources/stallTexture.png"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		entity = new Entity(texturedModel, new Vector3f(20, 0,13),0,195,0,1f);
		
		terrain = new Terrain(loader, new Vector3f(0,0,0),10, 10);
		
		player = new Player(shader, renderer, loader);

		camera = new FollowCamera(player, new Vector3f(0, 0, 0), 0, 0, 0) ;
		
	}
	
	private void loop() {
		while (!Display.isCloseRequested()) {
			
			
			//game logic
			
			//entity.increaseRotation(0, 0.5f, 0);
			player.update();
			camera.move();
			//rendering
			

			renderer.prepare();
			
			shader.start();
			shader.loadViewMatrix(camera);
			terrain.render(renderer, shader);
			player.render();

			renderer.render(entity, shader);
			
			shader.stop();
			DisplayManager.updateDisplay();
			
		}
		
	}
	
	private void exit() {
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
