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
    private Level level;
    private GameObject go;
    
    public AddCommand(Level l, int type, Point p){
        level = l;
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
            case 5: go = new EndPoint (p);
                    break;
            default:
                    break;
        }
    }
    
    public boolean doCom(){
        boolean success = false;
        if(go != null){
            if(level.addGameObject(go)){
                success = true;
            }
        }
        return success;
    }
    
    public boolean undoCom(){
        boolean success = false;
        if(level.removeGameObject(go.getLocation())!=null){
            success = true;
        } 
        return success;
    }
}
