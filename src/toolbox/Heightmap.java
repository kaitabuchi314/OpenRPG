package toolbox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Heightmap {

    private BufferedImage heightmap;

    public Heightmap(String filePath) throws IOException {
        heightmap = ImageIO.read(new File(filePath));
    }

    public float getHeightAt(int x, int y) {
        // Center of the image
        int centerX = heightmap.getWidth() / 2;
        int centerY = heightmap.getHeight() / 2;

        // Convert (x, y) to image coordinates
        int imgX = centerX + x;
        int imgY = centerY + y;

        // Check bounds
        if (imgX < 0 || imgX >= heightmap.getWidth() || imgY < 0 || imgY >= heightmap.getHeight()) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }

        // Get the pixel value at (imgX, imgY)
        int rgb = heightmap.getRGB(imgX, imgY);
        int rgb1 = heightmap.getRGB(imgX-1, imgY);
        int rgb2 = heightmap.getRGB(imgX+1, imgY);
        int rgb3 = heightmap.getRGB(imgX, imgY-1);
        int rgb4 = heightmap.getRGB(imgX, imgY+1);
        // Assuming a grayscale image, so R = G = B
        int height = (rgb >> 16) & 0xFF; // Extract the red component (since R = G = B)
        int height1 = (rgb1 >> 16) & 0xFF; // Extract the red component (since R = G = B)
        int height2 = (rgb2 >> 16) & 0xFF; // Extract the red component (since R = G = B)
        int height3 = (rgb3 >> 16) & 0xFF; // Extract the red component (since R = G = B)
        int height4 = (rgb4 >> 16) & 0xFF; // Extract the red component (since R = G = B)
        
        float average = (height+height1+height2+height3+height4)/5;
        return average/10;
    }

}