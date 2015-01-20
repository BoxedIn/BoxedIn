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
public class AddCommand extends Command{
    private GameObject go;
    
    public AddCommand(Point p, int type){
        go = null;
        
        switch (type){
            case 1: go = new BlockObject(p);
                    break;
            case 2: go = new BoulderObject(p);
                    break;
            case 3: go = new WoodenBoxObject(p);
                    break;
            case 4: go = new StartPoint(p);
                    break;
            case 5: go = new EndPoint(p);
                    break;
            case 6: go = new Water(p);
                    break;
            case 7: {
                    go = new TeleportPad(p);
                    break;
            }
            default:
                    break;
        }
    }
    
    public boolean doCom(Level level){
        boolean success = false;
        if(go != null){
            if(level.addGameObject(go)){
                success = true;
            }
        }
        return success;
    }
    
    public boolean undoCom(Level level){
        boolean success = false;
        if(level.removeGameObject(go.getLocation())!=null){
            success = true;
        } 
        return success;
    }
}
