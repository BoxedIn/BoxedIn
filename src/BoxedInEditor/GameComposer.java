/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import java.util.LinkedList;

/**
 *
 * @author tmb5577
 */
public class GameComposer extends LevelManager{
    private LevelEditorDisplay editorDisplay;
    private GameObject[] availableObjects = new GameObject[5];
    private FileInputStream fin; 
    private ObjectInputStream ois; 
    private Game game = new Game();         // **** not sure if this follows with the design ***   
    private LinkedList<Command> undoCom = new LinkedList(); 
    private LinkedList<Command>  doCom = new LinkedList(); 


    public GameComposer(){
        editorDisplay = new LevelEditorDisplay(this);
        editorDisplay.setVisible(true);
    }
    
    
    // this method may be used to dynamically add buttons to the toolbar in the editor
//    private void createButton(){
//        JButton b = new JButton();
//        b.setText("Circle");
//        b.setPreferredSize(new java.awt.Dimension(70, 23));
//        editorDisplay.addButton(b);
//    }
    
    public void canvasAction(int num, Point p){
        if(num == 7){    // 7 is the current number for delete
            removeObject(p);
        }else{      // add the object
            addNewObject(num, p);
        }
    }
    
    private void addNewObject(int num, Point p){
        AddCommand a = new AddCommand(currentLevel, num, p);  // create a command object to accomplish this task
        if(a.doCom()){
            levelIsNotSaved();      // update the levelManger to reflect that the level has been modified since last save
            undoCom.push(a);        // if the task did anything, push the task onto the stack to be undone
        }
    }
    
    private void removeObject(Point p){ 
        RemoveCommand r = new RemoveCommand(currentLevel, p);    // create a removecommand to carry out the remove obect task
        if(r.doCom()){
            levelIsNotSaved();      // update the levelManger to reflect that the level has been modified since last save
            undoCom.push(r);        // if the task did anything, push it onto the stack of tasks that can be undone
        }
    } 
    
    public void undo(){ 
        if(undoCom.size() > 0){ 
            undoCom.getFirst().undoCom(); 
            doCom.push(undoCom.pop()); 
            levelIsNotSaved();      // update the levelManger to reflect that the level has been modified since last save
        }         
    } 
    
    public void doCom(){ 
        if(doCom.size() > 0){ 
            doCom.getFirst().doCom(); 
            undoCom.push(doCom.pop()); 
            levelIsNotSaved();      // update the levelManger to reflect that the level has been modified since last save
        } 
    }
    
