package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;

public class Maths {

	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix,
				viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
	public static Vector3f addVector(Vector3f a, Vector3f b) {
		Vector3f v = new Vector3f(a.x, a.y, a.z);
		v.x += b.x;
		v.y += b.y;
		v.z += b.z;
		return v;
	}
	public static Vector3f subVector(Vector3f a, Vector3f b) {
		Vector3f v = new Vector3f(a.x, a.y, a.z);
		v.x -= b.x;
		v.y -= b.y;
		v.z -= b.z;
		return v;
	}
	
    public static Vector3f calculateForwardVector(Entity entity) {
        // Convert rotation angles to radians
        float rotX = (float) Math.toRadians(entity.getRotX());
        float rotY = (float) Math.toRadians(entity.getRotY());
        float rotZ = (float) Math.toRadians(entity.getRotZ());

        // Calculate forward vector using trigonometric functions
        float x = -(float) (Math.sin(rotY) * Math.cos(rotX));
        float y = (float) Math.sin(rotX);
        float z = -(float) (Math.cos(rotY) * Math.cos(rotX));

        return new Vector3f(x, y, z);
    }
    public static Vector3f calculateRotationToLookAtEntity(Camera observer, Vector3f targetPosition) {
        Vector3f direction = Vector3f.sub(targetPosition, observer.getPosition(), null);
        
        float yaw = (float) Math.toDegrees(Math.atan2(direction.x, direction.z));
        float pitch = (float) Math.toDegrees(Math.atan2(-direction.y, 
                                          Math.sqrt(direction.x * direction.x + direction.z * direction.z)));
        float roll = 0; // Roll will be assumed as 0

        return new Vector3f(pitch, yaw, roll);
    }
}
