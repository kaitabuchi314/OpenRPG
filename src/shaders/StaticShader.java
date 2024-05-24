package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram {

	public static final String VERTEX_FILE = "src/shaders/vertexShader";
	public static final String FRAGMENT_FILE = "src/shaders/fragmentShader";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColor;
	private int location_ambientLight;
	private int location_repeatScale;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColor = super.getUniformLocation("lightColor");
		location_ambientLight = super.getUniformLocation("ambientLight");
		location_repeatScale = super.getUniformLocation("repeatScale");

	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadAmbientLight(float a) {
		super.loadFloat(location_ambientLight, a);
	}
	
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColor, light.getColor());
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix); 
	}
	
	public void loadRepeatScale(float x) {
		super.loadFloat(location_repeatScale, x);
	}
	
	public void setAmbientLight(float a) {
		start();
		
		loadAmbientLight(a);

		stop();
	}
	
	public void addLight(Light light) {
		start();
		
		loadLight(light);
		
		stop();
	}
}
