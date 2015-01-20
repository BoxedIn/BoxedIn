/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author tmb5577
 */
public class GameComposer extends LevelManager{
    private LevelEditorDisplay editorDisplay;
    private FileInputStream fin; 
    private ObjectInputStream ois; 
    private LinkedList<Command> undoCom = new LinkedList(); 
    private LinkedList<Command>  doCom = new LinkedList(); 


    public GameComposer(){
        //this.setNumOfBlocks(20);
        editorDisplay = new LevelEditorDisplay(this);
        editorDisplay.setVisible(true);
    }

    public void canvasAction(int num, Point p){
        if(num == 9){    // 9 is the current number for delete
            removeObject(p);
        }else{      // add the object
            addNewObject(num, p);
        }
        if(!doCom.isEmpty()){
            // if the doCom has anything in it, there have been undone actions
            // this means, adding a new object should not allow the user to redo anything and so wer clear the array
            clearCommands();
        }
    }
    
    private void addNewObject(int num, Point p){
        AddCommand a = new AddCommand(p, num);  // create a command object to accomplish this task
        if(a.doCom(getCurrentLevel())){
            levelIsNotSaved();      // update the levelManger to reflect that the level has been modified since last save
            undoCom.push(a);        // if the task did anything, push the task onto the stack to be undone
        }
    }
    
    private void removeObject(Point p){ 
        RemoveCommand r = new RemoveCommand(p);    // create a removecommand to carry out the remove obect task
        if(r.doCom(getCurrentLevel())){
            levelIsNotSaved();      // update the levelManger to reflect that the level has been modified since last save
            undoCom.push(r);        // if the task did anything, push it onto the stack of tasks that can be undone
        }
    } 
    
    public void undo(){ 
        if(undoCom.size() > 0){ 
            undoCom.getFirst().undoCom(getCurrentLevel()); 
            doCom.push(undoCom.pop()); 
            levelIsNotSaved();      // update the levelManger to reflect that the level has been modified since last save
        }         
    } 
    
    public void doCom(){ 
        if(doCom.size() > 0){ 
            doCom.getFirst().doCom(getCurrentLevel()); 
            undoCom.push(doCom.pop()); 
            levelIsNotSaved();      // update the levelManger to reflect that the level has been modified since last save
        } 
    }
    
    public void clearCommands(){
        undoCom = new LinkedList();
        doCom = new LinkedList();
    }
    
    public void addLevelToGame(){
        File selectedFile = selectFile(0, getCurrentLevel().getPath());
        if(selectedFile != null){   // if the user cancels adding level to game, filename will be null
            try {
                fin = new FileInputStream(selectedFile);
                ois = new ObjectInputStream(fin);
                Level inputLevel = readLevel(ois);
                try{
                    inputLevel.checkCompletion();
                    getGame().addLevel(inputLevel);
                    editorDisplay.updateLevelOrganizer(getGame().getLevelNames());
                }catch(IncompleteLevelException ex){
                    JOptionPane.showMessageDialog(editorDisplay, ex.toString());
                }
            } catch (IOException ex) {
                System.err.println(ex);
            } catch (ClassNotFoundException ex) {
                System.err.println(ex);
            }
        }else{
            System.out.println("Adding level cancelled");
        }
    }
    
    public void removeLevelFromGame(int pos){
        getGame().removeLevel(pos);
        editorDisplay.updateLevelOrganizer(getGame().getLevelNames());
    }
    
    public void moveLevel(int init, int dest){
        getGame().moveLevel(init, dest);
        editorDisplay.updateLevelOrganizer(getGame().getLevelNames());
    }
    
    public void setLevelTimeLimit(int time){
        getCurrentLevel().setTimeLimit(time);
    }
    
    public void newLevel(int gridWidth, int gridHeight){
        setCurrentLevel(new Level(gridWidth, gridHeight));    // creates a new level with gridWidth x gridHeight blocks
        clearCommands();    // clear the command array
        editorDisplay.updateTimeLimitBoxText(getCurrentLevel().getTimeLimit());         // set timelimit box back to default
    }
    
    public void openLevel(){
        File fileName = selectFile(0, getCurrentLevel().getPath());
        if(fileName != null){   // if the user cancels opening, filename will be null
            try {
                fin = new FileInputStream(fileName);
                ois = new ObjectInputStream(fin);
                setCurrentLevel(readLevel(ois));
                System.out.println("opening " + getCurrentLevel().getLevelName());
                ois.close();
                clearCommands();    // clear the command array
                editorDisplay.updateTimeLimitBoxText(getCurrentLevel().getTimeLimit());
            } catch (IOException ex) {
                System.err.println(ex);
            } catch (ClassNotFoundException ex) {
                System.err.println(ex);
            }
        }else{
            System.out.println("open level cancelled");
        }
    }
      
    public void openGame(){
        try{
            loadGame(false);
            editorDisplay.updateLevelOrganizer(getGame().getLevelNames());
            clearCommands();    // clear the command array
        }catch(ClassCastException ex){
            System.err.println("incompatable filetype selected");
            JOptionPane.showMessageDialog(editorDisplay, "Incompatible filetype was selected");
        }
        if(getCurrentLevel() != null){
            editorDisplay.updateTimeLimitBoxText(getCurrentLevel().getTimeLimit());
        }
    }
 
    public void saveLevelAs(){
        File fileName = selectFile(1, getCurrentLevel().getPath());
            if(fileName != null){   // if the user cancels saving, filename will be null
            try {
                getCurrentLevel().setTimeLimit(editorDisplay.getTimeLimit());    // get integer in the time limit field and store in level
                FileOutputStream fout = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                getCurrentLevel().setPath(fileName.getAbsolutePath());   // set the level's path string for direct saving
                getCurrentLevel().setLevelName(fileName.getName()); // set the level's name string to match its file name with extension
                System.out.println("saving " + getCurrentLevel().getLevelName());
                exportLevel(fileName, oos, false);
                levelIsSaved();      // update the levelManger to reflect that the level has been saved
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }else{
            System.out.println("save level cancelled");
        }
    }
    
    public void saveLevel(){
        if(getCurrentLevel().getPath() != null){
            System.out.println(getCurrentLevel().getPath());
            try {
                getCurrentLevel().setTimeLimit(editorDisplay.getTimeLimit());    // get integer in the time limit field and store in level
                File tempFile = new File(getCurrentLevel().getPath());
                FileOutputStream fout = new FileOutputStream(tempFile);
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                exportLevel(tempFile, oos, false);
                levelIsSaved();      // update the levelManger to reflect that the level has been saved
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }else{
            System.out.println(getCurrentLevel().getPath());
            saveLevelAs();
        }
    }
}
