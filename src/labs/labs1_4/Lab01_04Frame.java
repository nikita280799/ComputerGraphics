package labs.labs1_4;

import javax.swing.*;
import java.awt.*;


public class Lab01_04Frame extends JFrame {

    private JButton clear, cdaBut, brezBut, circleBut, elipBut;

    private Labs01_04Panel labs0104Panel = new Labs01_04Panel(this);

    JTextField radCirField, elipAField, elipBField, affField;

    Point p1, p2 = null;

    boolean isCDA, isBresLine, isBresCir, isElip, isAff = false;

    private Lab01_04Frame(String title) {
        super(title);
        setSize(800, 600);
        setResizable(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JScrollPane scrollPanel = new JScrollPane(labs0104Panel);
        scrollPanel.setMinimumSize(new Dimension(200, 200));
        scrollPanel.setPreferredSize(new Dimension(500, 500));
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPanel, BorderLayout.CENTER);
        JPanel butPanel = new JPanel();
        butPanel.setLayout(new BoxLayout(butPanel, BoxLayout.Y_AXIS));
        clear = new JButton("Очистить");
        cdaBut = new JButton("ЦДА");
        brezBut = new JButton("Метод Брезенхема");
        circleBut = new JButton("Окружность");
        elipBut = new JButton("Эллипс");
        JTextArea radCirText = new JTextArea("Введите радиус окружности");
        radCirText.setMaximumSize(new Dimension(200, 25));
        JTextArea elipAText = new JTextArea("Введите a эллипса");
        elipAText.setMaximumSize(new Dimension(200, 25));
        JTextArea elipBText = new JTextArea("Введите b эллипса");
        elipBText.setMaximumSize(new Dimension(200, 25));
        radCirField = new JTextField();
        radCirField.setMaximumSize(new Dimension(100, 25));
        elipAField = new JTextField();
        elipAField.setMaximumSize(new Dimension(100, 25));
        elipBField = new JTextField();
        elipBField.setMaximumSize(new Dimension(100, 25));

        butPanel.add(clear);
        butPanel.add(cdaBut);
        butPanel.add(brezBut);

        butPanel.add(radCirText);
        butPanel.add(radCirField);
        butPanel.add(circleBut);

        butPanel.add(elipAText);
        butPanel.add(elipAField);
        butPanel.add(elipBText);
        butPanel.add(elipBField);
        butPanel.add(elipBut);

        this.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPanel, butPanel),
                BorderLayout.CENTER);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addListeners();
    }

    private void addListeners() {
        clear.addActionListener(e -> {
            labs0104Panel.points.clear();
            labs0104Panel.repaint();
        });
        cdaBut.addActionListener(e -> {
            setSwitchesOnFalse();
            isCDA = true;
        });
        brezBut.addActionListener(e -> {
            setSwitchesOnFalse();
            isBresLine = true;
        });
        circleBut.addActionListener(e -> {
            setSwitchesOnFalse();
            isBresCir = true;
        });
        elipBut.addActionListener(e -> {
            setSwitchesOnFalse();
            isElip = true;
        });
    }

    private void setSwitchesOnFalse() {
        isBresLine = false;
        isCDA = false;
        isBresCir = false;
        isElip = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Lab01_04Frame("labs.labs1_4.Lab01_04Frame"));
    }
}
