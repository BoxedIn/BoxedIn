/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInEditor;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author jds5782
 */
public abstract class GameObject implements Serializable{
    private int width, height;
    private Point location;
    
    public abstract void draw(Graphics g);
    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
