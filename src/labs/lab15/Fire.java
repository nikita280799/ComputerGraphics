package labs.lab15;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

class Fire {

    List<Particle> particles = new LinkedList<Particle>();
    private int width;
    private int height;
    private int FIREEND = 70;
    private int startX;
    private int startY;
    private Color[] palette = new Color[256];
    private int spawnCoeff = 2;
    private int spawnVar = 0;

    Fire(int width, int height, int startX, int startY) {
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
        createPalette();
        generateStartParticles(200);
    }

    private void generateStartParticles(int startCount) {
        for (int i = 0; i < startCount; i++) {
            particles.add(new Particle(random(startX, startX + width), startY,
                    random(height - FIREEND, height), palette[random(0, 255)]));
        }
    }

    void step() {
        List<Particle> particlesCopy = new LinkedList<Particle>(particles);
        for (Particle particle: particlesCopy) {
            particle.incY(random(2, 5));
            particle.incX(random(-2, 2));
            if (particle.getY() < particle.getMaxY()) particles.remove(particle);
            double end = (double) (startY - particle.getY()) / (startY - particle.getMaxY()) * width / 2;
            if (particle.getX() < end + startX || particle.getX() > startX + width - end) {
                if (random(0, 100) > 85)
                particles.remove(particle);
            }

        }
        spawnVar++;
        if (spawnVar == spawnCoeff) {
            generateStartParticles(500);
            spawnVar = 0;
        }
    }

    private void createPalette() {
        for (int i = 0; i < 128; i++) {
            palette[i] = new Color(i , 0, 0);
            palette[i+128] = new Color(255 , i*2, 0);

        }
    }

    private int random(int from, int to) {
        return (int) (Math.random() * (to - from) + from);
    }
}
