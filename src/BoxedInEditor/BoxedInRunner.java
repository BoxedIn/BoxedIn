/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInRunner;

import BoxedInEditor.EndPoint;
import BoxedInEditor.GameObject;
import BoxedInEditor.InteractableObject;
import BoxedInEditor.Level;
import BoxedInEditor.LevelManager;
import BoxedInEditor.MoveableObject;
import BoxedInEditor.NonmoveableObject;
import java.awt.Point;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author tmb5577
 */
public class BoxedInRunner extends LevelManager implements TimerObserver{
    private BoxedInRunnerUI birui = new BoxedInRunnerUI(this);
    private Timer timer;
    private Thread timerThread;
    private boolean paused = true; 
    
    public BoxedInRunner(){
        birui.setVisible(true);
        welcomeMessage();
        birui.repaint();
        timer = Timer.getInstance();
        timerThread = new Thread(timer);
        timerThread.start();
        timer.subscribe(birui);
        timer.subscribe(this);
        openGame();
        birui.refreshVariables();
    }
    
    public void movePlayer(int d){
        getCurrentLevel().movePlayer(d);
        
        if((int)getCurrentLevel().getPlayer().getLocation().getX() == (int)getCurrentLevel().getEnd().getX() && 
           (int)getCurrentLevel().getPlayer().getLocation().getY() == (int)getCurrentLevel().getEnd().getY()){
            System.out.printf("End point (%d, %d)\n", (int)getCurrentLevel().getEnd().getX(), (int)getCurrentLevel().getEnd().getY());
            System.out.printf("Start point (%d, %d)\n", (int)getCurrentLevel().getPlayer().getX(), (int)getCurrentLevel().getPlayer().getY());
            nextLevel();
        }
    }
    
    private void nextLevel(){
        setLevelNumber((getLevelNumber()+ 1));
        if(getGame().getLevel(getLevelNumber()) != null){
            setCurrentLevel(getGame().getLevel(getLevelNumber()));
            levelIsNotSaved();      // we've moved to the next level, mark that the game hasnt been saved yet
            timer.setTimer(getCurrentLevel().getTimeLimit());
            timer.startTimer();
            System.out.println(getCurrentLevel().getLevelName());
            System.out.printf("(%d, %d)\n", (int)getCurrentLevel().getEnd().getX(), (int)getCurrentLevel().getEnd().getY());
            System.out.println("Time for the next level. Hope you're ready!");
        }else{
            System.out.println("You've won the game Congrats!");
            win();
        }
    }
    
    public boolean levelIsOpen(){
        if(getCurrentLevel() != null){
            return true;
        }else{
            return false;
        }
    }
   
    @Override
    public void updateTime(int seconds){
        if(seconds <= 0){
            lose();
        }
    }
    
    public void pauseGame(){
        paused = true;  // enable pause
        timer.stopTimer();
    }
    
    public void resumeGame(){
        paused = false; // resume game
        timer.startTimer();
    }
    
    public boolean isPaused(){
        return paused;
    }
    
    public void win(){
        pauseGame();      // pause the game so keyboard inputs dont throw exceptions
        setCurrentLevel(null);    // stops keyboard from taking inputs
        birui.disableSaveButton();
        levelIsSaved();    // set it to saved so that it doesnt prompt to save on close
        displayGameWin();
    }
    
    public void lose(){
        pauseGame();      // pause the game so keyboard inputs dont throw exceptions
        displayGameLose();
    }
    
    public void restartLevel(){
        if(getCurrentLevel() != null){
            try{
                loadGame(true);         // true is a flag for restarting the level
                timer.setTimer(getCurrentLevel().getTimeLimit());
                timer.startTimer();
                birui.refreshVariables();
                resumeGame();
            }catch(ClassCastException ex){
                System.err.println("incompatable filetype selected");
                JOptionPane.showMessageDialog(birui, "Incompatible filetype was selected");
            }
        }
    }
    
