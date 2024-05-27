package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import game.collision.Box;
import game.collision.CollidableEntity;
import game.collision.CollisionWorld;
import game.terrain.Terrain;
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
	TexturedModel texturedModel1;
	TexturedModel texturedModel2;
	TexturedModel texturedModel3;
	TexturedModel texturedModel4;
	TexturedModel texturedModel5;

	float frame = 0;
	public Player(StaticShader shader, Renderer renderer, Loader loader, Terrain main) {
		super(null, new Vector3f(0, main.getHeightAtPoint(0, 0), 0), 0, 0, 0, 1);
		
		this.renderer = renderer;
		this.loader = loader;
		this.mainShader = shader;
		
		RawModel model = OBJLoader.loadObjModel("Resources/player1.obj", loader);
		ModelTexture texture = new ModelTexture(this.loader.loadTexture("Resources/white.png"));
		//ModelTexture texture = new ModelTexture(this.loader.loadTexture("Resources/player.png"));
		texturedModel1 = new TexturedModel(model, texture);
		RawModel model2 = OBJLoader.loadObjModel("Resources/player2.obj", loader);
		texturedModel2 = new TexturedModel(model2, texture);
		RawModel model3 = OBJLoader.loadObjModel("Resources/player3.obj", loader);
		texturedModel3 = new TexturedModel(model3, texture);
		RawModel model4 = OBJLoader.loadObjModel("Resources/player4.obj", loader);
		texturedModel4 = new TexturedModel(model4, texture);
		RawModel model5 = OBJLoader.loadObjModel("Resources/player5.obj", loader);
		texturedModel5 = new TexturedModel(model5, texture);
		super.setModel(texturedModel1);

		velocity = new Vector3f(0,0,0);
		Box box = new Box(position, new Vector3f(1,1,1));
		
		
		setCollisionShape(box);
		
		CollisionWorld.AddCollidableEntityToWorld(this);
		name = "PLAYER";
	}
	
	public void update(Terrain activeTerrain) {
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
		
		float terrainHeight = activeTerrain.getHeightAtPoint(position.x, position.z);
		//temp ground collision
		if (Maths.addVector(position, velocity).y < terrainHeight) {
			velocity.y = 0;
		}

		position = Maths.addVector(velocity, position);
		
		manageCollisions(name);
		
		if (position.y < terrainHeight) {
			position.y = terrainHeight;
		}
	}

	void manageInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			//velocity.z += 0.04;
			velocity = Maths.addVector(velocity, (Vector3f) Maths.calculateForwardVector(this).scale(-0.045f));
			frame+= 0.05f;
		} else {
			frame = 0;
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
			jumping = true;
			time = 0;
		}
		frame %= 4;

		if (velocity.y < 0) {
			frame = 4;
		}
	}
	
	

	public void render() {
		
		mainShader.loadRepeatScale(1);

		renderer.render(this, mainShader);
	}
	@Override
	public TexturedModel getModel() {
		if ((int)frame == 0) {
			return texturedModel1;
		} else if ((int)frame == 1) {
			return texturedModel2;
		} else if ((int)frame == 2) {
			return texturedModel3;
		} else if ((int)frame == 3) {
			return texturedModel4;
		} else if ((int)frame == 4) {
			return texturedModel5;
		} else {
			return null;
		}
	}
	public Vector3f getOffsetPosition() {
		return Maths.addVector(getPosition(), new Vector3f(0,3.5f,0));
	}

}
