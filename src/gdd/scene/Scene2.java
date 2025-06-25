package gdd.scene;

import static gdd.Global.*;

import gdd.SpawnDetails;
import gdd.sprite.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Scene2 extends JPanel {

    private List<Explosion> explosions;
    private List<Shot> shots;
    private Player player;
    // private Shot shot;
    private List<Alien1> aliens;
    private Map<Integer, List<SpawnDetails>> spawnMap;
    private int frame = 0; // track the current frame
    private List<SpawnDetails> sdList;

    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private String message = "Game Over";

    private final Dimension d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    private final Random randomizer = new Random();

    private Timer timer;

    public Scene2() {

        initBoard();
        gameInit();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);

        timer = new Timer(DELAY, new GameCycle());
        timer.start();

        gameInit();
    }

    private void gameInit() {

        aliens = new ArrayList<>();
        explosions = new ArrayList<>();
        shots = new ArrayList<>();
        aliens = new ArrayList<>();
        spawnMap = new HashMap<>();
        sdList = new ArrayList<>();


//        for (int i = 0; i < 24; i++) {
//            spawnMap.put(80*i, List.of(new SpawnDetails("Alien1", 100+(randomizer.nextInt(9)*80), 50)));
//        }

        spawnMap.put(80 , List.of(
                new SpawnDetails("Alien1", 100, 50),
                new SpawnDetails("Alien1", 150, 50 ),
                new SpawnDetails("Alien1", 200, 50 ),
                new SpawnDetails("Alien1", 250, 50 ))
        );

        for (int i = 0; i < 24; i++) {
            int randomX = randomizer.nextInt(9)*50;
            spawnMap.put(300 * i , List.of(
                    new SpawnDetails("Alien1", 100+randomX, 50),
                    new SpawnDetails("Alien1", 150+randomX, 50 ),
                    new SpawnDetails("Alien1", 200+randomX, 50 ),
                    new SpawnDetails("Alien1", 250+randomX, 50 ))
            );
        }

//        var alien = new Alien1(ALIEN_INIT_X + (ALIEN_WIDTH + ALIEN_GAP),
//                ALIEN_INIT_Y + (ALIEN_HEIGHT + ALIEN_GAP));
//        aliens.add(alien);

        player = new Player();
        // shot = new Shot();
    }

    private void drawAliens(Graphics g) {

        for (Alien1 alien : aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {

                alien.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            inGame = false;
        }
    }

    private void drawShot(Graphics g) {

        for (Shot shot : shots) {

            if (shot.isVisible()) {
                g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
            }
        }
    }

    private void drawBombing(Graphics g) {

        for (Alien1 e : aliens) {

            Alien1.Bomb b = e.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    private void drawExplosions(Graphics g) {

        List<Explosion> toRemove = new ArrayList<>();

        for (Explosion explosion : explosions) {

            if (explosion.isVisible()) {
                g.drawImage(explosion.getImage(), explosion.getX(), explosion.getY(), this);
                explosion.visibleCountDown();
                if (!explosion.isVisible()) {
                    toRemove.add(explosion);
                }
            }
        }

        explosions.removeAll(toRemove);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);

        g.setColor(Color.white);
        g.drawString("Win Yu Maung (ID - 6612054)", 10, 10);

        g.setColor(Color.green);

        if (inGame) {

            g.drawLine(0, GROUND,
                    BOARD_WIDTH, GROUND);

            drawExplosions(g);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
    }

    private void update() {

        System.out.println("Frame: " + frame);

        frame++;

//        if (frame > 80){
//            frame = 0;
//        }

        if (spawnMap.containsKey(frame)) {
            for (SpawnDetails sd : spawnMap.get(frame)) {
                if (sd.type.equals("Alien1")) {
                    aliens.add(new Alien1(sd.X, sd.Y));
                }
//                else if (sd.type.equals("Alien2")) {
//                    enemies.add(new Alien2(sd.x, sd.y));
//                }
            }
        }

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // player
        player.act();

        // shot
        List<Shot> shotsToRemove = new ArrayList<>();
        for (Shot shot : shots) {

            if (shot.isVisible()) {
                int shotX = shot.getX();
                int shotY = shot.getY();

                for (Alien1 alien : aliens) {
                    // Collision detection: shot and alien
                    int alienX = alien.getX();
                    int alienY = alien.getY();

                    if (alien.isVisible() && shot.isVisible()
                            && shotX >= (alienX)
                            && shotX <= (alienX + ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + ALIEN_HEIGHT)) {

                        var ii = new ImageIcon(IMG_EXPLOSION);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        explosions.add(new Explosion(alienX, alienY));
                        deaths++;
                        shot.die();
                        shotsToRemove.add(shot);
                    }
                }

                int y = shot.getY();
                // y -= 4;
                y -= 20;

                if (y < 0) {
                    shot.die();
                    shotsToRemove.add(shot);
                } else {
                    shot.setY(y);
                }
            }
        }
        shots.removeAll(shotsToRemove);

        // aliens
        for (Alien1 alien : aliens) {

            int x = alien.getX();
//            if (frame%40 == 0){
//                alien.setY(alien.getY()+GO_DOWN);
//            }

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {

                direction = -1;

                for (Alien1 e2 : aliens) {
                    e2.setY(e2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {

                direction = 1;

                for (Alien1 e : aliens) {
                    e.setY(e.getY() + GO_DOWN);
                }
            }
        }

        for (Alien1 alien : aliens) {
            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > GROUND - ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.act(direction);
            }
        }

        // bombs
        for (Alien1 alien : aliens) {

            int chance = randomizer.nextInt(15);
            Alien1.Bomb bomb = alien.getBomb();

            if (chance == CHANCE && alien.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()
                    && bombX >= (playerX)
                    && bombX <= (playerX + PLAYER_WIDTH)
                    && bombY >= (playerY)
                    && bombY <= (playerY + PLAYER_HEIGHT)) {

                var ii = new ImageIcon(IMG_EXPLOSION);
                player.setImage(ii.getImage());
                player.setDying(true);
                bomb.setDestroyed(true);
            }

            if (!bomb.isDestroyed()) {
                bomb.setY(bomb.getY() + 1);
                if (bomb.getY() >= GROUND - BOMB_HEIGHT) {
                    bomb.setDestroyed(true);
                }
            }
        }
    }

    private void doGameCycle() {
        update();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE && inGame) {
                System.out.println("Shots: "+shots.size());
                if (shots.size() < 4) {
                    // Create a new shot and add it to the list
                    Shot shot = new Shot(x, y);
                    shots.add(shot);
                }
            }
        }
    }
}

