package gdd.sprite;

import static gdd.Global.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Explosion extends Sprite {


    public Explosion(int x, int y) {

        initExplosion(x, y);
    }

    private void initExplosion(int x, int y) {

        this.x = x;
        this.y = y;

//        var ii = new ImageIcon(IMG_EXPLOSION);
//
//        // Scale the image to use the global scaling factor
//        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
//                ii.getIconHeight() * SCALE_FACTOR,
//                java.awt.Image.SCALE_SMOOTH);
//        setImage(scaledImage);

        try {
            BufferedImage explosionImg = ImageIO.read(new File(IMG_EXPLOSION));
            var scaledImage = explosionImg.getScaledInstance(explosionImg.getWidth() * SCALE_FACTOR,
                    explosionImg.getHeight() * SCALE_FACTOR,
                    java.awt.Image.SCALE_SMOOTH);
            setImage(scaledImage);
        } catch (IOException e) {
            System.err.println("Error loading explosion image: " + e.getMessage());
        }
    }

    public void act(int direction) {

        // this.x += direction;
    }


}
