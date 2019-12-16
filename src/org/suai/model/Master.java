package org.suai.model;

import java.util.Random;

public class Master {
    private int condition = 0; //0-player's move, 1-enemy
    private Field myField = new Field();
    private Field enemyField = new Field();
    private int[] enemymtx = new int[100];

    public Master(){
        Random rand = new Random(System.currentTimeMillis());
        while(!myField.isReady()){
            myField.setShip(rand.nextInt(10), rand.nextInt(10), 1+rand.nextInt(4), rand.nextInt(2));
        }
    }

    public int doShot(int x, int y){
        return 0;
    }

    public int getShot(int s){
        return myField.getShot(s);
    }

    public void doGame(){

    }
}
