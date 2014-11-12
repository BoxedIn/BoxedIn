/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInEditor;

import static BoxedInEditor.GameObject.levelGraphics;
import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author andrew
 */
public class CircleObject extends GameObject{
    public static Image gOImage;
        
        public CircleObject(Point p){
            location = p;
        }
        public CircleObject(SaveableGameObject sgo){
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
