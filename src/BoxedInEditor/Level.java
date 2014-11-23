/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInEditor;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author jds5782
 */
public class Level implements Serializable{
    // for opening a Level
    static java.util.logging.Level SEVERE;
    
    private GameObject go[][];
    private int spotsTaken[][];
    private int gridW, gridH;       // number of blocks in width/height
    public static int boxPixelWidth, boxPixelHeight;
    private StartPoint start; //checks for start/end points since there can be only 1 of each
    private EndPoint end;   //false indicates that they do not exist in level yet
    String name;        //for later 
    
    public Level(int gridWidth, int gridHeight){
        gridW = gridWidth; // subtract 1 to account for zero indexed array
        gridH = gridHeight;  // subtract 1 to account for zero indexed array
        go = new GameObject[gridW][gridH];
        spotsTaken = new int[gridW][gridH];
        initSpotsTaken();
    }
    
    private void initSpotsTaken(){
        for(int[] row: spotsTaken){ // iterate through rows
            for(int column : row){  // iterate through columns
                column = 0;         // set all equal to 0
            }
        }
    }
    
    public void drawObjects(){
        for (int i = 0; i < spotsTaken.length; i++){
            for(int j = 0; j < spotsTaken[i].length; j++){
                if(spotsTaken[i][j] != 0){
                    go[i][j].draw();
                }
            }
        }
    }
    
    public boolean addGameObject(GameObject go){
        boolean added = false;
        
        if(go.getLocation().x < gridW && go.getLocation().y < gridH && checkStartEndPoints(go)){
           if(spotsTaken[go.getLocation().x][go.getLocation().y] == 0){     // check that no other objects occupy that point
               this.go[go.getLocation().x][go.getLocation().y] = go;    // set this spot in the object array to object passed in
               this.spotsTaken[go.getLocation().x][go.getLocation().y] = 1;     // could possibly equal some other int to represent another type of object
               added = true;
           }
        }
        else if(go instanceof StartPoint){
            System.out.println("ERROR!! Currently have start point");
        }
        else if(go instanceof EndPoint){
            System.out.println("ERROR!! Currently have end point");
        }
        return added;
    }
    
    public GameObject removeGameObject(Point p){
        GameObject g = null;
        if(p.getLocation().x < gridW && p.getLocation().y < gridH){     // if this point is within the grid
            this.spotsTaken[p.getLocation().x][p.getLocation().y] = 0;    // mark this spot as empty
            g = go[p.getLocation().x][p.getLocation().y];
            
            if(this.go[p.getLocation().x][p.getLocation().y] instanceof StartPoint){
                start = null;
            }
            if(this.go[p.getLocation().x][p.getLocation().y] instanceof EndPoint){
                end = null;
            }
            
            this.go[p.getLocation().x][p.getLocation().y] = null;     // remove this object from the array
            
        }   // else point is point is not within the grid, do nothing
        return g;
    }
    
    public boolean checkGameObject(GameObject go){
         for(int i = 0; i < this.go.length; i++){
             //check to make sure that there isnt an object at that point

         }
         return true;
    }
    
    public GameObject[][] getGameObject(){
        return this.go;
    }
    
    //check to make sure user doesn't add more than one start or end point
    public boolean checkStartEndPoints(GameObject go){
        boolean ok = true;
     
        //check to see if go is a start/end point, if not then its ok to add go
        if(go instanceof StartPoint || go instanceof EndPoint ){
            
            //no start points currently, ok to add
            if(go instanceof StartPoint && start == null)
                start = (StartPoint)go;
            
            //no end points currently, ok to add
            else if(go instanceof EndPoint && end == null)
                end = (EndPoint)go;
            
            //currently have a start/end point, not ok to add
            else
                ok = false;
        }
        
        return ok;  
    }
    
    public boolean checkCompletion(){
        // the minimum a level needs to be playable is a start and end point
        if(start != null && end != null)
            return true;
        else 
            return false;
    }
}