    private void displayGameWin(){
        JOptionPane.showMessageDialog(birui, "Congratulations! You've won the game!");
    }
    
    private void displayGameLose(){
        JOptionPane.showMessageDialog(birui, "Nice Try\nBut you'll have to be quicker than that");
    }
    
    public boolean processMove(int move){
        // returns true if the player is allowed to move, false otherwise
        Point playerPosition = getCurrentLevel().getPlayer().getLocation();//space occupied by character
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
        
        //ensure player does not move out of bounds
        if(nextPosition.y<0 || nextPosition.y>19 || nextPosition.x<0 || nextPosition.x>19){
                    System.out.println("NOOO");
                    return okayToMove = false;
        }
        
        //collision detection
        okayToMove = collisionDetection(playerPosition, nextPosition, nextNextPosition);
      
        return okayToMove;
    }
    
    //check different cases of player moving into empty spaces or those occupied by game objects
    public boolean collisionDetection(Point current, Point next, Point nextnext){
        
        boolean canmove = false;
        GameObject go[][] = getCurrentLevel().getGameObject();

        //player trying to move to an open space
        if(go[current.x][current.y] instanceof MoveableObject && go[next.x][next.y] == null) {
            canmove = true;
        }
        //player trying to move to the end point
        else if(go[current.x][current.y] instanceof MoveableObject && go[next.x][next.y] instanceof EndPoint) {
            canmove = true;
        }
        else if(go[current.x][current.y] instanceof MoveableObject && go[next.x][next.y] instanceof InteractableObject){
            canmove = true;
        }
        //player trying to move a boulder/block
        else if(go[current.x][current.y] instanceof MoveableObject && go[next.x][next.y] instanceof NonmoveableObject) {
            canmove = false;
        }
        //player trying to move a box
        else if(go[current.x][current.y] instanceof MoveableObject && go[next.x][next.y] instanceof MoveableObject) {
            
            //player trying to push box off screen
            if(nextnext.y<0 || nextnext.y>19 || nextnext.x<0 || nextnext.x>19){
                System.out.println("NOOO");
                return canmove = false;
            }
            
            //check to see if the space behind the boulder is open
            if(go[nextnext.x][nextnext.y] == null){
                canmove = true;
            }
            
            //if not theres a boulder or block behind it
            else{
                canmove = false;
            }
        }
        return canmove;
    }
    
    public void saveGame(){
        restartLevel();
        super.setGame(this.getGame());
        buildGame();
        levelIsSaved();     // set the boolean variable so it doesnt prompt for saving on close
    }
    public void openGame(){
        getGame().setFilePath(null);
        try{
            loadGame(false);
        }catch(ClassCastException ex){
            System.err.println("incompatable filetype selected");
            JOptionPane.showMessageDialog(birui, "Incompatible filetype was selected");
        }
        if(getCurrentLevel() != null){
            timer.setTimer(getCurrentLevel().getTimeLimit());
            timer.startTimer();
            birui.refreshVariables();
            resumeGame();
            birui.enableSaveButton();
        }
    }
       
    public File selectFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(birui);
        File fileName = fileChooser.getSelectedFile();
        return fileName;
    }
    
    private void welcomeMessage(){
        String str3 = "When you close this box another will open with a file directory\n";
        str3 += "Please select a \"game\" file you created in the editor to use, then click anywhere on the gray screen\n";
        str3 += "Press the HELP button at any time for more info";

        JOptionPane.showMessageDialog(birui, str3, "", JOptionPane.INFORMATION_MESSAGE, null);

    }
    
    public void helpMessage(){
        String str = "Help Menu\n";
        str += "You can move boxes around the level, but not bricks or boulders\n";
        str += "Try stepping on the small teleporter pad to see what happens\n";
        str += "Press Restart Game at any time if you get stuck or Pause Game if you just need a break\n";
        JOptionPane.showMessageDialog(birui, str);
    }
}
