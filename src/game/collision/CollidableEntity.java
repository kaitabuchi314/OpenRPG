package game.collision;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.TexturedModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import toolbox.Maths;

public class CollidableEntity extends Entity {
	
	public Box collisionShape;
	public Vector3f velocity;
	boolean isNowColliding = false;
	public String name = "NULL";
	
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
	public void manageCollisions(String NAME) {
		for (CollidableEntity e : CollisionWorld.GetAllCollidableEntities()) {
			
			if (e.name != NAME) {
				isNowColliding = isColliding(e);

				Vector3f oldPosition = new Vector3f(position.x, position.y, position.z);
				if (isNowColliding) {
					
					position.x -= velocity.x;
					if (!isColliding(e)) {
						velocity.x = 0;
					} else {
						position.x = oldPosition.x;
					}
					
					position.y -= velocity.y;
					if (!isColliding(e)) {
						velocity.y = 0;
					} else {
						position.y = oldPosition.y;
					}
					
					position.z -= velocity.z;
					if (!isColliding(e)) {
						velocity.z = 0;
					} else {
						position.z = oldPosition.z;
					}
					// ggs
					position.x += velocity.x;
					if (!isColliding(e)) {
						velocity.x = 0;
					} else {
						position.x = oldPosition.x;
					}
					
					position.y += velocity.y;
					if (!isColliding(e)) {
						velocity.y = 0;
					} else {
						position.y = oldPosition.y;
					}
					
					position.z += velocity.z;
					if (!isColliding(e)) {
						velocity.z = 0;
					} else {
						position.z = oldPosition.z;
					}
					
					
					position = Maths.addVector(velocity, position);
				}
			}
			
		}
	}
}
