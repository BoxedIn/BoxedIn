/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInEditor;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author jds5782
 */
public class Level implements Serializable{  
    private GameObject go[][];
    private int gridW, gridH;       // number of blocks in width/height
    public static int boxPixelWidth, boxPixelHeight;
    private Point start; //checks for start/end points since there can be only 1 of each
    private Point end;   //false indicates that they do not exist in level yet
    private String path;        // path to this levels file
    private String name;        // filename with extension
    private Player player; //player object
    private int timeLimit = 0;
    private LinkedList <TeleportPad> tp = new LinkedList();    
    
    public static final int MOVE_UP = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_DOWN = 3;
    public static final int MOVE_LEFT = 4;
    
    public Level(int gridWidth, int gridHeight){
        gridW = gridWidth; // subtract 1 to account for zero indexed array
        gridH = gridHeight;  // subtract 1 to account for zero indexed array
        go = new GameObject[gridW][gridH];
    }
    
    public void movePlayer(int d){
        int playerOldX = player.getX();
        int playerOldY = player.getY();
        int playerNewX = player.getX();
        int playerNewY = player.getY();
        int objectNewX = player.getX();
        int objectNewY = player.getY();
        
        
        //go[4][6] = ((Door)go[4][6]).flip();
        //System.out.println(go[4][6].toString());
        
        if(d == MOVE_UP){
            System.out.println("move up");
            playerNewX = playerOldX;
            playerNewY = playerOldY - 1;
            objectNewX = playerOldX;
            objectNewY = playerOldY - 2;
        }else if(d == MOVE_RIGHT){
            System.out.println("move right");
            playerNewX = playerOldX + 1;
            playerNewY = playerOldY;
            objectNewX = playerOldX + 2;
            objectNewY = playerOldY;
        }else if(d == MOVE_DOWN){
            System.out.println("move down");
            playerNewX = playerOldX;
            playerNewY = playerOldY + 1;
            objectNewX = playerOldX;
            objectNewY = playerOldY + 2;
        }else if(d == MOVE_LEFT){
            System.out.println("move left");
            playerNewX = playerOldX - 1;
            playerNewY = playerOldY;
            objectNewX = playerOldX - 2;
            objectNewY = playerOldY;
        }
        GameObject oldObject;
        oldObject = player.getIo();
        if(oldObject != null){
            player.setIo(null);
        }else{
            System.out.println("its not null");
        }
        if((go[playerNewX][playerNewY] != null) && (go[playerNewX][playerNewY] instanceof MoveableObject)){// if there is an object in front of the player
                System.out.println("this is moveable");
                if(go[objectNewX][objectNewY] == null){
                    System.out.println("nothing in front of object");
                }
                go[objectNewX][objectNewY] = go[playerNewX][playerNewY];    // move object in front of player up one space
                ((MoveableObject)go[objectNewX][objectNewY]).setX(objectNewX);     // set the moved objects x location to match its new spot
                ((MoveableObject)go[objectNewX][objectNewY]).setY(objectNewY);     // set the moved objects y location to match its new spot
                player.setX(playerNewX);    // set player x position inside the object
                go[playerNewX][playerNewY] = go[playerOldX][playerOldY];     // move player object in array from old position to new position
                go[playerOldX][playerOldY] = oldObject;  // set players old spot to empty
                player.setY(playerNewY);     // set player y position inside the object
                player.setX(playerNewX);    // set player x position inside the object
                if(go[objectNewX][objectNewY] instanceof MoveableObject){
                    System.out.println("its been moved");
                }
        }else if(go[playerNewX][playerNewY] instanceof EndPoint){   // if the object in front of player is the end point
            go[playerNewX][playerNewY] = null;      // remove the endPoint so the player can move there
            go[playerNewX][playerNewY] = go[playerOldX][playerOldY];     // move player object to the endPoints position
            go[playerOldX][playerOldY] = null;  // set players old spot to empty
            player.setY(playerNewY);     // set player y position inside the object
            player.setX(playerNewX);    // set player x position inside the object
        }else if(go[playerNewX][playerNewY] instanceof TeleportPad){
            if(((TeleportPad)go[playerNewX][playerNewY]).getTeleTo() != null){
                player.setIo(((TeleportPad)go[playerNewX][playerNewY]).getTeleTo());
                player.setX(((TeleportPad)player.getIo()).getLocation().x);
                player.setY(((TeleportPad)player.getIo()).getLocation().y);
                go[player.getX()][player.getY()] = go[playerOldX][playerOldY];
                go[playerOldX][playerOldY] = oldObject;
            }
        }else{
            System.out.println("nothing in front of me");
            go[playerNewX][playerNewY] = go[playerOldX][playerOldY];     // move player object in array from old position to new position
            go[playerOldX][playerOldY] = oldObject;  // set players old spot to empty
            player.setY(playerNewY);     // set player y position inside the object
            player.setX(playerNewX);    // set player x position inside the object
        }
//        if(player.getIo() != null && ){
//            go[playerOldX][playerOldY] = (TeleportPad) player.getIo();
//            player.setIo(null);
//        }
    }
    
