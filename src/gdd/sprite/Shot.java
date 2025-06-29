package gdd.sprite;

import static gdd.Global.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Shot extends Sprite {

    private static final int H_SPACE = 20;
    private static final int V_SPACE = 1;

    public Shot() {
    }

    public Shot(int x, int y) {

        initShot(x, y);
    }

    private void initShot(int x, int y) {

//        var ii = new ImageIcon(IMG_SHOT);
//
//        // Scale the image to use the global scaling factor
//        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
//                ii.getIconHeight() * SCALE_FACTOR,
//                java.awt.Image.SCALE_SMOOTH);
//        setImage(scaledImage);


        try {
            BufferedImage shotImg = ImageIO.read(new File(IMG_SHOT));
            var scaledImage = shotImg.getScaledInstance(shotImg.getWidth() * SCALE_FACTOR,
                    shotImg.getHeight() * SCALE_FACTOR,
                    java.awt.Image.SCALE_SMOOTH);
            setImage(scaledImage);
        } catch (IOException e) {
            System.err.println("Error loading explosion image: " + e.getMessage());
        }

        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }
}
