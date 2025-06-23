package gdd;

import gdd.scene.Scene1;
import gdd.scene.Title;

import javax.swing.JFrame;

public class Game extends JFrame  {

    public Game() {
        initUI();
    }

    private void initUI() {
        add(new Title());

        setTitle("Space Invaders");
        setSize(Global.BOARD_WIDTH, Global.BOARD_HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public void initScene1(){
        getContentPane().removeAll();
        getContentPane().add(new Scene1());
        revalidate();
        repaint();
    }
}