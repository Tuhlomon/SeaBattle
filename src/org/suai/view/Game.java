package org.suai.view;

import org.suai.model.Field;

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
    private BufferedImage[] signed = new BufferedImage[2];
    private BufferedImage cursor;
    private BufferedImage ship;
    private BufferedImage buttonrun;
    private BufferedImage buttonfire;
    private BufferedImage fire30;
    private BufferedImage fire60;

    private int screen = 0; //0 - главный; 1 - комнаты; 2 - расстановка сил; 3 - поле боя
    private int condition = 0; //0 - ход игрока; 1 - ход соперника
    private int unlockFire = 0; //0 - кнопка заблокирована; 1 - можно жмякать
    private int sign = 0;

    public Game(){
        try {
            signed[0] = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\notsigned2.png"));
            signed[1] = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\signed.png"));
            bg[0] = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\bg_main.png"));
            bg[2] = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\bg_planning.png"));
            bg[3] = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\bg_battlearea.png"));
            cursor = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\cursor.png"));
            buttonrun = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\RUN.png"));
            buttonfire = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\FIRE.png"));
            fire30 = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\blockfire_30.png"));
            fire60 = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\blockfire_60.png"));
            ship = ImageIO.read(new File("C:\\Users\\Tuhlomon\\Desktop\\spaceship battle\\ship2.png"));
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
                switch (screen) {
                    case 0:
                        if (mouseX > 510 && mouseX < 690){
                            if (mouseY > 300 && mouseY < 330){ //play offline
                                System.out.println("play offline");
                                screen = 2;
                            }
                            else if (mouseY > 360 && mouseY < 390){ //play online
                                System.out.println("play online");
                                screen = 1;
                            }
                            else if (mouseY > 420 && mouseY < 450){ //exit
                                System.out.println("exit");
                                System.exit(0);
                            }
                        }
                        if (sign == 0){
                            if (mouseX > 540 && mouseY > 30 && mouseX < 660 && mouseY < 60){ //sign in
                                System.out.println("sign in");
                                sign = 1;
                            }
                            else if (mouseX > 510 && mouseY > 90 && mouseX < 690 && mouseY < 120){ //registration
                                System.out.println("registration");
                            }
                        }
                        else{
                            if (mouseY > 90 && mouseY < 120){
                                if (mouseX > 480 && mouseX < 570){ //info
                                    System.out.println("info");
                                }
                                else if (mouseX > 630 && mouseX < 720){ //sign out
                                    System.out.println("sign out");
                                    sign = 0;
                                }
                            }
                        }
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:
                        if (mouseX > 60 && mouseX < 360 && mouseY > 60 && mouseY < 360) { //клик по полю врага
                            if (true) { //добавить проверку на свобдную клетку
                                cursorX = (mouseX - 60) / 30;
                                cursorY = (mouseY - 60) / 30;
                                unlockFire = 1;
                            }
                        }
                        else if (mouseX > 60 && mouseX < 330 && mouseY > 390 && mouseY < 450 && unlockFire == 1) { //клик по кнопке огонь
                            System.out.println("BOOOOM! C:");
                            unlockFire = 0;
                        }
                        break;
                }
            }
        });
    }

    public void drawField(Graphics g, Field f, int offsetX, int offsetY){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                switch (f.getCell(i, j)){
                    case 0: continue;
                    case 1: g.drawImage(ship, offsetX + i*30, offsetY + j*30, null);
                    case 2: g.drawImage(marker, offsetX + i*30, offsetY + j*30, null);
                    case 3: g.drawImage(destroyedShip, offsetX + i*30, offsetY + j*30, null);
                }
            }
        }
    }

    public void paint(Graphics g){
        g.drawImage(bg[screen], 0, 0, null);
        switch (screen){
            case 0: //Главный экран
                g.drawImage(signed[sign], 480, 30, null);
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
                drawField(g, userField, 60, 60);
                drawField(g, enemyField, 60, 60);
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