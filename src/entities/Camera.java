package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	public Vector3f position = new Vector3f(0,0,0);
	public float pitch;
	public float yaw;
	public float roll;
	
	public Camera(Vector3f position, float pitch, float yaw, float roll) {
		this.position = position;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}

	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z-=0.05f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z+=0.05f;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			position.y+=0.05f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			position.y-=0.05f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x-=0.05f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x+=0.05f;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			pitch-=0.5f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			pitch+=0.5f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			yaw-=0.5f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			yaw+=0.5f;
		}
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}


	public float getYaw() {
		return yaw;
	}


	public float getRoll() {
		return roll;
	}
	
	
}
