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
public class Player extends MoveableObject{
    private InteractableObject io;
    public Player(Point p){
            setLocation(p);
    }
    
    public void draw(Graphics g) {   
        int x = this.getLocation().x*Level.boxPixelWidth;
        int y = this.getLocation().y*Level.boxPixelHeight;
        g.drawImage(ImageUtility.getPlayerImage(), x, y, null);
    }
    
    public int getX(){
        return (int)getLocation().getX();
    }
    
    public int getY(){
        return (int)getLocation().getY();
    }    

    /**
     * @return 
     */
    public GameObject getIo() {
        if(io instanceof TeleportPad){
            TeleportPad p = (TeleportPad) io;
            return (TeleportPad) io;
        }else{
            return null;
        }
        //return io;
    }

    public void setIo(InteractableObject io) {
        this.io = io;
    }
}
