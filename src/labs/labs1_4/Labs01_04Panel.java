package labs.labs1_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.*;

public class Labs01_04Panel extends JPanel {

    Set<Point> points = new HashSet<>();

    private Lab01_04Frame frame;

    Labs01_04Panel(Lab01_04Frame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(1000, 1000));
        addListeners();
    }

    private void addListeners() {
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (frame.isBresLine || frame.isCDA) {
                        if (frame.p1 == null) {
                            frame.p1 = new Point(e.getX(), e.getY());
                            points.add(frame.p1);
                        } else {
                            frame.p2 = new Point(e.getX(), e.getY());
                            points.add(frame.p2);
                            if (frame.isCDA) cdaLine(frame.p1, frame.p2);
                            else bresenhamLine(frame.p1.x, frame.p1.y, frame.p2.x, frame.p2.y);
                            frame.p1 = null;
                            frame.p2 = null;
                        }
                    } else if (frame.isBresCir) {
                        bresenhamCircle(e.getX(), e.getY(), Integer.parseInt(frame.radCirField.getText()));
                    } else if (frame.isElip) {
                        ellipse(e.getX(), e.getY(), Integer.parseInt(frame.elipAField.getText()),
                                Integer.parseInt(frame.elipBField.getText()));
                    } else if (frame.isAff) {
                        affineTransform(Integer.parseInt(frame.affField.getText()), new Dimension(500, 500));
                    }
                }
                repaint();
            }
        };
        addMouseListener(mouseListener);
    }

    private void cdaLine(Point start, Point end) {
        int py = end.y - start.y;
        int px = end.x - start.x;
        int n = abs(end.y - start.y) > abs(end.x - start.x) ? abs(py) : abs(px);
        double xi = start.x;
        double yi = start.y;
        for (int i = 1; i < n; i++) {
            xi = xi + (double) px / n;
            yi = yi + (double) py / n;
            points.add(new Point((int) xi, (int) yi));
        }
    }

    private int sign(int x) {
        if (x >= 0) return 1;
        else return -1;
    }

    private void bresenhamLine(int xstart, int ystart, int xend, int yend) {
        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;
        dx = xend - xstart;
        dy = yend - ystart;

        incx = sign(dx);
        incy = sign(dy);

        if (dx < 0) dx = -dx;
        if (dy < 0) dy = -dy;

        if (dx > dy) {
            pdx = incx;
            pdy = 0;
            es = dy;
            el = dx;
        } else {
            pdx = 0;
            pdy = incy;
            es = dx;
            el = dy;
        }
        x = xstart;
        y = ystart;
        err = el / 2;
        points.add(new Point(x, y));
        for (int t = 0; t < el; t++) {
            err -= es;
            if (err < 0) {
                err += el;
                x += incx;
                y += incy;
            } else {
                x += pdx;
                y += pdy;
            }

            points.add(new Point(x, y));
        }
    }

    private void bresenhamCircle(int center_x, int center_y, int radius) {
        int x = 0, y = radius, sigma, delta = 2 - 2 * radius;
        while (y >= 0) {
            points.add(new Point(center_x + x, center_y + y));     // 1 четверть
            points.add(new Point(center_x - x, center_y + y));     // 2 четверть
            points.add(new Point(center_x + x, center_y - y));     // 3 четверть
            points.add(new Point(center_x - x, center_y - y));     // 4 четверть
            sigma = 2 * (delta + y) - 1;
            if (delta < 0 && sigma <= 0) {
                x++;
                delta += x + 1;
            } else if (delta > 0 && sigma > 0) {
                y--;
                delta -= y + 1;
            } else {
                x++;
                delta += x - y;
                y--;
            }
        }
    }

    private void pixel4(int x, int y, int dx, int dy) {
        points.add(new Point(x + dx, y + dy));     // 1 четверть
        points.add(new Point(x - dx, y + dy));     // 2 четверть
        points.add(new Point(x + dx, y - dy));     // 3 четверть
        points.add(new Point(x - dx, y - dy));     // 4 четверть
    }

    private void ellipse(int x, int y, int a, int b) {
        int dx = 0;
        int dy = b;
        int a_sqr = a * a;
        int b_sqr = b * b;
        int delta = 4 * b_sqr * ((dx + 1) * (dx + 1)) + a_sqr * ((2 * dy - 1) * (2 * dy - 1)) - 4 * a_sqr * b_sqr;
        while (a_sqr * (2 * dy - 1) > 2 * b_sqr * (dx + 1)) {
            pixel4(x, y, dx, dy);
            if (delta < 0) {
                dx++;
                delta += 4 * b_sqr * (2 * dx + 3);
            } else {
                dx++;
                delta = delta - 8 * a_sqr * (dy - 1) + 4 * b_sqr * (2 * dx + 3);
                dy--;
            }
        }
        delta = b_sqr * ((2 * dx + 1) * (2 * dx + 1)) + 4 * a_sqr * ((dy + 1) * (dy + 1)) - 4 * a_sqr * b_sqr;
        while (dy + 1 != 0) {
            pixel4(x, y, dx, dy);
            if (delta < 0) {
                dy--;
                delta += 4 * a_sqr * (2 * dy + 3);
            } else {
                dy--;
                delta = delta - 8 * b_sqr * (dx + 1) + 4 * a_sqr * (2 * dy + 3);
                dx++;
            }
        }
    }

    private void transToCenter(Point p, Dimension d) {
        p.x = p.x - d.width / 2;
        p.y = -p.y + d.height / 2;
    }

    private void transFromCenter(Point p, Dimension d) {
        p.x = p.x + d.width / 2;
        p.y = -p.y + d.height / 2;
    }

    private void affineTransform(int angle, Dimension d) {
        double a = angle * PI / 180;
        double da = 0.1;
        double acur = 0.0;
        int c = 0;
        System.out.println(d);
        repaint();
        while (acur < a) {
            for (Point p : points) {
                transToCenter(p, d);
                int xnew = (int) (p.x * cos(da) - p.y * sin(da));
                int ynew = (int) (p.x * sin(da) + p.y * cos(da));
                p.x = xnew;
                p.y = ynew;
                repaint();
                transFromCenter(p, d);
            }
            try {
                Thread.sleep(100);
            } catch(Exception ex) {System.out.println("Error");}
            acur += da;
            c++;
            System.out.println(this);
        }
        System.out.println(c);
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("HERE");
        super.paintComponent(g);
        for (Point point : points) {
            g.drawRect(point.x, point.y, 1, 1);
        }
    }
}