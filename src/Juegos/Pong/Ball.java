/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Pong;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Juli
 */
public class Ball {

    static final int MAX_SPEED = 30;
    public Color color;
    public int cx;
    public int cy;
    public int x;
    public int size;
    public int y;
    public int speed;
    
    public Ball(int x, int y, int cx, int cy, int speed, Color color, int size)
    {
    this.x = x;
    this.y = y;
    this.cx = cx;
    this.cy = cy;
    this.speed = speed;
    this.color = color;
    this.size = size;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveBall() {
        x += cx;
        y += cy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setCy(int cy) {
        this.cy = cy;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public int getX() {
        return x;
    }

    public int getSize() {
        return size;
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    public void invertY() {
        cy *= -1;
    }

    public void bounceBack(int top, int bottom) {
        if (y > bottom - size) {
            invertY();
        } else if (y < top) {
            invertY();
        }
    }

    public void invertX() {
        cx *= -1;
    }

    public void increaseSpeed() {
        if (speed < Juegos.Pong.Ball.MAX_SPEED) {
            speed++;
            cx = (cx / Math.abs(cx) * speed);
            cy = (cy / Math.abs(cy) * speed);
        }
    }

    public int getY() {
        return y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
}
