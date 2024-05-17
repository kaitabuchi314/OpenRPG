package game.collision;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.TexturedModel;
import renderEngine.Renderer;
import shaders.StaticShader;

public class CollidableEntity extends Entity {
	
	public Box collisionShape;
	
	public CollidableEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void setCollisionShape(Box shape) {
		collisionShape = shape;
	}
	
	public boolean isColliding(CollidableEntity other) {
		collisionShape.setPosition(position);
		return collisionShape.isCollidingWith(other.collisionShape);
	}
	
	public void render(Renderer renderer, StaticShader baseShader) {
		renderer.render(this, baseShader);
	}

}
