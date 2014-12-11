/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInEditor;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author jds5782
 */
public class Player extends MoveableObject{

    public Player(Point p){
            location = p;
    }
    
    public void draw(Graphics g) {   
        int x = this.location.x*Level.boxPixelWidth;
        int y = this.location.y*Level.boxPixelHeight;
        g.drawImage(ImageUtility.getPlayerImage(), x, y, null);
    }
    
    public int getX(){
        return (int)location.getX();
    }
    
    public int getY(){
        return (int)location.getY();
    }    
}
