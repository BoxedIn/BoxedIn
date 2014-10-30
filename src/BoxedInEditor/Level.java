/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInEditor;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author jds5782
 */
public class Level {
    private GameObject go[][];
    private int gridW, gridH;
    public static int boxPixelWidth, boxPixelHeight;
    
    public Level(int gridW, int gridH){
        this.gridW = gridW;
        this.gridH = gridH;
        go = new GameObject[gridW][gridH];
    }
    public void addGameObject(GameObject go){
           if(!this.checkGameObject(go)){
               this.go[go.getLocation().x][go.getLocation().y] = go;
           }
    }
   public void removeGameObject(Point p){
       
       
       
    }
   public void moveGameObject(GameObject go){
       
   }
   public boolean checkGameObject(GameObject go){
        for(int i = 0; i < this.go.length; i++){
            //check to make sure that there isnt an object at that point
              
            
        }
        return true;
   }
}
