package game.collision;

import java.util.ArrayList;

public class CollisionWorld {
	public static ArrayList<CollidableEntity> collidableEntities = new ArrayList<CollidableEntity>();
	
	public static ArrayList<CollidableEntity> GetAllCollidableEntities() {
		return collidableEntities;
	}
	
	public static void RemoveAllCollidableEntitiesFromWorld() {
		collidableEntities.clear();
	}
	
	public static void AddCollidableEntityToWorld(CollidableEntity entity) {
		collidableEntities.add(entity);
	}
}
