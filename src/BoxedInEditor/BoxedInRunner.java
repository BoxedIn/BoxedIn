/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInRunner;

import BoxedInEditor.EndPoint;
import BoxedInEditor.Game;
import BoxedInEditor.GameObject;
import BoxedInEditor.Level;
import BoxedInEditor.LevelManager;
import BoxedInEditor.MoveableObject;
import BoxedInEditor.NonmoveableObject;
import BoxedInEditor.Player;
import java.awt.Point;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;

/**
 *
 * @author tmb5577
 */
public class BoxedInRunner extends LevelManager{
    BoxedInRunnerUI birui = new BoxedInRunnerUI(this);
    Game g = new Game();
    int levelNumber;
    
    public BoxedInRunner(){
        birui.setVisible(true);
        try {
            this.loadGame();
            birui.refreshVariables();
            birui.repaint();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public void movePlayer(int d){
        currentLevel.movePlayer(d);
    }
    
    public boolean levelIsOpen(){
        if(currentLevel != null){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean processMove(int move){
        
        Point playerPosition = currentLevel.getPlayer().getLocation();//space occupied by character
        Point nextPosition = new Point();//space character wants to move to
        Point nextNextPosition = new Point();//space after space character wants to move to
        boolean okayToMove;     // will be set by collionDetection method

        //calculate space character wants to move to, and space behind it, based on key pressed
        if(move == Level.MOVE_UP){
            nextPosition.x = playerPosition.x;
            nextPosition.y = playerPosition.y - 1;
            nextNextPosition.x = playerPosition.x;
            nextNextPosition.y = playerPosition.y - 2;
        }
        else if(move == Level.MOVE_DOWN){
            nextPosition.x = playerPosition.x;
            nextPosition.y = playerPosition.y + 1;
            nextNextPosition.x = playerPosition.x;
            nextNextPosition.y = playerPosition.y + 2;
        }
        else if(move == Level.MOVE_LEFT){
            nextPosition.x = playerPosition.x - 1;
            nextPosition.y = playerPosition.y;
            nextNextPosition.x = playerPosition.x - 2;
            nextNextPosition.y = playerPosition.y;
        }
        else if(move == Level.MOVE_RIGHT){
            nextPosition.x = playerPosition.x + 1;
            nextPosition.y = playerPosition.y;
            nextNextPosition.x = playerPosition.x + 2;
            nextNextPosition.y = playerPosition.y;
        }
        
        //collision detection
        okayToMove = collisionDetection(playerPosition, nextPosition, nextNextPosition);
        
        if(currentLevel.getPlayer().getLocation() == currentLevel.getEnd().getLocation()){
//            birui.displayWin();
            levelNumber++;
            currentLevel = g.getLevel(levelNumber);
            if(currentLevel == null){
                //this.win();
                System.out.println("You've won the game Congrats!");
            }
            else{
                System.out.println("Time for the next level. Hope you're ready!");
            }
        }
        return okayToMove;
    }
    
    //check different cases of player moving into empty spaces or those occupied by game objects
    public boolean collisionDetection(Point current, Point next, Point nextnext){
        
        boolean canmove = false;
        GameObject go[][] = currentLevel.getGameObject();

        //player trying to move to an open space
        if(go[current.x][current.y] instanceof MoveableObject && go[next.x][next.y] == null) {
            System.out.println("Go ahead sir you can move to that empty space");
            canmove = true;
        }
        //player trying to move to the end point
        else if(go[current.x][current.y] instanceof MoveableObject && go[next.x][next.y] instanceof EndPoint) {
            System.out.println("Go ahead sir you can move to the end point...");
            System.out.println("...I wonder what will happen next...");
            canmove = true;
        }
        //player trying to move a box
        else if(go[current.x][current.y] instanceof MoveableObject && go[next.x][next.y] instanceof NonmoveableObject) {
            System.out.println("Fuck you dumbass thats a block you can't move there!");
            canmove = false;
        }
        //player trying to move a boulder
        else if(go[current.x][current.y] instanceof MoveableObject && go[next.x][next.y] instanceof MoveableObject) {
            
            //check to see if the space behind the boulder is open
            if(go[nextnext.x][nextnext.y] == null){
                System.out.println("Go ahead sir you can move that boulder to that empty space");
                canmove = true;
            }
            
            //if not theres a boulder or block behind it
            else{
                System.out.println("Shit there seems to be a block and/or boulder in front of you and/or that boulder!");
                System.out.println("Fuck you dumbass you can't move there");
                canmove = false;
            }
        }
        return canmove;
    }
    
    
    // ************* josh, i made changes to this method to allow me to write some of the drawing
    // for the display. please finalize this method as far as what you want to do with setting
    // the currentLevel and stuff. also i dont think this should be throwing these exceptions. 
    // each call to this method will have to have a try/catch block.... 
    // maybe catch them inside the method and use a boolean return if you want some way of 
    // checking if the game was successfully opened
    // *******************
    public void loadGame() throws FileNotFoundException, IOException{
        Level l;
        File fileName = selectFile();
        Player p;
        levelNumber = 0;
        
        fin = new FileInputStream(fileName);
        ois = new ObjectInputStream(fin);
        
        int i = 0;
        try{
            while((l = readLevel(ois))!= null){
                p = new Player(l.getStart().getLocation());
                l.removeGameObject(l.getStart().getLocation());
                l.addGameObject(p);
                l.setPlayer(p);
                g.addLevel(l, Integer.toString(i));
                i++;
            }
        }catch(ClassNotFoundException ex){
            System.err.println(ex);
        }catch(EOFException ex){
            System.out.println("reached end of file");
        }
        ois.close(); 
        
        Level temp = g.getLevel(0); // for testing, manually set the level
        if(temp != null){
            setLevel(temp);     // set the currentLevel in the levelmanager to the first level
            System.out.println("current level has been set");
        }else{
            System.out.println("current level could not be set");
        }
    }
    
    public void saveGame(){
        
    }
    public void continueGame(){
        
    }
    public void pauseGame(){
        
    }
    public void beginGame(){
    }
       
    public File selectFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(birui);
        File fileName = fileChooser.getSelectedFile();
        return fileName;
    }
    
    public void win(){
        birui.displayGameWin();
    }
    public void restartLevel(){
        this.currentLevel = g.getLevel(levelNumber);
    }


}
