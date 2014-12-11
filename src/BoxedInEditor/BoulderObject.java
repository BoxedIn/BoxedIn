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
            location = p;
        }
        public void draw(Graphics g){
            int x = this.location.x*Level.boxPixelWidth;
            int y = this.location.y*Level.boxPixelHeight;
            g.drawImage(ImageUtility.getBoulderImage(), x, y, null);
        } 
}
