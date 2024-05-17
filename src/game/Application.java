package game;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import game.collision.Box;
import game.collision.CollidableEntity;
import game.collision.CollisionWorld;
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
	FollowCamera camera;
	Player player;
	Terrain terrain;
	Box box2;
	CollidableEntity testEntity;
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
		
		terrain = new Terrain(loader, new Vector3f(0,0,0), 10);
		
		player = new Player(shader, renderer, loader);

		camera = new FollowCamera(player, new Vector3f(0, 0, 0), 0, 0, 0) ;
		
		
		RawModel testmodel = OBJLoader.loadObjModel("Resources/box.obj", loader);
		ModelTexture testtexture = new ModelTexture(loader.loadTexture("Resources/grass.png"));
		TexturedModel testtexturedModel = new TexturedModel(testmodel, testtexture);
		
		testEntity = new CollidableEntity(testtexturedModel, new Vector3f(17,0,12), 0,0,0,1);
		
		box2 = new Box(testEntity.getPosition(), new Vector3f(2f,1f,2f));
		testEntity.setCollisionShape(box2);
		
		CollisionWorld.AddCollidableEntityToWorld(testEntity);
		
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
			testEntity.render(renderer, shader);
			player.render();
			
			shader.stop();
			DisplayManager.updateDisplay();
			
		}
		
	}
	
	private void exit() {
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
