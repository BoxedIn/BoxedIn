/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author tmb5577
 */
public class SaveableGameObject implements Serializable{
    int w;
    int h;
    Point p;
    public SaveableGameObject(int w, int h, Point p){
        this.w = w;
        this.h = h;
        this.p = p;
    }
    public SaveableGameObject(GameObject go){
        this.w = go.getWidth();
        this.h = go.getHeight();
        this.p = go.getLocation();
    }
    public void save(){
        
    }
    
    
    
}
