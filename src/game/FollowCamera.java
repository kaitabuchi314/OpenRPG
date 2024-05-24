package game;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class FollowCamera extends Camera {
    private Player player;
    private float distanceFromPlayerX = 20f;
    private float distanceFromPlayerY = 20f;

    private float angleAroundPlayer = 0;
    private boolean isDragging = false;
    private float lastMouseX;
    private float lastMouseY;

    

    
    public FollowCamera(Player player, Vector3f position, float pitch, float yaw, float roll) {
        super(position, pitch, yaw, roll);
        this.player = player;
    }

    @Override
    public void move() {
        calculateZoom();
        handleMouseInput();
        if (isDragging) {
            calculatePitch();
            calculateAngleAroundPlayer();
        }

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateCameraPosition(horizontalDistance, verticalDistance);

        yaw = 180 - (angleAroundPlayer);
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));

        position.x = player.getPosition().x - offsetX;
        position.y = player.getOffsetPosition().y + verticalDistance;
        position.z = player.getPosition().z - offsetZ;
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayerX * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayerY * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        float zoomLevel = Mouse.getDWheel() * 0.006f;
        distanceFromPlayerX -= zoomLevel;
        distanceFromPlayerY -= zoomLevel;

    }

    private void calculatePitch() {
        if (isDragging) {
            float pitchChange = (lastMouseY- Mouse.getY()) * 0.1f;
            pitch += pitchChange;
            lastMouseY = Mouse.getY();
        }
    }

    private void calculateAngleAroundPlayer() {
        if (isDragging) {
            float angleChange = (lastMouseX - getMouseX()) * 0.3f;
            angleAroundPlayer -= angleChange;
            lastMouseX = getMouseX();
        }
    }

    public void handleMouseInput() {
        if (Mouse.isButtonDown(0)) {
            if (!isDragging) {
                lastMouseX = getMouseX();
                lastMouseY = Mouse.getY();
                isDragging = true;
            }
        } else {
            isDragging = false;
        }
    }
    
    int getMouseX() {
    	return -Mouse.getX();
    }
}