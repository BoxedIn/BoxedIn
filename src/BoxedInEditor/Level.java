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
    private Player player; //player object
    
    public static final int MOVE_UP = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_DOWN = 3;
    public static final int MOVE_LEFT = 4;
    
    public Level(int gridWidth, int gridHeight){
        gridW = gridWidth; // subtract 1 to account for zero indexed array
        gridH = gridHeight;  // subtract 1 to account for zero indexed array
        go = new GameObject[gridW][gridH];
        spotsTaken = new int[gridW][gridH];
        initSpotsTaken();
    }
    
    public void movePlayer(int d){
        if(d == MOVE_UP){
            if(go[player.getX()][player.getY() - 1] != null){// if there is an object in front of the player
                go[player.getX()][player.getY() - 2] = go[player.getX()][player.getY() - 1];    // move object in front of player up one space
                spotsTaken[player.getX()][player.getY() - 2] = 1;   // set the new spot as filled in the spots array
                go[player.getX()][player.getY() + 1] = null;    // set player old position to null
                spotsTaken[player.getX()][player.getY() + 1] = 0;   // set player old spot as empty in spots array
            }else{
                go[player.getX()][player.getY() - 1] = player; // move player up 1 position
                spotsTaken[player.getX()][player.getY() - 2] = 1;   // set the new spot as filled in the spots array
                player.setY(player.getY() - 1);     // subtract 1 from y position
                go[player.getX()][player.getY() + 1] = null;    // set player old position to null
                spotsTaken[player.getX()][player.getY() + 1] = 0;   // set player old spot as empty in spots array
            }
        }else if(d == MOVE_RIGHT){
            if(go[player.getX() + 1][player.getY()] != null){// if there is an object to the right of the player
                go[player.getX() + 2][player.getY()] = go[player.getX() + 1][player.getY()];    // move object in front of player right one space
                go[player.getX() - 1][player.getY()] = null;    // set player old position to null
            }else{
                go[player.getX() + 1][player.getY()] = player; // move player up 1 position
                player.setX(player.getX() + 1);     // add 1 to x position
                go[player.getX() - 1][player.getY()] = null;    // set player old position to null
            }
        }else if(d == MOVE_DOWN){
            if(go[player.getX()][player.getY() + 1] != null){// if there is an object below the player
                go[player.getX()][player.getY() + 2] = go[player.getX()][player.getY() + 1];    // move object below the player down 1 space
                go[player.getX()][player.getY() - 1] = null;    // set player old position to null
            }else{
                go[player.getX()][player.getY() + 1] = player; // move player down 1 position
                player.setY(player.getY() +  1);     // add 1 to y position
                go[player.getX()][player.getY() - 1] = null;    // set player old position to null
            }
        }else if(d == MOVE_LEFT){
            if(go[player.getX() - 1][player.getY()] != null){// if there is an object to the left of the player
                go[player.getX() - 2][player.getY()] = go[player.getX() - 1][player.getY()];    // move object in front of player left one space
                go[player.getX() + 1][player.getY()] = null;    // set player old position to null
            }else{
                go[player.getX() - 1][player.getY()] = player; // move player down 1 position
                player.setX(player.getX() - 1);     // subtract 1 from x position
                go[player.getX() + 1][player.getY()] = null;    // set player old position to null
            }
        }else{
            // dont move
        }
    }

    
    private void initSpotsTaken(){
        for(int[] row: spotsTaken){ // iterate through rows
            for(int column : row){  // iterate through columns
                column = 0;         // set all equal to 0
            }
        }
    }
    
    public void drawBackground(){
        try {
            ImageUtility.getGraphics().drawImage(ImageUtility.getBackgroundImage(), 0, 0, null);
        } catch (UninitializedGraphicsException ex) {
            System.err.println(ex);
        }
    }
    
    public void drawObjects(){
        for (int i = 0; i < go.length; i++){
            for(int j = 0; j < go[i].length; j++){
                if(go[i][j] != null){
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
    
    public int getLevelWidth(){
        return gridW;
    }
    
    public int getLevelHeight(){
        return gridH;
    }
    public Player getPlayer() {
        return player;
    }

    public EndPoint getEnd() {
        return end;
    }

    /**
     * @return the start
     */
    public StartPoint getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(StartPoint start) {
        this.start = start;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
