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
            setLocation(p);
        }

        @Override
        public void draw(Graphics g){
            int x = this.getLocation().x*Level.boxPixelWidth;
            int y = this.getLocation().y*Level.boxPixelHeight;
            g.drawImage(ImageUtility.getBlockImage(), x, y, null);
        }    
}
