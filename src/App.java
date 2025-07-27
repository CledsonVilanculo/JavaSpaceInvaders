import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.*;

public class App {
    JFrame tela;
    JLabel player;
    JButton left;
    JButton right;
    JButton shoot;
    JLabel boolet;
    JLabel status;
    int health = 3;
    int score = 0;
    JLabel[] hearts = new JLabel[health];
    JLabel wallpaper;
    JLayeredPane layeredPane;

    public static void main(String[] args) throws Exception {
        App app = new App();
        Enemy enemy = new Enemy(app);

        app.tela = new JFrame("Space Invaders");
        app.tela.setVisible(true);
        app.tela.setSize(350, 480);
        app.tela.setLocationRelativeTo(null);
        app.tela.setResizable(false);
        app.tela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.tela.setLayout(null);
        app.tela.getContentPane().setBackground(Color.BLACK);

        app.layeredPane = new JLayeredPane();
        app.layeredPane.setBounds(0, 0, 350, 480);

        Random random = new Random();
        int ranImg = random.nextInt(4);

        ImageIcon wallImg = new ImageIcon("assets/background/wallpaper" + ranImg + ".png");
        app.wallpaper = new JLabel();
        app.wallpaper.setBounds(0, 0, 350, 480);
        app.wallpaper.setIcon(wallImg);
        app.layeredPane.add(app.wallpaper, Integer.valueOf(0)); 

        ImageIcon playerIcon = new ImageIcon("assets/player.png");
        app.player = new JLabel();
        app.player.setBounds(0, 400, 50, 50);
        app.player.setIcon(playerIcon);
        app.layeredPane.add(app.player, Integer.valueOf(1)); 
        
        app.left = new JButton("<");
        app.left.setBounds(225, 390, 50, 50);
        app.left.setBackground(Color.BLACK);
        app.left.setForeground(Color.WHITE);
        app.left.setFont(new Font("Arial", Font.BOLD, 24));
        app.left.addActionListener(app::moveLeft);
        app.layeredPane.add(app.left, Integer.valueOf(1)); 

        app.right = new JButton(">");
        app.right.setBounds(280, 390, 50, 50);
        app.right.setBackground(Color.BLACK);
        app.right.setForeground(Color.WHITE);
        app.right.setFont(new Font("Arial", Font.BOLD, 24));
        app.right.addActionListener(app::moveRight);
        app.layeredPane.add(app.right, Integer.valueOf(1)); 

        app.shoot = new JButton("*");
        app.shoot.setBounds(250, 335, 50, 50);
        app.shoot.setBackground(Color.BLACK);
        app.shoot.setForeground(Color.WHITE);
        app.shoot.setFont(new Font("Arial", Font.BOLD, 30));
        app.shoot.addActionListener(_ -> enemy.killEnemy());
        app.layeredPane.add(app.shoot, Integer.valueOf(1)); 

        ImageIcon booletIcon = new ImageIcon("assets/boom.png");
        app.boolet = new JLabel();
        app.boolet.setBounds(-50, 440, 50, 50);
        app.boolet.setIcon(booletIcon);
        app.layeredPane.add(app.boolet, Integer.valueOf(1)); 

        app.status = new JLabel("<html>Status<br> Score: " + app.score + " <br>Lifes:</html>");
        app.status.setBounds(225, 10, 100, 90);
        app.status.setForeground(Color.WHITE);
        app.status.setFont(new Font("Arial", Font.BOLD, 16));
        app.layeredPane.add(app.status, Integer.valueOf(1)); 

        ImageIcon heartIcon = new ImageIcon("assets/heart.png");
        int heartsX = 225;
        
        for (int i = 0; i < app.hearts.length; i ++) {
            app.hearts[i] = new JLabel();
            app.hearts[i].setBounds(heartsX, 100, 20, 20);
            app.hearts[i].setIcon(heartIcon);
            app.layeredPane.add(app.hearts[i], Integer.valueOf(1)); 
            heartsX += 30;
        }

        app.tela.add(app.layeredPane);
        app.tela.repaint();
        app.tela.revalidate();
        enemy.setEnemy();
    }

    public void moveRight(ActionEvent a) {
        if (this.player.getX() >= 160) {

        } else {
            this.player.setLocation(player.getX() + 55, player.getY());
            this.tela.repaint();
            this.tela.revalidate();
        }
    }

    public void moveLeft(ActionEvent b) {
        if (this.player.getX() <= 0) {

        } else {
            this.player.setLocation(player.getX() - 55, player.getY());
            this.tela.repaint();
            this.tela.revalidate();
        }
    }
}