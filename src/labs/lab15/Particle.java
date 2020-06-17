package labs.lab15;

import java.awt.*;

public class Particle {

    final int SIZE = 4;
    private int x;

    int getMaxY() {
        return maxY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int y;
    private int maxY;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private Color color;

    Particle(int x, int y, int maxY, Color color) {
        this.x = x;
        this.y = y;
        this.maxY = maxY;
        this.color = color;
    }

    void incY(int dy) {
        y -= dy;
    }

    void incX(int dx) {
        x += dx;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "SIZE=" + SIZE +
                ", x=" + x +
                ", y=" + y +
                ", maxY=" + maxY +
                ", color=" + color +
                '}';
    }
}
