import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper extends JFrame {
    private Game game;
    private JPanel panel;
    private JLabel label;
    private final int CONS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main( String[] args ) {
        new JavaSweeper();
    }

    // конструктор JavaSweeper
    private JavaSweeper() {
        game = new Game(CONS, ROWS, BOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    // инициализация окна программы
    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        // иконка на заголовок
        setIconImage(getImage("icon"));
    }

    private void initLabel() {
        label = new JLabel("Добро пожаловать!");
        add(label, BorderLayout.SOUTH);
    }

    // панель самой программы
    private void initPanel() {
        panel = new JPanel() {
            // рисуем картинку на нашем поле
            @Override
            // функция рисует все что нужно
            protected void paintComponent( Graphics g ) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };
        // добавили мышечный адаптер
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed( MouseEvent e ) {
                // переменные получает координату клика
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                // левая кнопка мыши
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.pressLeftButton(coord);
                }
                // правая кнопка мыши
                if (e.getButton() == MouseEvent.BUTTON3) {
                    game.pressRightButton(coord);
                }
                // средняя кнопка мыши
                if (e.getButton() == MouseEvent.BUTTON2) {
                    game.start();
                }
                label.setText(getMassege());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE, Ranges.getSize().y * IMAGE_SIZE));
        add(panel);
    }

    private String getMassege() {
        switch (game.getState()) {
            case PLAYED:
                return "Семь раз отмерь один раз отрежь!";
            case BOMBER:
                return "Миссия провалена";
            case WINNER:
                return "Поздравляю! Ты выиграл!";
            default:
                return "Добро пожаловать!";
        }
    }

    // метод возвращает картинку из папки img
    private Image getImage( String name ) {
        String failImage = "img/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(failImage));
        return icon.getImage();
    }

    // метод перебирет картинки в перечислениях Box
    private void setImages() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name());
        }
    }

}

