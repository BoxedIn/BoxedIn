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
public class Level {
    private GameObject go[][];
    private int spotsTaken[][];
    private int gridW, gridH;
    public static int boxPixelWidth, boxPixelHeight;
    
    public Level(int gridW, int gridH){
        this.gridW = gridW;
        this.gridH = gridH;
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
    
    public void addGameObject(GameObject go){
           if(spotsTaken[go.getLocation().x][go.getLocation().y] == 0){     // check that no other objects occupy that point
               this.go[go.getLocation().x][go.getLocation().y] = go;    // set this spot in the object array to object passed in
               this.spotsTaken[go.getLocation().x][go.getLocation().y] = 1;     // could possibly equal some other int to represent another type of object
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
