/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author azh5442
 */
public class SquareObject extends GameObject{
        public static Image gOImage;

        public SquareObject(Point p){
            location = p;
        }
        public SquareObject(SaveableGameObject sgo){
            this.location = sgo.p;
            this.setHeight(sgo.h);
            this.setWidth(sgo.w);
        }
        public void draw(){
            int x = this.location.x*Level.boxPixelWidth;
            int y = this.location.y*Level.boxPixelHeight;
            levelGraphics.drawImage(gOImage, x, y, null);
        }    
}
