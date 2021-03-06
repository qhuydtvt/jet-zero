package game;

import game.bases.GameObject;
import game.bases.inputs.InputManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import game.bases.*;
import game.bases.scenes.SceneManager;
import game.maps.Map;
import game.platforms.Platform;
import game.players.Player;

/**
 * Created by huynq on 7/9/17.
 */
public class GameWindow extends JFrame {

    BufferedImage backBufferImage;
    Graphics2D backBufferGraphics2D;

    InputManager inputManager = InputManager.instance;

    long lastTimeUpdate = -1;

    private ViewPort viewPort;
    private Player player;

    public GameWindow() {
        setupWindow();
        setupBackBuffer();
        setupInputs();
        addPlayer();
        addPlatforms();
        setupStartupScene();
        this.setVisible(true);
    }

    private void addPlatforms() {
        Map map = Map.load("assets/maps/tut_lvl.json");
        map.generate();
    }

    private void addPlatform(int x, int y) {
        Platform platform = new Platform();
        platform.position.set(x, y);
        GameObject.add(platform);
    }

    private void addPlayer() {
        player = new Player();
        player.position.set(120, 100);
        GameObject.add(player);
    }

    private void setupStartupScene() {

    }

    private void setupBackBuffer() {
        backBufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        backBufferGraphics2D = (Graphics2D) backBufferImage.getGraphics();
    }

    private void setupInputs() {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                inputManager.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                inputManager.keyReleased(e);
            }
        });
    }


    public void loop() {
        while (true) {

            if (lastTimeUpdate == -1) {
                lastTimeUpdate = System.nanoTime();
            }

            long currentTime = System.nanoTime();

            if (currentTime - lastTimeUpdate > 17000000) {

                lastTimeUpdate = currentTime;

                run();

                render();
            }
        }
    }

    private void run() {
        GameObject.runAll();
        GameObject.runAllActions();
        viewPort.follow(player);
        SceneManager.instance.changeSceneIfNeeded();
    }

    private void render() {
        backBufferGraphics2D.setColor(Color.BLACK);
        backBufferGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        GameObject.renderAll(backBufferGraphics2D, viewPort);

        repaint();
    }

    private void setupWindow() {
        this.setSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        this.setResizable(false);
        this.setTitle("Platformer begin");
        this.viewPort = new ViewPort();
        this.viewPort.getFollowOffset().set(-Settings.GAMEPLAY_WIDTH / 2, -Settings.GAMEPLAY_HEIGHT / 2);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(backBufferImage, 0, 0, null);
    }
}