    public void addLevelToGame(){
        File selectedFile = selectFile(0, currentLevel.getPath());
        if(selectedFile != null){   // if the user cancels adding level to game, filename will be null
            try {
                fin = new FileInputStream(selectedFile);
                ois = new ObjectInputStream(fin);
                Level inputLevel = readLevel(ois);
                if(inputLevel.checkCompletion()){
                    game.addLevel(inputLevel);
                    editorDisplay.updateLevelOrganizer(game.getLevelOrder());
                }else{
                    System.out.println("This level is not complete!!!");
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
        game.removeLevel(pos);
        editorDisplay.updateLevelOrganizer(game.getLevelOrder());
    }
    
    public void moveLevel(int init, int dest){
        game.moveLevel(init, dest);
        editorDisplay.updateLevelOrganizer(game.getLevelOrder());
    }
    
    
//    public File selectFile(int soo){
//        JFileChooser fileChooser = new JFileChooser();
//        
//        if(soo == 1){
//            fileChooser.showSaveDialog(editorDisplay);
//        }
//        else{
//            fileChooser.showOpenDialog(editorDisplay);
//        }
//        
//        File fileName = fileChooser.getSelectedFile();
//        return fileName;
//    }
    
    public File selectFile(int soo, String fileName){
        JFileChooser fileChooser = new JFileChooser();
        if(fileName != null){
            System.out.println("name exists");
            fileChooser.setSelectedFile(new File(currentLevel.getPath()));
        }
        
        if(soo == 1){
            fileChooser.showSaveDialog(editorDisplay);
        }
        else{
            fileChooser.showOpenDialog(editorDisplay);
        }
        
        File file = fileChooser.getSelectedFile();
        return file;
    }    
    
//    public void openLevel(){
//        File fileName = selectFile(0, currentLevel.getPath());
//        if(fileName != null){   // if the user cancels opening, filename will be null
//            try {
//                fin = new FileInputStream(fileName);
//                ois = new ObjectInputStream(fin);
//                currentLevel = readLevel(ois);
//                System.out.println("opening " + currentLevel.getLevelName());
//                ois.close();
//            } catch (IOException ex) {
//                System.err.println(ex);
//            } catch (ClassNotFoundException ex) {
//                System.err.println(ex);
//            }
//        }else{
//            System.out.println("open level cancelled");
//        }
//    }
    
    public void newLevel(int gridWidth, int gridHeight){
        currentLevel = new Level(gridWidth, gridHeight);    // creates a new level with gridWidth x gridHeight blocks
    }
 
    public void saveLevelAs(){
        File fileName = selectFile(1, currentLevel.getPath());
            if(fileName != null){   // if the user cancels saving, filename will be null
            try {
                FileOutputStream fout = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                currentLevel.setPath(fileName.getAbsolutePath());   // set the level's path string for direct saving
                currentLevel.setLevelName(fileName.getName()); // set the level's name string to match its file name with extension
                System.out.println("saving " + currentLevel.getLevelName());
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
        if(currentLevel.getPath() != null){
            System.out.println(currentLevel.getPath());
            try {
                File tempFile = new File(currentLevel.getPath());
                FileOutputStream fout = new FileOutputStream(tempFile);
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                exportLevel(tempFile, oos, false);
                levelIsSaved();      // update the levelManger to reflect that the level has been saved
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }else{
            System.out.println(currentLevel.getPath());
            saveLevelAs();
        }
    }
    
    public void exportLevel(File fileName, ObjectOutputStream stream, boolean append) throws IOException {
        ArrayList<GameObject> blocks = new ArrayList();
        ArrayList<GameObject> woodenBoxes = new ArrayList();
        ArrayList<GameObject> boulders = new ArrayList();
        ArrayList<GameObject> water = new ArrayList();
        GameObject startpoint = null;
        GameObject endpoint = null;
        
        try {
            GameObject[][] go = currentLevel.getGameObject();
            
            for(int i = 0; i< go.length; i++){
                for(int j = 0; j < go[i].length; j++){
                    if(go[i][j] instanceof BlockObject){
                        blocks.add(go[i][j]);
                    }else if(go[i][j] instanceof WoodenBoxObject){
                        woodenBoxes.add(go[i][j]);
                    }else if(go[i][j] instanceof BoulderObject){
                        boulders.add(go[i][j]);
                    }else if(go[i][j] instanceof Water){
                        water.add(go[i][j]);
                    }else if(go[i][j] instanceof StartPoint){
                        startpoint = go[i][j];
                    }else if(go[i][j] instanceof EndPoint){
                        endpoint = go[i][j];
                    }
                }
            }
            
            stream.writeObject(blocks);      // write blocks arraylist to file
            stream.writeObject(woodenBoxes);      // write woodenBoxes arraylist to file
            stream.writeObject(boulders);      // write boulders arraylist to file
            stream.writeObject(water);      // write water arraylist to file
            stream.writeObject(startpoint);
            stream.writeObject(endpoint);
            stream.writeObject(currentLevel.getLevelName());
            stream.writeObject(currentLevel.getPath());
            System.out.println("level saved successfully");
            
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }    
    }
    
    public boolean buildGame(){
        boolean success = false;
        System.out.println("Inside Build Game");
        File fileName = selectFile(1, null);
        if(fileName != null){   // if the user cancels building, filename will be null
            FileOutputStream fout;
            ObjectOutputStream oos;
            for(int i = 0; i < game.levels.size(); i++){
                System.out.println(game.levels.get(i).getLevelName());
            }
            Level tLevel = this.currentLevel;  // stores the current running level so we can restore after building game
            try {
                 fout = new FileOutputStream(fileName);
                 oos = new ObjectOutputStream(fout); 
                for(int i = 0; i<game.levels.size(); i++){
                    this.currentLevel = game.levels.get(i);    // saveLevel save this.level, so it must be set here
                    this.exportLevel(fileName, oos, true);
                }
                oos.close();
                fout.close();
                success = true;
            } catch (IOException ex) {
                System.err.println(ex);
            }
            for(int i = 0; i < game.levels.size(); i++){
                System.out.println(game.levels.get(i).getLevelName());
            }
        
            this.currentLevel = tLevel;    // reassign the original level to current level
        }else{
                System.out.println("Build cancelled");
        }
        return success;
    }
}
