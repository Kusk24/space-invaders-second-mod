package gdd.sprite;

import static gdd.Global.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Player extends Sprite {

    private static final int START_X = 270;
    private static final int START_Y = 540;
    private int width;

    public Player() {
        initPlayer();
    }

    private void initPlayer() {

//        var ii = new ImageIcon(IMG_PLAYER);
//
//        // Scale the image to use the global scaling factor
//        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
//                ii.getIconHeight() * SCALE_FACTOR,
//                java.awt.Image.SCALE_SMOOTH);
//        setImage(scaledImage);

        try {
            BufferedImage playerImg = ImageIO.read(new File(IMG_PLAYER));
            var scaledImage = playerImg.getScaledInstance(playerImg.getWidth() * SCALE_FACTOR,
                    playerImg.getHeight() * SCALE_FACTOR,
                    java.awt.Image.SCALE_SMOOTH);
            setImage(scaledImage);
        } catch (IOException e) {
            System.err.println("Error loading explosion image: " + e.getMessage());
        }

        setX(START_X);
        setY(START_Y);
    }

    public void act() {
        x += dx;

        if (x <= 2) {
            x = 2;
        }

        if (x >= BOARD_WIDTH - 2 * width) {
            x = BOARD_WIDTH - 2 * width;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -4;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 4;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
