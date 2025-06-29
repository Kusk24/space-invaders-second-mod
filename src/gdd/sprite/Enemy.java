package gdd.sprite;

import static gdd.Global.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy extends Sprite {

    private Bomb bomb;

    public Enemy(int x, int y) {

        initEnemy(x, y);
    }

    private void initEnemy(int x, int y) {

        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);

//        var ii = new ImageIcon(IMG_ENEMY);
//
//        // Scale the image to use the global scaling factor
//        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
//                ii.getIconHeight() * SCALE_FACTOR,
//                java.awt.Image.SCALE_SMOOTH);
//        setImage(scaledImage);

        try {
            BufferedImage enemyImg = ImageIO.read(new File(IMG_ENEMY));
            var scaledImage = enemyImg.getScaledInstance(enemyImg.getWidth() * SCALE_FACTOR,
                    enemyImg.getHeight() * SCALE_FACTOR,
                    java.awt.Image.SCALE_SMOOTH);
            setImage(scaledImage);
        } catch (IOException e) {
            System.err.println("Error loading explosion image: " + e.getMessage());
        }
    }

    public void act(int direction) {
        this.x += direction;
    }

    public Bomb getBomb() {

        return bomb;
    }

    public class Bomb extends Sprite {

        private boolean destroyed;

        public Bomb(int x, int y) {

            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);

            this.x = x;
            this.y = y;

            var bombImg = "src/images/bomb.png";
            try {
                BufferedImage explosionImg = ImageIO.read(new File(bombImg));
                setImage(explosionImg);
            } catch (IOException e) {
                System.err.println("Error loading explosion image: " + e.getMessage());
            }
        }

        public void setDestroyed(boolean destroyed) {

            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {

            return destroyed;
        }
    }
}
