/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInEditor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author jds5782
 */
public class GameObject {
    private Image gOImage;
    private int width, height;
    private Point location;
    public static Graphics levelGraphics;
    
    
    public GameObject(Point p, Image i){
        this.gOImage = i;
        this.location = new Point(p.x, p.y); 
        
    }
    
    public void drawObject(){
        this.location.getX();
        this.location.getY();
        int x = this.location.x*Level.boxPixelWidth;
        int y = this.location.y*Level.boxPixelHeight;
        levelGraphics.drawImage(gOImage, x, y, null);
        
    }
    /**
     * @return the i
     */
    public Image getGameObjectImage() {
        return gOImage;
    }

    /**
     * @param i the i to set
     */
    public void set(Image i) {
        this.gOImage = i;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the location
     */
    public Point getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Point location) {
        this.location = location;
    }
    
}
