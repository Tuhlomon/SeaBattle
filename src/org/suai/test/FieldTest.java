package org.suai.test;

import org.junit.Test;
import org.junit.Assert;
import org.suai.model.Field;

public class FieldTest {

    @Test
    public void testSetShip() {
        Field f = new Field();
        Assert.assertEquals(true, f.setShip(0, 0, 1, 0)); //ставим однопалубный на 0 0
        for (int i = 1; i < 5; i++) { //клетки вокруг 0 0 заняты, ожидаем false для всех типов кораблей и всех ориентаций кораблей
            for (int j = 0; j < 2; j++) {
                Assert.assertEquals(false, f.setShip(0, 0, i, j));
                Assert.assertEquals(false, f.setShip(0, 1, i, j));
                Assert.assertEquals(false, f.setShip(1, 0, i, j));
                Assert.assertEquals(false, f.setShip(1, 1, i, j));
            }
        }
        for (int i = 2; i < 5; i++) { //нельзя поставить корабли типов 2, 3, 4
            Assert.assertEquals(false, f.setShip(0, 9, i, 1)); //на клетку 0 9 горизонтально
            Assert.assertEquals(false, f.setShip(9, 0, i, 0)); //на клетку 9 0 вертикально
        }
        //                  заполняем поле всеми кораблями
        Assert.assertEquals(true, f.setShip(0, 2, 1, 1)); //ставим однопалубный на первую строку
        Assert.assertEquals(true, f.setShip(0, 4, 1, 1)); //ставим однопалубный на первую строку
        Assert.assertEquals(true, f.setShip(0, 6, 1, 1)); //ставим однопалубный на первую строку
        Assert.assertEquals(true, f.setShip(0, 8, 2, 1)); //ставим двухпалубный на 8 0
        Assert.assertEquals(true, f.setShip(2, 0, 2, 1)); //ставим двухпалубные
        Assert.assertEquals(true, f.setShip(2, 3, 2, 1)); //ставим двухпалубные

        //                  проверяем на столкновение с другим кораблем:
        for (int i = 1; i < 5; i++) { //нельзя поставить корабли типов 1, 2, 3, 4
            Assert.assertEquals(false, f.setShip(2, 4, i, 1)); //столкновение с горизонтально поставленными
            Assert.assertEquals(false, f.setShip(2, 4, i, 0)); //столкновение с вертикально поставленными
        }

        Assert.assertEquals(true, f.setShip(2, 6, 3, 1)); //ставим двухпалубные
        Assert.assertEquals(true, f.setShip(4, 0, 3, 1)); //ставим трехпалубный
        Assert.assertEquals(true, f.setShip(4, 4, 4, 1)); //ставим четырехпалубный
        //                  заполнили поле всеми кораблями

        //                  пытаемся добавить новый корабль --- ожидаем false
        for (int i = 1; i < 5; i++) {
            Assert.assertEquals(false, f.setShip(6, 4, i, 1)); //превышение числа кораблей
            Assert.assertEquals(false, f.setShip(6, 4, i, 0));
        }
    }

    @Test
    public void testResetShip() {
        Field f1 = new Field();
        //                  ставим по одному кораблю всех видов
        int j = 1;
        for(int i = 0; i < 7; i += 2) {
            Assert.assertEquals(true, f1.setShip(i, 0, j, 1));
            j++;
        }
        //                  удаляем по одному кораблю каждого вида. ожидается тип корабля
        for(int i = 6; i <= 0; i -= 2) {
            int count = f1.resetShip(i, 0, 1);
            Assert.assertEquals(j, count);
            j--;
        }
        //                  ожидаем 0, если клетка пустая
        int count = f1.resetShip(9, 9, 1);
        Assert.assertEquals(0, count);
    }

    @Test
    public void testIsReady() {
        Field f = new Field();
        Assert.assertEquals(true, f.setShip(0, 0, 1, 0)); //ставим однопалубный на 0 0
        Assert.assertEquals(false, f.isReady()); //поставлен всего один корабль, ожидаем false
        for(int i = 2; i < 9; i+=2){
            if(i == 8) {
                Assert.assertEquals(true, f.setShip(0, i, 2, 1)); //ставим однопалубный на 0 0
                break;
            }
            Assert.assertEquals(true, f.setShip(0, i, 1, 1)); //ставим однопалубный на 0 0
        }
        for(int i = 0; i < 7; i+=3){
            if(i == 6) {
                Assert.assertEquals(true, f.setShip(2, i, 3, 1)); //ставим однопалубный на 0 0
                break;
            }
            Assert.assertEquals(true, f.setShip(2, i, 2, 1)); //ставим однопалубный на 0 0
        }
        for(int i = 0; i < 5; i+=4){
            if(i == 4) {
                Assert.assertEquals(true, f.setShip(4, i, 4, 1)); //ставим однопалубный на 0 0
                break;
            }
            Assert.assertEquals(true, f.setShip(4, i, 3, 1)); //ставим однопалубный на 0 0
        }
        Assert.assertEquals(true, f.isReady()); //поставлен все корабли

    }

    @Test
    public void testGetShot() {
    }

    @Test
    public void testSetMarker() {
    }

    @Test
    public void testGetCell() {
        Field f = new Field();

    }

    @Test
    public void testIsFreeCell() {
    }
}