package gdd.scene;

import static gdd.Global.*;

import gdd.AudioPlayer;
import gdd.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Title extends JPanel {

    private final Dimension d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);

    private Timer timer;

    private Image image;

    private int frame = 0;

    private AudioPlayer audioPlayer;

    public Title() {

        initBoard();
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
        // Load graphics
        ImageIcon ii = new ImageIcon("src/images/title2.png");
        image = ii.getImage();

        // initialise the audio
        try {
            audioPlayer = new AudioPlayer("src/audio/title.wav");
            audioPlayer.play();
        } catch (Exception e) {
            System.err.println("Error loading audio: " + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);

        g.drawImage(image, 0, -40, BOARD_WIDTH, BOARD_HEIGHT, null);

        if (frame % 60 < 30) {
            g.setColor(Color.white);
            g.setFont(getFont().deriveFont(Font.BOLD, 30));
            String text = "Press SPACE to Start";
            int stringWidth = g.getFontMetrics().stringWidth(text);
            int x = (d.width - stringWidth) / 2;
            g.drawString(text, x, 600);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void update() {
        frame++;
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
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                System.out.println("Detected Spacebar");
                Game game = (Game) getTopLevelAncestor(); // Get the parent Game instance
                game.initScene1(); // Switch to Scene1
            }
        }
    }
}
