package org.suai.model;

public class Field {
    private int[][] mtx = new int [10][10];
    private int[] maxships = {4, 3, 2, 1};
    private int[] ships = {0, 0, 0, 0};
    private int totalships = 20;

    public boolean setShip(int x, int y, int type, int hor){ // type - кол-во палуб, hor = 1 - горизонтально, 0 - вертикально
        if (ships[type-1] >= maxships[type-1]) return false;
        if (mtx[x][y] != 0) return false;
        if (x-1 >= 0 && mtx[x-1][y] == 1) return false;
        if (x+1 < 10 && mtx[x+1][y] == 1) return false;
        if (y-1 >= 0 && mtx[x][y-1] == 1) return false;
        if (y+1 < 10 && mtx[x][y+1] == 1) return false;
        if (x-1 >= 0 && y-1 >= 0 && mtx[x-1][y-1] == 1) return false;
        if (x-1 >= 0 && y+1 < 10 && mtx[x-1][y+1] == 1) return false;
        if (x+1 < 10 && y+1 < 10 && mtx[x+1][y+1] == 1) return false;
        if (x+1 < 10 && y-1 >= 0 && mtx[x+1][y-1] == 1) return false;
        if (hor == 1){
            if (y + type - 1 > 9) return false;
            for (int i = 0; i <= type; i++) if (y+i < 10 && (mtx[x][y+i] != 0 || (x+1<10 && mtx[x+1][y+i] != 0) || (x-1>=0 && mtx[x-1][y+i] != 0))) return false;
            for (int i = 0; i < type; i++) mtx[x][y+i] = 1;
        }
        else{
            if (x + type - 1 > 9) return false;
            for (int i = 0; i <= type; i++) if (x+i < 10 && (mtx[x+i][y] != 0 || (y+1<10 && mtx[x+i][y+1] != 0) || (y-1>=0 && mtx[x+i][y-1] != 0))) return false;
            for (int i = 0; i < type; i++) mtx[x+i][y] = 1;
        }
        ships[type-1]++;
        return true;
    }

    public int resetShip(int x, int y, int father){ //при вызове подавать на последний 1
        if (mtx[x][y] == 0) return 0;
        mtx[x][y] = 0;
        int counter = 1;
        if (x-1 >= 0 && mtx[x-1][y] == 1) counter += resetShip(x-1, y, 0);
        if (x+1 < 10 && mtx[x+1][y] == 1) counter += resetShip(x+1, y, 0);
        if (y-1 >= 0 && mtx[x][y-1] == 1) counter += resetShip(x, y-1, 0);
        if (y+1 < 10 && mtx[x][y+1] == 1) counter += resetShip(x, y+1, 0);
        if (father == 1)
            ships[counter-1]--;
        return counter;
    }

    public boolean isReady() {
        for (int i = 0; i < 4; i++) if (maxships[i] != ships[i]) return false;
        return true;
    }

    public int getShot(int x){ //3 - попал, 5 - выиграл, 2 - промах
        if (mtx[x/10][x%10] == 0) {
            mtx[x/10][x%10] = 2;
            return 2;
        }
        mtx[x/10][x%10] = 3;
        if (--totalships <= 0) return 5;
        return 3;
    }

    public void setMarker(int x, int y, int marker){
        mtx[x][y] = marker;
    }

    public int getCell(int x, int y){
        return mtx[x][y];
    }

    public boolean isFreeCell(int x, int y){
        if (mtx[x][y] == 0) return true;
        return false;
    }
}
