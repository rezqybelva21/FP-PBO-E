package cars;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class mainMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JButton playButton;
    private JButton selectCarButton;
    private CustomPanel customPanel; // Use the custom panel

    public mainMenu() {
        setTitle("Traffic Swerving");
        setSize(500, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        customPanel = new CustomPanel(); // Instantiate the custom panel
        add(customPanel);

        playButton = new JButton("Play");
        selectCarButton = new JButton("Select Car");

        playButton.addActionListener(e -> startGame());

        selectCarButton.addActionListener(e -> openCarSelectionMenu());

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playButton);
        buttonPanel.add(selectCarButton);

        // Add the button panel to the main frame
        add(buttonPanel);

        setLayout(new java.awt.FlowLayout());

        setVisible(true);
    }

    private void startGame() {
        dispose();
        JFrame app = new JFrame();
        work w = new work();
        app.add(w);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(500, 720);
        app.setVisible(true);
    }

    private void openCarSelectionMenu() {
        // Add logic to open the car selection menu
        // You can create another JFrame for car selection menu
        // or integrate it into the existing MainMenu class
    }

    public static void main(String[] args) {
        new mainMenu();
    }

    // Custom JPanel class to override paintComponent
    private class CustomPanel extends JPanel {
        private BufferedImage backgroundImage;

        public CustomPanel() {
            InputStream is = mainMenu.class.getResourceAsStream("/background.png");
            try {
                backgroundImage = ImageIO.read(is);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the background image
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
