package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

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
import shaders.Shader2D;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;
import ui.Panel;
import ui.Sprite2D;

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
	Entity grass;
	Panel sprite;
	Panel sprite2;

	Shader2D shader2D;
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
		testEntity.velocity = new Vector3f(0,0,0);
		CollisionWorld.AddCollidableEntityToWorld(testEntity);
		
		sprite = new Panel(loader, "Resources/ui_panel.png", new Vector3f(-10,-8.5f,0), new Vector3f(0.07f, 0.1f, 0.0f));
		sprite.AddSubpanel(new Panel(loader, "Resources/image.png", new Vector3f(-17.5f,12.15f,0), new Vector3f(0.04f, -0.07f, 0.0f)));
		
		sprite2 = new Panel(loader, "Resources/ui_panel.png", new Vector3f(3.85f,0,0), new Vector3f(0.2f, 0.7f, 0.0f));
		sprite2.AddSubpanel(new Panel(loader, "Resources/image.png", new Vector3f(19.5f,-6f,0), new Vector3f(0.04f, -0.07f, 0.0f)));
		
		shader2D = new Shader2D();
		
		
		RawModel model = OBJLoader.loadObjModel("Resources/grass.obj", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("Resources/flower.png"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		grass = new Entity(texturedModel, new Vector3f(5,-2,-10), 0, 0, 0, 3f);
		
		
	}
	
	private void loop() {
		while (!Display.isCloseRequested()) {
			
			//game logic
			
			//entity.increaseRotation(0, 0.5f, 0);
			//testEntity.velocity.x -= 0.00005f;
			testEntity.position = Maths.addVector(testEntity.position, testEntity.velocity);
			testEntity.manageCollisions("TestEntity");
			player.update();
			camera.move();
			//rendering
			

			renderer.prepare();
			
			shader.start();
			shader.loadViewMatrix(camera);

			shader.loadRepeatScale(1);

			renderer.render(grass, shader);
			

			terrain.render(renderer, shader);
			
			testEntity.render(renderer, shader);
			
			player.render();
			
			
			shader.stop();
			
			shader2D.start();
			
			//ui render
			
			sprite.render(camera, renderer, shader2D);
			sprite2.render(camera, renderer, shader2D);

			shader2D.stop();
			
			DisplayManager.updateDisplay();
			
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				break;
			}
		}
		
	}
	
	private void exit() {
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
