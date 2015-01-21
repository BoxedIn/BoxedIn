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
    private Point p;
    private int object;
    private GameObject go;
    
    public RemoveCommand(Point p){
        this.p = p;
        go = null;
                //********* this is wrong, we need to figure out a way to know which object was removed ******
    }
    public boolean doCom(Level level){  
        boolean success = false;
        if((go = level.removeGameObject(p)) != null){
            success = true;
        }
        return success;
    }
    
    public boolean undoCom(Level level){
        boolean success = false;
        if(go != null){       
            if(level.addGameObject(go)){
                success = true;     // the object was successfully added
            }
        }
        return success;
    }

}