    public void drawBackground(Graphics g){
        g.drawImage(ImageUtility.getBackgroundImage(), 0, 0, null);
    }
    
    public void drawObjects(Graphics g){
        for (int i = 0; i < go.length; i++){
            for(int j = 0; j < go[i].length; j++){
                if(go[i][j] != null){
                    go[i][j].draw(g);
                }
            }
        }
    }
    
    public boolean addGameObject(GameObject go){
        boolean added = false;
    
        if(go.getLocation().x < gridW && go.getLocation().y < gridH){
           if(this.go[go.getLocation().x][go.getLocation().y] == null  && setStartEndPoints(go)){
               // check that no other object occupies that point and if it is empyt, set the start or end point it necessary
               this.go[go.getLocation().x][go.getLocation().y] = go;    // set this spot in the object array to object passed in
               added = true;
               if(go instanceof StartPoint || go instanceof Player){
                   start = new Point(go.getLocation());
               }
               else if(go instanceof EndPoint){
                   end = new Point(go.getLocation());
               }
               
               if(go instanceof TeleportPad && tp.isEmpty()){
                   tp.push((TeleportPad)go);
                   System.out.println("TempTele is set");
               }
               else if(go instanceof TeleportPad &&  !tp.isEmpty()){
                   
                   TeleportPad tempTele = tp.pop();
                   tempTele.setTeleTo((TeleportPad) go);
                   
                   ((TeleportPad) go).setTeleTo(tempTele);
                   System.out.println("TelePad set");
                   
               }
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
            g = go[p.getLocation().x][p.getLocation().y];
            if(go[p.getLocation().x][p.getLocation().y] instanceof TeleportPad){
                if(((TeleportPad)g).getTeleTo() != null){
                    tp.push(((TeleportPad)g).getTeleTo());
                }
            
            }
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
  
    
    public GameObject[][] getGameObject(){
        return this.go;
    }
    
    public void setGameObject(ArrayList <GameObject> g0){
        for(int i = 0; i < g0.size(); i++){
            
            if(g0.get(i) != null){
                go[g0.get(i).getLocation().x][g0.get(i).getLocation().y] = g0.get(i);
            }
        }
    }
    
    //check to make sure user doesn't add more than one start or end point
    public boolean setStartEndPoints(GameObject go){
        boolean ok = true;
     
        //check to see if go is a start/end point, if not then its ok to add go
        if(go instanceof StartPoint || go instanceof EndPoint ){
            
            //no start points currently, ok to add
            if(go instanceof StartPoint && start == null){
                start = new Point((int)go.getLocation().getX(), (int)go.getLocation().getY());
                System.out.println("placing start point");
            }
            
            //no end points currently, ok to add
            else if(go instanceof EndPoint && end == null){
                end = new Point((int)go.getLocation().getX(), (int)go.getLocation().getY());  // match end points
                System.out.println("placing end point");
            }
            
            //currently have a start/end point, not ok to add
            else
                ok = false;
        }
        return ok;  
    }
    
    public void checkCompletion() throws IncompleteLevelException{
        // the minimum a level needs to be playable is a start and end point
        boolean failed = false;
        String warning = "Cannot add an incomplete level to the game!\n\n";
        if(start == null || end == null){
            warning += "Level does not contain both a Start and End point.\n";
            failed = true;  // to throw exception
        }
//        if(timeLimit > 0 || timeLimit <= 600){
//            warning += "Invalid level time limit.\n";
//            failed = true;  // to throw exception
//        }
        
        if(failed){
            throw new IncompleteLevelException(warning);
        }
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

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }
    
    public void setEnd(Point end){
        this.end = end;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public String getPath(){
        return path;
    }
    
    public void setPath(String p){
        path = p;
    }
    
    public String getLevelName(){
        return name;
    }
    
    public String getLevelNameWithoutExt(){
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos);
        }
        return name;     // return filename without extension
    }
    
    public void setLevelName(String n){
        name = n;
    }
    
    public void setTimeLimit(int seconds){
        if(seconds >= 600){
            timeLimit = 600;
        }else if(seconds <= 0){
            timeLimit = 0;
        }else{
            timeLimit = seconds;
        }
    }
    
    public int getTimeLimit(){
        return timeLimit;
    }
}
