/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInEditor;

import java.awt.Point;

/**
 *
 * @author jds5782
 */
public abstract class MoveableObject extends GameObject{
    public void setX(int x){
        Point p = new Point(x, getLocation().y);
        setLocation(p);
    }
    
    public void setY(int y){
        Point p = new Point(getLocation().x, y);
        setLocation(p);
    }
}
