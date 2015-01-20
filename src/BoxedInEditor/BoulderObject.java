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
 * @author andrew
 */
public class BoulderObject extends NonmoveableObject{
        
        public BoulderObject(Point p){
            setLocation(p);
        }
        
        @Override
        public void draw(Graphics g){
            int x = this.getLocation().x*Level.boxPixelWidth;
            int y = this.getLocation().y*Level.boxPixelHeight;
            g.drawImage(ImageUtility.getBoulderImage(), x, y, null);
        } 
}
