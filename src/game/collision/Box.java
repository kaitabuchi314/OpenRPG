package game.collision;
import org.lwjgl.util.vector.Vector3f;

public class Box {
    private Vector3f position;
    private Vector3f scale;

    public Box(Vector3f position, Vector3f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public boolean isCollidingWith(Box other) {
        return (Math.abs(this.position.x - other.position.x) < (this.scale.x + other.scale.x)) &&
               (Math.abs(this.position.y - other.position.y) < (this.scale.y + other.scale.y)) &&
               (Math.abs(this.position.z - other.position.z) < (this.scale.z + other.scale.z));
    }
}