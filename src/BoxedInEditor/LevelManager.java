package BoxedInEditor;

import java.awt.Graphics;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jds5782
 */
public abstract class LevelManager {
    ImageUtility imageUtility = new ImageUtility();
    protected Level currentLevel;
    protected FileInputStream fin;
    protected ObjectInputStream ois;
    private boolean saved = true;
    protected Game game = new Game();
    
    public void drawLevel(Graphics g){
        if(currentLevel != null){  // only call draw if there is a level 
            currentLevel.drawBackground(g);
            currentLevel.drawObjects(g);
        }
    }
    
    public void setLevel(Level l){
        currentLevel = l;
    }

    public String isLevelInit(){
        if(currentLevel != null){
            return "good";
        }else
            return "its null";
    }
    
    public int getLevelWidth(){
        return currentLevel.getLevelWidth();
    }
    
    protected Level readLevel(ObjectInputStream ois) throws IOException, ClassNotFoundException {

        Level inputLevel = new Level(20, 20);
        ArrayList<BlockObject> squares = new ArrayList();
        ArrayList<WoodenBoxObject> triangles = new ArrayList();
        ArrayList<BoulderObject> circles = new ArrayList();
        Point startpoint = null;
        Point endpoint = null;

        if (null != (squares = (ArrayList<BlockObject>) ois.readObject())) {
            triangles = (ArrayList<WoodenBoxObject>) ois.readObject();
            circles = (ArrayList<BoulderObject>) ois.readObject();
            startpoint = (Point) ois.readObject();
            endpoint = (Point) ois.readObject();
            for (int i = 0; i < squares.size(); i++) {
                GameObject go = squares.get(i);
                inputLevel.addGameObject(go);
            }
            for (int i = 0; i < triangles.size(); i++) {
                GameObject go = triangles.get(i);
                inputLevel.addGameObject(go);
            }
            for (int i = 0; i < circles.size(); i++) {
                GameObject go = circles.get(i);
                inputLevel.addGameObject(go);
            }
            if (startpoint != null) {
                StartPoint s = new StartPoint(startpoint);
                inputLevel.addGameObject(s);
            }
            if (endpoint != null) {
                EndPoint go2 = new EndPoint(endpoint);
                inputLevel.addGameObject(go2);
            }

            inputLevel.setLevelName((String)ois.readObject());
            inputLevel.setPath((String)ois.readObject());
            System.out.println("level opened successfully");

            return inputLevel;
        }
        return null;
    }
    
    public File selectFile(int soo, String fileName){
        JFileChooser fileChooser = new JFileChooser();
        if(fileName != null){
            System.out.println("name exists");
            fileChooser.setSelectedFile(new File(currentLevel.getPath()));
        }
        
        if(soo == 1){
            fileChooser.showSaveDialog(fileChooser);
        }
        else{
            fileChooser.showOpenDialog(fileChooser);
        }
        
        File file = fileChooser.getSelectedFile();
        return file;
    }
 
    
    public void openLevel(){
        File fileName = selectFile(0, currentLevel.getPath());
        if(fileName != null){   // if the user cancels opening, filename will be null
            try {
                fin = new FileInputStream(fileName);
                ois = new ObjectInputStream(fin);
                currentLevel = readLevel(ois);
                System.out.println("opening " + currentLevel.getLevelName());
                ois.close();
            } catch (IOException ex) {
                System.err.println(ex);
            } catch (ClassNotFoundException ex) {
                System.err.println(ex);
            }
        }else{
            System.out.println("open level cancelled");
        }
    }
    
    public boolean needsSaved(){
        // returns opposite of the saved state variable, saved should return false for needing to save
        return !saved;
    }
    
    // called when a save is completed
    public void levelIsSaved(){
        saved = true;
    }
    
    // called when a change is made to keep track if a level has unsaved changes that have been made
    public void levelIsNotSaved(){
        saved = false;
    }
    public void exportLevel(File fileName, ObjectOutputStream stream, boolean append) throws IOException {
        ArrayList<GameObject> squares = new ArrayList();
        ArrayList<GameObject> triangles = new ArrayList();
        ArrayList<GameObject> circles = new ArrayList();
        Point startpoint = null;
        Point endpoint = null;
        
        try {
            GameObject[][] go = currentLevel.getGameObject();
            startpoint = currentLevel.getStart();
            endpoint = currentLevel.getEnd();
            for(int i = 0; i< go.length; i++){
                for(int j = 0; j < go[i].length; j++){
                    if(go[i][j] instanceof BlockObject){
                        
                        squares.add(go[i][j]);
                    }
                    if(go[i][j] instanceof WoodenBoxObject){
                        triangles.add(go[i][j]);
                    }
                    if(go[i][j] instanceof BoulderObject){
                        circles.add(go[i][j]);
                    }

                }
            }

            //stream.writeObject(game.getCurrentLevel());
            stream.writeObject(squares);
            stream.writeObject(triangles);
            stream.writeObject(circles);
            stream.writeObject(startpoint);
            stream.writeObject(endpoint);
            stream.writeObject(currentLevel.getLevelName());
            stream.writeObject(currentLevel.getPath());
            System.out.println("level saved successfully");
            
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }    
    }
        
    public void buildGame(){
        System.out.println("Inside Build Game");
        File fileName = selectFile(1, currentLevel.getPath());
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
                 oos.writeObject(game.getCurrentLevel());
                for(int i = 0; i<game.levels.size(); i++){
                    this.currentLevel = game.levels.get(i);    // saveLevel save this.level, so it must be set here
                    this.exportLevel(fileName, oos, true);
                }
                
                oos.close();
                fout.close();
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
    }   
        
        
        
        
}
