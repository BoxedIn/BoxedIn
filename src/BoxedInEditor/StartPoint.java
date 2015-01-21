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
public class StartPoint extends NonmoveableObject{

        public StartPoint(Point p){
            setLocation(p);
        }
        public void draw(Graphics g){
            int x = this.getLocation().x*Level.boxPixelWidth;
            int y = this.getLocation().y*Level.boxPixelHeight;
            g.drawImage(ImageUtility.getStartPointImage(), x, y, null);
        } 
    
}
