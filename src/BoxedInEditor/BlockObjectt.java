/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author azh5442
 */
public class BlockObject extends NonmoveableObject{

        public BlockObject(Point p){
            location = p;
        }

        public void draw(Graphics g){
            int x = this.location.x*Level.boxPixelWidth;
            int y = this.location.y*Level.boxPixelHeight;
            g.drawImage(ImageUtility.getBlockImage(), x, y, null);
        }    
}
