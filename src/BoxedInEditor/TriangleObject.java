/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author tmb5577
 */
public class TriangleObject extends GameObject{
    public static Image gOImage;
        
        public TriangleObject(Point p){
            location = p;
        }
        public TriangleObject(SaveableGameObject sgo){
            this.location = sgo.p;
            this.setHeight(sgo.h);
            this.setWidth(sgo.w);
        }
        public void draw(){
            int x = this.location.x*Level.boxPixelWidth;
            int y = this.location.y*Level.boxPixelHeight;
            System.out.println("drawing");
            //gOImage = gOImage.getScaledInstance(Level.boxPixelWidth, Level.boxPixelHeight, Image.SCALE_DEFAULT);
            levelGraphics.drawImage(gOImage, x, y, null);
        }  
    
}
