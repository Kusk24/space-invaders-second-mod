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
        Scene1 scene1 = new Scene1();
        getContentPane().add(scene1);
        scene1.requestFocusInWindow();
        revalidate();
        repaint();
    }

    public void initScene2(){
        getContentPane().removeAll();
        gdd.scene.Scene2 scene2 = new gdd.scene.Scene2();
        getContentPane().add(scene2);
        scene2.requestFocusInWindow();
        revalidate();
        repaint();
    }
}