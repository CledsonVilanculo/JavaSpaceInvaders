import javax.swing.*;
import java.io.FileWriter;
import java.util.Random;

public class Enemy {
    JLabel[] enemy = new JLabel[5];
    App app;
    JButton shoot;
    int speed = 500;

    public Enemy (App app) {
        this.app = app;
    }

    public void setEnemy() {
        Random random = new Random();
        int[] positions = {0, 55, 110, 165};
        ImageIcon enemyIcon = new ImageIcon("assets/green.png");

        for (int i = 0; i < this.enemy.length; i ++) {
            int ramPose = random.nextInt(positions.length);

            this.enemy[i] = new JLabel("");
            this.enemy[i].setBounds(positions[ramPose], 0, 50, 50);
            this.enemy[i].setIcon(enemyIcon);
            app.layeredPane.add(this.enemy[i], Integer.valueOf(1)); 
            this.app.tela.add(app.layeredPane);
            this.app.tela.repaint();
            this.app.tela.revalidate();
        }
        
        this.invade();
    }

    public void killEnemy() {
        Random ramdom = new Random();
        int positions[] = {0, 55, 110, 165};

        for (int i = 0; i < this.enemy.length; i ++) {
            if (this.enemy[i].getX() == this.app.player.getX() && this.enemy[i].getY() > -1) {
                int ramPose = ramdom.nextInt(4);
                app.boolet.setLocation(this.enemy[i].getX(), this.enemy[i].getY());
                this.enemy[i].setLocation(positions[ramPose], -50);
                this.app.score += 10;
                app.status.setText("<html>Status<br> Score: " + app.score + "<br>HS: " + app.highScore + "<br>Lifes:</html>");
                this.app.tela.repaint();
                this.app.tela.revalidate();
                Music.playSoundEffect("shoot.wav");
                break;
            }
        }

        changeEnemies(this.app.score);
    }

    public void invade() {
        Random random = new Random();
        int selectEn = random.nextInt(3);
        int positions[] = {0, 55, 110, 165};
        int ramPose = random.nextInt(4);

        this.enemy[selectEn].setLocation(this.enemy[selectEn].getX(), this.enemy[selectEn].getY() + 50);

        app.tela.repaint();
        app.tela.revalidate();

        if (this.enemy[selectEn].getY() > 440) {
            Music.playSoundEffect("passed.wav");
            this.app.health --;

            switch (this.app.health) {
                case 2: this.app.hearts[2].setVisible(false);
                break;

                case 1: this.app.hearts[1].setVisible(false);
                break;

                case 0: this.app.hearts[0].setVisible(false);
                break;
            }

            this.enemy[selectEn].setLocation(positions[ramPose], -50);

            this.app.tela.repaint();
            this.app.tela.revalidate();

            if (this.app.health == 0) {
                Music.playSoundEffect("explosion.wav");
                if (app.score > app.highScore) {
                    try {
                        FileWriter fw = new FileWriter("assets/highScore");
                        fw.write(String.valueOf(app.score));
                        fw.close();
                        app.mensagemFinal = "Nova pontuação máxima! " + app.score;
                    } catch (Exception _) {}
                } else {
                    app.mensagemFinal = "Sua pontuação " + app.score;
                }

                JOptionPane.showMessageDialog(null, "Game Over\n" + app.mensagemFinal, "Space Invaders", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
        }

        try {
            Thread.sleep(this.speed);
            invade();
        } catch (Exception a) {

        }
    }

    public void changeEnemies(int enemyType) {
        switch (enemyType) {
            case 200: this.speed = 400;
            break;

            case 300: this.speed = 300;
            break;

            case 400: for (int i = 0; i < this.enemy.length; i ++) {
                    this.speed = 250;
                    ImageIcon enemyIcon = new ImageIcon("assets/yellow.png");
                    this.enemy[i].setIcon(enemyIcon);
                    this.app.tela.repaint();
                    this.app.tela.revalidate();
                }
            break;

            case 500: this.speed = 200;
            break;

            case 600: for (int i = 0; i < this.enemy.length; i ++) {
                    this.speed = 100;
                    ImageIcon enemyIcon = new ImageIcon("assets/red.png");
                    this.enemy[i].setIcon(enemyIcon);
                    this.app.tela.repaint();
                    this.app.tela.revalidate();
                }
            break;
        }
    }
}