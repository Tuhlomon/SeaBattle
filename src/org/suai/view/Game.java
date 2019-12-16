package org.suai.view;

import org.suai.model.Field;
import org.suai.model.Master;

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
    private int type = 1;
    private int hor = 0;
    private boolean online = false;
    private Field myField = new Field();
    private Field enemyField = new Field();
    private Master bot;
    private String path = "C:\\Users\\ponit\\IdeaProjects\\SeaBattle\\spaceship battle\\";

    private Timer timer = new Timer(50, this);
    private BufferedImage[] bg = new BufferedImage[4];
    private BufferedImage[] signed = new BufferedImage[2];
    private BufferedImage cursor;
    private BufferedImage ship;
    private BufferedImage marker;
    private BufferedImage destroyedShip;
    private BufferedImage buttonrun;
    private BufferedImage buttonfire;
    private BufferedImage fire30;
    private BufferedImage fire60;
    private BufferedImage buttonReady;
    private BufferedImage[] turn = new BufferedImage[2];

    private int screen = 0; //0 - главный; 1 - комнаты; 2 - расстановка сил; 3 - поле боя
    private int condition = 0; //0 - ход игрока; 1 - ход соперника
    private int unlockFire = 0; //0 - кнопка заблокирована; 1 - можно жмякать
    private int sign = 0;
    private int x = 0;


    public Game(){
        try {
            signed[0] = ImageIO.read(new File(path + "notsigned2.png"));
            signed[1] = ImageIO.read(new File(path + "signed.png"));
            bg[0] = ImageIO.read(new File(path + "bg_main.png"));
            bg[2] = ImageIO.read(new File(path + "bg_planning.png"));
            bg[3] = ImageIO.read(new File(path + "bg_battlearea.png"));
            cursor = ImageIO.read(new File(path + "cursor.png"));
            buttonrun = ImageIO.read(new File(path + "RUN.png"));
            buttonfire = ImageIO.read(new File(path + "FIRE.png"));
            fire30 = ImageIO.read(new File(path + "blockfire_30.png"));
            fire60 = ImageIO.read(new File(path + "blockfire_60.png"));
            ship = ImageIO.read(new File(path + "ship2.png"));
            marker = ImageIO.read(new File(path + "marker2.png"));
            destroyedShip = ImageIO.read(new File(path + "error2.png")); //CHANGE IT!
            buttonReady = ImageIO.read(new File(path + "iamready.png"));
            turn[0] = ImageIO.read(new File(path + "turntohor.png"));
            turn[1] = ImageIO.read(new File(path + "turntover.png"));
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
                                myField = new Field();
                                screen = 2;
                                online = false;
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
                        if (mouseX > 60 && mouseX < 360 && mouseY > 60 && mouseY < 360) { //клик по полю
                            cursorX = (mouseX - 60) / 30;
                            cursorY = (mouseY - 60) / 30;
                            if (myField.isFreeCell(cursorX, cursorY)) myField.setShip(cursorX, cursorY, type, hor);
                            else myField.resetShip(cursorX, cursorY, 1);
                            if (myField.isReady()){
                                unlockFire = 1;
                            }
                            else unlockFire = 0;
                        }
                        if (mouseY > 60 && mouseY < 90 && mouseX > 510 && mouseX < 540) type = 1;
                        if (mouseY > 120 && mouseY < 150 && mouseX > 510 && mouseX < 570) type = 2;
                        if (mouseY > 180 && mouseY < 210 && mouseX > 510 && mouseX < 600) type = 3;
                        if (mouseY > 240 && mouseY < 270 && mouseX > 510 && mouseX < 630) type = 4;
                        if (mouseY > 300 && mouseY < 360 && mouseX > 510 && mouseX < 570) hor = ++hor%2;
                        if (mouseX > 60 && mouseX < 330 && mouseY > 390 && mouseY < 450 && unlockFire == 1){
                            unlockFire = 0;
                            enemyField = new Field();
                            screen = 3;
                            if (!online) bot = new Master();
                        }
                        if (mouseX > 450 && mouseX < 720 && mouseY > 390 && mouseY < 450)
                            screen = 0;
                        break;
                    case 3:
                        if (mouseX > 60 && mouseX < 360 && mouseY > 60 && mouseY < 360) { //клик по полю врага
                            if (enemyField.isFreeCell((mouseX - 60) / 30, (mouseY - 60) / 30)) { //добавить проверку на свобдную клетку
                                cursorX = (mouseX - 60) / 30;
                                cursorY = (mouseY - 60) / 30;
                                unlockFire = 1;
                            }
                        }
                        else if (mouseX > 60 && mouseX < 330 && mouseY > 390 && mouseY < 450 && unlockFire == 1) { //клик по кнопке огонь
                            System.out.println("BOOOOM! C:");
                            if (online){

                            }
                            else{
                                x = bot.getShot(cursorX*10+cursorY);
                                if (x == 5) {
                                    JOptionPane.showMessageDialog(null, "YOU WIN!");
                                    screen = 0;
                                }
                                else if (x == 4) enemyField.setMarkers(cursorX, cursorY);
                                else enemyField.setMarker(cursorX, cursorY, x);
                                if (x == 2) condition = 1;
                            }
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
                    break;
                    case 2: g.drawImage(marker, offsetX + i*30, offsetY + j*30, null);
                    break;
                    case 3: g.drawImage(destroyedShip, offsetX + i*30, offsetY + j*30, null);
                }
            }
        }
    }

    public void paint(Graphics g){
        g.drawImage(bg[screen], 0, 0, null);
        switch (screen) {
            case 0: //Главный экран
                g.drawImage(signed[sign], 480, 30, null);
                break;
            case 1: //Комнаты

                break;
            case 2: //Расстановка сил
                drawField(g, myField, 60, 60);
                if (unlockFire == 1){
                    g.drawImage(fire60, 60, 390, null);
                }
                else g.drawImage(fire30, 60, 390, null);
                g.drawImage(buttonReady, 60, 390, null);
                g.drawImage(turn[hor], 510, 300, null);
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
                drawField(g, myField, 450, 60);
                drawField(g, enemyField, 60, 60);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (screen == 3 && !online && condition == 1){
            x = bot.doShot();
            if (myField.getShot(x) == 2) condition = 0;
        }
        repaint();
    }
}