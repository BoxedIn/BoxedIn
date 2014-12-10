package BoxedInEditor;

import java.awt.Graphics;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    protected Game g = new Game();
    protected Level currentLevel;
    protected FileInputStream fin;
    protected ObjectInputStream ois;
    private boolean saved = true;
    protected int levelNumber;
    
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
        ArrayList<BlockObject> blocks = new ArrayList();
        ArrayList<WoodenBoxObject> woodenBlocks = new ArrayList();
        ArrayList<BoulderObject> boulders = new ArrayList();
        ArrayList<Water> water = new ArrayList();
        StartPoint startpoint = null;
        EndPoint endpoint = null;

        if (null != (blocks = (ArrayList<BlockObject>) ois.readObject())) {
            woodenBlocks = (ArrayList<WoodenBoxObject>) ois.readObject();
            boulders = (ArrayList<BoulderObject>) ois.readObject();
            water = (ArrayList<Water>) ois.readObject();
            startpoint = (StartPoint) ois.readObject();
            endpoint = (EndPoint) ois.readObject();

            
            for (int i = 0; i < blocks.size(); i++) {
                GameObject go = blocks.get(i);
                inputLevel.addGameObject(go);
            }
            for (int i = 0; i < woodenBlocks.size(); i++) {
                GameObject go = woodenBlocks.get(i);
                inputLevel.addGameObject(go);
            }
            for (int i = 0; i < boulders.size(); i++) {
                GameObject go = boulders.get(i);
                inputLevel.addGameObject(go);
            }
            for (int i = 0; i < water.size(); i++) {
                GameObject go = water.get(i);
                inputLevel.addGameObject(go);
            }
            if (startpoint != null) {
                GameObject go1 = startpoint;
                inputLevel.addGameObject(go1);
            }
            if (endpoint != null) {
                GameObject go2 = endpoint;
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
    
    public void loadGame(){
        Level l;
        File fileName = selectFile(0, null);
        Player p;
        levelNumber = 0;
        
        try {
            fin = new FileInputStream(fileName);
            ois = new ObjectInputStream(fin);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
        int i = 0;
        try{
            while((l = readLevel(ois))!= null){
                p = new Player(l.getStart().getLocation());
                l.removeGameObject(l.getStart().getLocation());
                l.addGameObject(p);
                l.setPlayer(p);
                g.addLevel(l);
                i++;
            }
        }catch(ClassNotFoundException ex){
            System.err.println(ex);
        }catch(EOFException ex){
            System.out.println("reached end of file");
        }catch(IOException ex){
            System.out.println(ex);
        }
        try { 
            ois.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        Level temp = g.getLevel(0); // set level to level 0
        if(temp != null){
            setLevel(temp);     // set the currentLevel in the levelmanager to the first level
            System.out.println("current level has been set");
            for(int j = 0; j <= i; j++){
                System.out.println(g.getLevel(j));
            }
        }else{
            System.out.println("current level could not be set");
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
}
