/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author tmb5577
 */
public class WoodenBoxObject extends MoveableObject{
        
        public WoodenBoxObject(Point p){
            location = p;
        }
        public void draw(Graphics g){
            int x = this.location.x*Level.boxPixelWidth;
            int y = this.location.y*Level.boxPixelHeight;
            g.drawImage(ImageUtility.getWoodenBoxImage(), x, y, null);
        }  
    
}
