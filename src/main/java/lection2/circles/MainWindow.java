package lection2.circles;

import lection2.common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame implements CanvasRepaintListener, Thread.UncaughtExceptionHandler {

    private static final int POS_X = 200;
    private static final int POS_Y = 50;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private final Interactable[] sprites = new Sprite[15];
    private boolean flag = false;
    private MainWindow() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Circles");

        sprites[0] = new Background();
        for (int i = 1; i < sprites.length; i++) {
            sprites[i] = new Ball();
        }
        BallButton ballButton = new BallButton("Нажми меня!");
        ballButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (sprites.length > 15) {
                        throw new RuntimeException("Слишком много шариков!");
                    } else {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            flag = true;
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            flag = false;
                        }
                    }
                } catch (RuntimeException ex) {
                    uncaughtException(Thread.currentThread(), ex);
                }
            }
        });
        add(ballButton, BorderLayout.SOUTH);
        MainCanvas canvas = new MainCanvas(this);
        add(canvas);
        setVisible(true);
    }

    @Override
    public void onDrawFrame(MainCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime);
        render(canvas, g);
    }

    private void update(MainCanvas canvas, float deltaTime) {
        if (flag) {
            if (sprites[1] == null ) {
                for (int i = 1; i < sprites.length; i++) {
                    sprites[i] = new Ball();
                }
            }
            for (int i = 0; i < sprites.length; i++) {
                sprites[i].update(canvas, deltaTime);
            }
        } else {
            for (int i = 1; i < sprites.length; i++) {
                sprites[i] = null;
            }
        }
    }
    private void render(MainCanvas canvas, Graphics g) {
        if (flag) {
            for (int i = 0; i < sprites.length; i++) {
                sprites[i].render(canvas, g);
            }
        } else {
            sprites[0] = new Background();
        }
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        JOptionPane.showMessageDialog(
                null, e.getMessage(),
                "Exception!", JOptionPane.ERROR_MESSAGE);
    }
}
