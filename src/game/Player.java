package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import game.collision.Box;
import game.collision.CollidableEntity;
import game.collision.CollisionWorld;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;

public class Player extends CollidableEntity {

	Renderer renderer;
	Loader loader;
	StaticShader mainShader;
	boolean jumping = false;
	int time = 0;


	public Player(StaticShader shader, Renderer renderer, Loader loader) {
		super(null, new Vector3f(0, 0, 0), 0, 0, 0, 1);
		
		this.renderer = renderer;
		this.loader = loader;
		this.mainShader = shader;
		
		RawModel model = OBJLoader.loadObjModel("Resources/player.obj", loader);
		ModelTexture texture = new ModelTexture(this.loader.loadTexture("Resources/white.png"));
		//ModelTexture texture = new ModelTexture(this.loader.loadTexture("Resources/player.png"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		super.setModel(texturedModel);

		velocity = new Vector3f(0,0,0);
		Box box = new Box(position, new Vector3f(1,1,1));
		
		
		setCollisionShape(box);
		
		CollisionWorld.AddCollidableEntityToWorld(this);
		name = "PLAYER";
	}
	
	public void update() {
		float gravity = 0.06f;
		time++;

		manageInput();
		
		if (jumping && time < 40) {
			velocity.y += gravity + 0.08;
		} else if (time > 40) {
			jumping = false;
		}

		velocity = (Vector3f) velocity.scale(0.75f);

		velocity.y -= gravity;
		
		//temp ground collision
		if (Maths.addVector(position, velocity).y < 0) {
			velocity.y = 0;
		}

		position = Maths.addVector(velocity, position);
		
		manageCollisions(name);
		
	}

	void manageInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			//velocity.z += 0.04;
			velocity = Maths.addVector(velocity, (Vector3f) Maths.calculateForwardVector(this).scale(-0.045f));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			velocity = Maths.addVector(velocity, (Vector3f) Maths.calculateForwardVector(this).scale(0.045f));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			rotY += 1.5f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			rotY -= 1.5f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (velocity.y == 0) {
				jumping = true;
				time = 0;
			}
		}
	}
	
	

	public void render() {
		mainShader.loadRepeatScale(1);

		renderer.render(this, mainShader);
	}
	
	public Vector3f getOffsetPosition() {
		return Maths.addVector(getPosition(), new Vector3f(0,3.5f,0));
	}

}
