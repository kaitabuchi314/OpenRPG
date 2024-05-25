package toolbox;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.RawModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class HeightmapToRawModel {

    public static RawModel convertFromImage(String imagePath) {
        float[][] heightmap = loadHeightmapFromImage(imagePath);
        if (heightmap == null) {
            System.err.println("Heightmap loading failed.");
            return null;
        }
        return convert(heightmap);
    }

    private static float[][] loadHeightmapFromImage(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            int width = image.getWidth();
            int height = image.getHeight();
            float[][] heightmap = new float[width][height];

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int color = image.getRGB(x, y);
                    int red = (color >> 16) & 0xFF;
                    int green = (color >> 8) & 0xFF;
                    int blue = color & 0xFF;
                    int gray = (red + green + blue) / 3;
                    heightmap[x][y] = gray / 255.0f;
                }
            }
            return heightmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RawModel convert(float[][] heightmap) {
        int width = heightmap.length;
        int height = heightmap[0].length;
        int vertexCount = width * height;

        float[] vertices = new float[vertexCount * 3];
        float[] normals = new float[vertexCount * 3];
        float[] textureCoords = new float[vertexCount * 2];
        int[] indices = new int[6 * (width - 1) * (height - 1)];

        int vertexPointer = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                vertices[vertexPointer * 3] = j; // X coordinate
                vertices[vertexPointer * 3 + 1] = heightmap[j][i]*2; // Y coordinate (height) - scaled for visibility
                vertices[vertexPointer * 3 + 2] = i; // Z coordinate

                normals[vertexPointer * 3] = 0;
                normals[vertexPointer * 3 + 1] = 1;
                normals[vertexPointer * 3 + 2] = 0;

                textureCoords[vertexPointer * 2] = (float) j / ((float) width - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) height - 1);

                vertexPointer++;
            }
        }

        int pointer = 0;
        for (int gz = 0; gz < height - 1; gz++) {
            for (int gx = 0; gx < width - 1; gx++) {
                int topLeft = (gz * width) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * width) + gx;
                int bottomRight = bottomLeft + 1;

                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }

        int vaoID = createVAO(vertices, textureCoords, indices);

        return new RawModel(vaoID, indices.length, vertices);
    }

    private static int createVAO(float[] vertices, float[] textureCoords, int[] indices) {
        int vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);

        storeDataInAttributeList(0, 3, vertices);
        storeDataInAttributeList(1, 2, textureCoords);
        bindIndicesBuffer(indices);

        GL30.glBindVertexArray(0);
        return vaoID;
    }

    private static void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private static void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}