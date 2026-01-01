import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;
import javax.swing.*;

public class App {
    JFrame tela;
    JLabel player;
    JLabel boolet;
    JLabel status;
    int health = 3;
    int score = 0;
    int highScore = 0;
    JLabel[] hearts = new JLabel[health];
    JLabel wallpaper;
    JLayeredPane layeredPane; // Melhor para defenir o que fica em cima do outro
    String[] controls = {"PERIOD", "SLASH", "X", ""}; // Esquerda, direita e fogo
    String mensagemFinal;
    
    public static void main(String[] args) throws Exception {
        App app = new App();
        Enemy enemy = new Enemy(app);
        
        if ((new File("assets/highScore").exists())) {
            try {
                FileReader fileReader = new FileReader("assets/highScore");
                StringBuilder stringBuilder = new StringBuilder();
                int c;

                while ((c = fileReader.read()) != -1) {
                    stringBuilder.append((char) c);
                }
                
                app.highScore = Integer.parseInt(stringBuilder.toString());
                fileReader.close();
            } catch (Exception _) {}
        }

        if (!(new File("assets/controls").exists())) {
            try {
                FileWriter fileWriter = new FileWriter("assets/controls");
                fileWriter.write("LEFT - PERIOD\nRIGHT - SLASH\nFIRE - X");
                fileWriter.close();
            } catch (Exception _) {}
        } else {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("assets/controls"));
                String linha;

                while ((linha = bufferedReader.readLine()) != null) {
                    if (linha.contains("LEFT")) {
                        app.controls[0] = linha.replace("LEFT - ", "");
                    } else if (linha.contains("RIGHT")) {
                        app.controls[1] = linha.replace("RIGHT - ", "");
                    } else if (linha.contains("FIRE")) {
                        app.controls[2] = linha.replace("FIRE - ", "");
                    }
                }
                bufferedReader.close();
            } catch (Exception _) {}
        }

        app.tela = new JFrame("Space Invaders");
        app.tela.setVisible(true);
        app.tela.setSize(350, 480);
        app.tela.setLocationRelativeTo(null);
        app.tela.setResizable(false);
        app.tela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.tela.setLayout(null);
        app.tela.getContentPane().setBackground(Color.BLACK);
        app.tela.setIconImage(new ImageIcon("icon.png").getImage());
        app.tela.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(app.controls[0]), "goLeft");
        app.tela.getRootPane().getActionMap().put("goLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(app.player.getX() <= 0)) {
                    new Timer(10, new ActionListener() {
                        int contador = 0;
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            app.player.setLocation(app.player.getX() - 5, app.player.getY());
                            app.tela.repaint();
                            app.tela.revalidate();
                            contador ++;
                            if (contador == 11) {
                                ((Timer)e.getSource()).stop();
                            }
                        }
                    }).start();
                }
            }
        });
        app.tela.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(app.controls[1]), "goRight");
        app.tela.getRootPane().getActionMap().put("goRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(app.player.getX() >= 160)) {
                    new Timer(10, new ActionListener() {
                        int contador = 0;
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            app.player.setLocation(app.player.getX() + 5, app.player.getY());
                            app.tela.repaint();
                            app.tela.revalidate();
                            contador ++;
                            if (contador == 11) {
                                ((Timer)e.getSource()).stop();
                            }
                        }
                    }).start();
                }
            }
        });

        app.tela.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(app.controls[2]), "fire");
        app.tela.getRootPane().getActionMap().put("fire", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enemy.killEnemy();
            }
        });

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
        
        ImageIcon booletIcon = new ImageIcon("assets/boom.png");
        app.boolet = new JLabel();
        app.boolet.setBounds(-50, 440, 50, 50);
        app.boolet.setIcon(booletIcon);
        app.layeredPane.add(app.boolet, Integer.valueOf(1)); 

        app.status = new JLabel("<html>Status<br> Score: " + app.score + "<br>HS: " + app.highScore + "<br>Lifes:</html>");
        app.status.setBounds(225, 10, 100, 120);
        app.status.setForeground(Color.WHITE);
        app.status.setFont(new Font("Arial", Font.BOLD, 16));
        app.layeredPane.add(app.status, Integer.valueOf(1)); 

        ImageIcon heartIcon = new ImageIcon("assets/heart.png");
        int heartsX = 225;
        
        for (int i = 0; i < app.hearts.length; i ++) {
            app.hearts[i] = new JLabel();
            app.hearts[i].setBounds(heartsX, 120, 20, 20);
            app.hearts[i].setIcon(heartIcon);
            app.layeredPane.add(app.hearts[i], Integer.valueOf(1)); 
            heartsX += 30;
        }

        app.tela.add(app.layeredPane);
        app.tela.repaint();
        app.tela.revalidate();
        Music.playMusic();
        enemy.setEnemy();
    }
}