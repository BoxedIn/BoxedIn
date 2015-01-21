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
public class TeleportPad extends NonmoveableObject implements InteractableObject{
    private TeleportPad teleTo;
    private Point destination;
    public TeleportPad(Point p){
        setLocation(p);
    }
    public void draw(Graphics g){
            int x = this.getLocation().x*Level.boxPixelWidth;
            int y = this.getLocation().y*Level.boxPixelHeight;
            g.drawImage(ImageUtility.getPadImage(), x, y, null);
    } 
    public void setTeleTo(TeleportPad p){
        teleTo = p;
        if(teleTo == null){
            setDestination(null);
        }else{
            setDestination(new Point(getTeleTo().getLocation()));
        }
    }
    public TeleportPad getTeleTo(){
        return teleTo;
    }

    public Point getDestination() {
        return destination;
    }


    public void setDestination(Point destination) {
        this.destination = destination;
    }
}
