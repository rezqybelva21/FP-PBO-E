package cars;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class work extends JPanel implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private int space;
    private int width = 80;
    private int height = 90;
    private int speed;
    private int move = 15;
    private int count = 1;
    private int WIDTH = 300;
    private int HEIGHT = 700;
    private int accelerationRate = 1;
    private int timeInSeconds = 100;
    private int score = 0;
    private Timer timer;
    private boolean gameOver = false;
    private boolean timeRunning = true;
    private JButton restartButton;
    private ArrayList<Rectangle> ocars;
    private Rectangle car;
    private Random rand;
    private boolean keyPressedUp = false;
    private boolean keyPressedDown = false;
    private boolean keyPressedLeft = false;
    private boolean keyPressedRight = false;
    BufferedImage bg;
    BufferedImage op1;
    BufferedImage op2;
    Timer t;

    public work() {
        InputStream background = work.class.getResourceAsStream("/Road.png");
        InputStream op_1 = work.class.getResourceAsStream("/Car1.png");
        InputStream op_2 = work.class.getResourceAsStream("/Car2.png");

        try {
            bg = ImageIO.read(background);
            op1 = ImageIO.read(op_1);
            op2 = ImageIO.read(op_2);
        } catch (IOException ex) {
            Logger.getLogger(work.class.getName()).log(Level.SEVERE, null, ex);
        }

        t = new Timer(20, this);
        rand = new Random();
        ocars = new ArrayList<Rectangle>();
        car = new Rectangle(WIDTH / 2 - 110, HEIGHT - 100, width, height);
        space = 550;
        speed = 8;
        addKeyListener(this);
        setFocusable(true);
        restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        add(restartButton);
        addocars(true);
        addocars(true);
        addocars(true);
        t.start();

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeRunning) {
                    timeInSeconds--;

                    if (timeInSeconds <= 0) {
                        gameOver = true;
                        timeRunning = false;
                    }

                    if (timeInSeconds % 6 == 0) { // Setiap 3 detik, tambahkan waktu
                        timeInSeconds += 3;
                    }

                    repaint();
                }
            }
        });
        timer.start();
    }

    private void restartGame() {
        gameOver = false;
        timeRunning = true;
        timeInSeconds = 100;
        score = 0;
        car = new Rectangle(WIDTH / 2 - 110, HEIGHT - 100, width, height);
        ocars.clear();
        count = 1;
        speed = 10;
        move = 15;
        accelerationRate = 1;
        addocars(true);
        addocars(true);
        addocars(true);
        restartButton.setVisible(false);
        repaint();
    }

    public void addocars(boolean first) {
        int positionx = rand.nextInt() % 2;
        int x = 0;
        int y = 0;
        int Width = width;
        int Height = height;
        if (positionx == 0) {
            x = WIDTH / 2 - 110;
        } else {
            x = WIDTH / 2 + 30;
        }
        if (first) {
            ocars.add(new Rectangle(x, y - 200 - (ocars.size() * space), Width, Height));
        } else {
            ocars.add(new Rectangle(x, ocars.get(ocars.size() - 1).y - 400, Width, Height));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, WIDTH, HEIGHT, this);

        if (!gameOver) {
            g.drawImage(op1, car.x, car.y, car.width, car.height, this);

            for (Rectangle rect : ocars) {
                g.drawImage(op2, rect.x, rect.y, rect.width, rect.height, this);
            }

            g.setColor(Color.WHITE);
            Font font = new Font("Arial", Font.BOLD, 16);
            g.setFont(font);
            g.drawString("Time: " + timeInSeconds + "s", 20, 20);
            g.drawString("Score: " + score, 20, 40);
        } else {
            g.setColor(Color.RED);
            Font font = new Font("Arial", Font.BOLD, 24);
            g.setFont(font);
            g.drawString("CRASHED! Score: " + score, WIDTH / 2 - 100, HEIGHT / 2);

            restartButton.setBounds(WIDTH / 2 - 50, HEIGHT / 2 + 20, 100, 30);
            restartButton.setVisible(true);
        }
    }

    public void actionPerformed(ActionEvent e) {
        Rectangle rect;
        count++;

        if (!gameOver) {
            for (int i = 0; i < ocars.size(); i++) {
                rect = ocars.get(i);
                if (count % 1000 == 0) {
                    speed += accelerationRate;
                    if (move < 10) {
                        move += 0.5;
                    }
                }
                rect.y += speed;
            }

            for (Rectangle r : ocars) {
                if (r.intersects(car)) {
                    car.y = r.y + height;
                    gameOver = true;
                }
            }

            for (int i = 0; i < ocars.size(); i++) {
                rect = ocars.get(i);
                if (rect.y + rect.height > HEIGHT) {
                    ocars.remove(rect);
                    addocars(false);
                    score += 10;
                }
            }
            repaint();
        }
    }

    public void moveup() {
        if (car.y - move < 90) {
            System.out.println("\b");
        } else {
            car.y -= move;
        }
    }

    public void movedown() {
        if (car.y + move + car.height > HEIGHT - 1) {
            System.out.println("\b");
        } else {
            car.y += move;
        }
    }

    public void moveleft() {
        if (car.x - move < WIDTH / 2 - 110) {
            System.out.println("\b");
        } else {
            car.x -= move;
        }
    }

    public void moveright() {
        if (car.x + move > WIDTH / 2 + 30) {
            System.out.println("\b");
        } else {
            car.x += move;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                keyPressedUp = true;
                moveup();
                break;
            case KeyEvent.VK_DOWN:
                keyPressedDown = true;
                movedown();
                break;
            case KeyEvent.VK_LEFT:
                keyPressedLeft = true;
                moveleft();
                break;
            case KeyEvent.VK_RIGHT:
                keyPressedRight = true;
                moveright();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                keyPressedUp = false;
                break;
            case KeyEvent.VK_DOWN:
                keyPressedDown = false;
                break;
            case KeyEvent.VK_LEFT:
                keyPressedLeft = false;
                break;
            case KeyEvent.VK_RIGHT:
                keyPressedRight = false;
                break;
            default:
                break;
        }

        if (!keyPressedUp && !keyPressedDown && !keyPressedLeft && !keyPressedRight) {
            // Tidak ada tombol panah yang ditekan, hentikan pergerakan op1 di sini
        }
    }
}
