package gdd.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static gdd.Global.IMG_EXPLOSION;
import static gdd.Global.SCALE_FACTOR;

public class Alien2 extends Enemy{

    public Alien2(int x, int y) {
        super(x, y);

        try {
            BufferedImage enemyImg = ImageIO.read(new File("src/images/alien2.png"));
            // Use specific dimensions instead of scaling
            var scaledImage = enemyImg.getScaledInstance(32, 32, // Set desired width and height
                    java.awt.Image.SCALE_SMOOTH);
            setImage(scaledImage);
        } catch (IOException e) {
            System.err.println("Error loading alien2 image: " + e.getMessage());
        }
    }

    @Override
    public void act(int direction) {
        this.x += direction;
        this.y += 1;
    }

}
