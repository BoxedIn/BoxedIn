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
public class RemoveCommand extends Command{
    private Level level;
    private Point p;
    private int object;
    public RemoveCommand(int type, Point p){
        this.p = p;
        object = type;
    }
    public void undoCom(){  
        level.removeGameObject(p);       
    }
    public void doCom(){
        GameObject o = null;
        switch (object){
            case 1: o = new SquareObject(p);
                    break;
            case 2: o = new TriangleObject(p);
                    break;
            case 3: o = new CircleObject(p);
                    break;
            default:
                    break;
        }
        if(o != null){
            level.addGameObject(o);}
    }

}
