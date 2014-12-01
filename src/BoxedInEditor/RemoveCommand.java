/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import java.awt.Point;

/**
 *
 * @author jds5782
 */
public class RemoveCommand extends Command{
    private Level level;
    private Point p;
    private int object;
    GameObject go;
    
    public RemoveCommand(Level l, Point p){
        level = l;
        this.p = p;
        go = null;
                //********* this is wrong, we need to figure out a way to know which object was removed ******
    }
    public boolean doCom(){  
        boolean success = false;
        if((go = level.removeGameObject(p)) != null){
            success = true;
        }
        return success;
    }
    
    public boolean undoCom(){
        boolean success = false;
        if(go != null){       
            if(level.addGameObject(go)){
                success = true;     // the object was successfully added
            }
        }
        return success;
    }

}
