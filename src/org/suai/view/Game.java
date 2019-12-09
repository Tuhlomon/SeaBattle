package org.suai.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Game extends JPanel implements ActionListener {
    private int mouseX = 0;
    private int mouseY = 0;
    private int cursorX = 0;
    private int cursorY = 0;

    private Timer timer = new Timer(50, this);
    private BufferedImage[] bg = new BufferedImage[4];
    private BufferedImage cursor;
    private BufferedImage buttonrun;
    private BufferedImage buttonfire;
    private BufferedImage fire30;
    private BufferedImage fire60;

    private int screen = 3; //0 - главный; 1 - комнаты; 2 - расстановка сил; 3 - поле боя
    private int condition = 0; //0 - ход игрока; 1 - ход соперника
    private int unlockFire = 0; //0 - кнопка заблокирована; 1 - можно жмякать

    public Game(){
        try {
            bg[0] = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\bg_main.png"));
            bg[2] = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\bg_planning.png"));
            bg[3] = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\bg_battlearea.png"));
            cursor = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\cursor.png"));
            buttonrun = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\RUN.png"));
            buttonfire = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\FIRE.png"));
            fire30 = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\blockfire_30.png"));
            fire60 = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\blockfire_60.png"));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        timer.start();
        this.setFocusable(true);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                mouseX = e.getX();
                mouseY = e.getY();
                System.out.println("click on " + mouseX + " " + mouseY);
                if (mouseX > 60 && mouseX < 360 && mouseY > 60 && mouseY < 360){ //клик по полю врага
                    if (true){ //добавить проверку на свобдную клетку
                        cursorX = (mouseX - 60) / 30;
                        cursorY = (mouseY - 60) / 30;
                        unlockFire = 1;
                    }
                }
                if (mouseX > 60 && mouseX < 330 && mouseY > 390 && mouseY < 450 && unlockFire == 1) { //клик по кнопке огонь
                    System.out.println("BOOOOM! C:");
                    unlockFire = 0;
                }
            }
        });
    }

    public void paint(Graphics g){
        g.drawImage(bg[screen], 0, 0, null);
        switch (screen){
            case 0: //Главный экран

                break;
            case 1: //Комнаты

                break;
            case 2: //Расстановка сил

                break;
            case 3: //Поле боя
                if (condition == 0){
                    if (unlockFire == 1){
                        g.drawImage(cursor, cursorX * 30 + 60, cursorY * 30 + 60, null);
                        g.drawImage(fire60, 60, 390, null);
                    }
                    else g.drawImage(fire30, 60, 390, null);
                    g.drawImage(buttonfire, 60, 390, null);
                    g.drawImage(buttonrun, 450, 390, null);
                }
                else { //наши орудия перезаряжаются

                }
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       /* if (player.getHealth() <= 0){
            JOptionPane.showMessageDialog(this, "Game over!!", "Hahaha", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
        if (dir != 4) {
            player.move(dir, mass);
            dir = 4;
        }
        if (enemy != null) for (int i = 0; i < enemy.size(); i++){
            if (enemy.get(i) != null) {
                enemy.get(i).calculate(player, mass);
            }
        }*/
        repaint();
    }
}