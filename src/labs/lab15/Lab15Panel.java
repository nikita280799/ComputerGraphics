package labs.lab15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class Lab15Panel extends JPanel {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;
    private int FIREWIDTH = 200;
    private int FIREHEIGHT = 200;
    private Fire fire;

    private Lab15Panel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        fire = new Fire(FIREWIDTH, FIREHEIGHT, WIDTH / 2 - FIREWIDTH / 2, HEIGHT / 2 + 100);
        ActionListener timerListener = e -> {
            fire.step();
            this.repaint();
        };
        Timer timer = new Timer(50, timerListener);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for (Particle particle:fire.particles) {
            g.setColor(particle.getColor());
            particle.setColor(new Color(particle.getColor().getRed(), particle.getColor().getGreen(), particle.getColor().getBlue(),
                    particle.getColor().getAlpha() - 2 < 0 ? 0 : particle.getColor().getAlpha() - 2));
            g.fillRect(particle.getX(), particle.getY(), particle.SIZE, particle.SIZE);
        }
    }

    public static void main(String[] args) {
        Lab15Panel lab15Panel = new Lab15Panel();
        JFrame frame = new JFrame();
        frame.add(lab15Panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        lab15Panel.requestFocus();
    }
}