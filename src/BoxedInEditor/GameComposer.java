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
    GameObject[] availableObjects = new GameObject[5];
    FileInputStream fin; 
    ObjectInputStream ois; 
    Game game = new Game();         // **** not sure if this follows with the design ***   
    //Level level;      //  level has been moved to the levelManager class
    LinkedList<Command> undoCom = new LinkedList(); 
    LinkedList<Command>  doCom = new LinkedList(); 
    int saveOrOpen = 0;


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
        if(num == 6){    // 6 is the current number for delete
            removeObject(p);
        }else{      // add the object
            addNewObject(num, p);
        }
    }
    
    private void addNewObject(int num, Point p){
        AddCommand a = new AddCommand(currentLevel, num, p);  // create a command object to accomplish this task
        if(a.doCom()){
            undoCom.push(a);        // if the task did anything, push the task onto the stack to be undone
        }
    }
    
    private void removeObject(Point p){ 
        RemoveCommand r = new RemoveCommand(currentLevel, p);    // create a removecommand to carry out the remove obect task
        if(r.doCom()){
            undoCom.push(r);        // if the task did anything, push it onto the stack of tasks that can be undone
        }
    } 
    
    public void undo(){ 
        if(undoCom != null){ 
            undoCom.getFirst().undoCom(); 
            doCom.push(undoCom.pop()); 
        }         
    } 
    
    public void doCom(){ 
        if(doCom != null){ 
            doCom.getFirst().doCom(); 
            undoCom.push(doCom.pop()); 
        } 
    }
    
    public void addLevelToGame(){
        File selectedFile = selectFile(saveOrOpen);
        
        try {
            fin = new FileInputStream(selectedFile);
            ois = new ObjectInputStream(fin);
            Level inputLevel = readLevel(ois);
            if(inputLevel.checkCompletion()){
                // ******* hardcoded as true until i get the uptodate load with start and endpoints ************
                String fileName = selectedFile.getName();
                game.addLevel(currentLevel, fileName);
                editorDisplay.updateLevelOrganizer(game.getLevelOrder());
            }else{
                System.out.println("This level is not complete!!!");
            }
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
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
    
    
    public File selectFile(int soo){
        JFileChooser fileChooser = new JFileChooser();
        
        if(soo == 1){
            fileChooser.showSaveDialog(editorDisplay);
        }
        else{
            fileChooser.showOpenDialog(editorDisplay);
        }
        
        File fileName = fileChooser.getSelectedFile();
        return fileName;
    }
    public void openLevel(){
        File fileName = selectFile(saveOrOpen);
        try {
            fin = new FileInputStream(fileName);
            ois = new ObjectInputStream(fin);
            currentLevel = readLevel(ois);
            ois.close();
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        }

    }
    
    public void newLevel(int gridWidth, int gridHeight){
        currentLevel = new Level(gridWidth, gridHeight);    // creates a new level with gridWidth x gridHeight blocks
    }
 
    public void saveLevel(){
        saveOrOpen = 1;
        File fileName = selectFile(saveOrOpen);
        saveOrOpen = 0;
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fout);            
            exportLevel(fileName, oos, false);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public void exportLevel(File fileName, ObjectOutputStream stream, boolean append) throws IOException {

        
        ArrayList <SaveableGameObject> squares = new ArrayList();
        ArrayList <SaveableGameObject> triangles = new ArrayList();
        ArrayList <SaveableGameObject> circles = new ArrayList();
        SaveableGameObject startpoint = null;
        SaveableGameObject endpoint = null;
        
        try {
            GameObject[][] go = currentLevel.getGameObject();
            
            for(int i = 0; i< go.length; i++){
                for(int j = 0; j < go[i].length; j++){
                    if(go[i][j] instanceof SquareObject){
                        SaveableGameObject sgo = new SaveableGameObject(go[i][j]);
                        squares.add(sgo);
                    }
                    if(go[i][j] instanceof TriangleObject){
                        SaveableGameObject sgo = new SaveableGameObject(go[i][j]);
                        triangles.add(sgo);
                    }
                    if(go[i][j] instanceof CircleObject){
                        SaveableGameObject sgo = new SaveableGameObject(go[i][j]);
                        circles.add(sgo);
                    }
                    if(go[i][j] instanceof StartPoint){
                        SaveableGameObject sgo = new SaveableGameObject(go[i][j]);
                        startpoint = sgo;
                    }
                    if(go[i][j] instanceof EndPoint){
                        SaveableGameObject sgo = new SaveableGameObject(go[i][j]);
                        endpoint = sgo;
                    }
                }
            }
            
            stream.writeObject(squares);
            stream.writeObject(triangles);
            stream.writeObject(circles);
            stream.writeObject(startpoint);
            stream.writeObject(endpoint);
            System.out.println("level saved successfully");
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }    
    }
    

    
    public void buildGame(){
        saveOrOpen = 1;
        File fileName = selectFile(saveOrOpen);
        saveOrOpen = 0;
        FileOutputStream fout;
        ObjectOutputStream oos;
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
        } catch (IOException ex) {
            System.err.println(ex);
        }
        this.currentLevel = tLevel;    // reassign the original level to current level
    }
}
