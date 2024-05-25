package models;

public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	public float[] vertices;
	
	public RawModel(int vaoId, int vertexCount, float[] vertices) {
		this.vaoID = vaoId;
		this.vertexCount = vertexCount;
		this.vertices = vertices;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
}
