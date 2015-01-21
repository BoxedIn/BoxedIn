package BoxedInEditor;

import java.awt.Graphics;
import java.awt.Point;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private Game game = new Game();
    private Level currentLevel;
    private int NUM_OF_BLOCKS = 20;     // will implement methods to modify this later
    private FileInputStream fin;
    private ObjectInputStream ois;
    private boolean saved = true;
    private int levelNumber;
    
    public void drawBackground(Graphics g){
        if(getCurrentLevel() != null){
            getCurrentLevel().drawBackground(g);
        }
    }
    public void drawLevel(Graphics g){
        if(getCurrentLevel() != null){  // only call draw if there is a level 
            getCurrentLevel().drawObjects(g);
        }
    }
    
    public void setLevel(Level l){
        setCurrentLevel(l);
    }

    public String isLevelInit(){
        if(getCurrentLevel() != null){
            return "good";
        }else
            return "its null";
    }
    
    public int getLevelWidth(){
        return getCurrentLevel().getLevelWidth();
    }
    
    public int getLevelTimeLimit(){
        return getCurrentLevel().getTimeLimit();
    }
    
     protected Level readLevel(ObjectInputStream ois) throws IOException, ClassNotFoundException {

        Level inputLevel = new Level(20, 20);
        Point startpoint = null;
        Point endpoint = null;
        ArrayList <GameObject> gameObjectIn = (ArrayList) ois.readObject();
        if (gameObjectIn != null){
            inputLevel.setGameObject(gameObjectIn);
            inputLevel.setStart((Point) ois.readObject());
            inputLevel.setEnd((Point) ois.readObject());
            inputLevel.setLevelName((String)ois.readObject());
            inputLevel.setPath((String)ois.readObject());
            inputLevel.setTimeLimit((int)ois.readObject());
            System.out.println("level opened successfully");

            return inputLevel;
        }
        return null;
    }
       
    public File selectFile(int soo, String fileName){
        int returnValue; 
        File file;
        JFileChooser fileChooser = new JFileChooser();
        if(fileName != null){
            fileChooser.setSelectedFile(new File(getCurrentLevel().getPath()));
        }
        
        if(soo == 1){
            returnValue = fileChooser.showSaveDialog(fileChooser);
        }
        else{
            returnValue = fileChooser.showOpenDialog(fileChooser);
        }
        if(returnValue == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();  // user selected a file, continue
        }else{
            file = null;    // the user cancelled, return null
        }
        return file;
    }
    
    protected void loadGame(boolean restart) throws ClassCastException{
        Level l;
        Player p;
        File fileName;
        String temps = getGame().getFilePath();
    
        
        if(!restart){
            fileName = selectFile(0 ,getGame().getFilePath());
 
        }else{
            fileName = new File(getGame().getFilePath());       // open file dialog
        }
        
        int i = 0;
        if(fileName != null){
            setGame(null);      // remove current game
            setGame(new Game());    // create new game
            getGame().setFilePath(fileName.getPath());   // set filepath to oldfilepath
     
            try{
                fin = new FileInputStream(fileName);
                ois = new ObjectInputStream(fin);
                getGame().setCurrentLevel((int) ois.readObject());
                System.out.println(getGame().getCurrentLevel());
                while((l = readLevel(ois))!= null){
                    System.out.print("l.getStart() returns ");
                    System.out.println(l.getStart());
                    if(l.getStart()!= null){
                        
                        p = new Player(l.getStart().getLocation());
                        l.removeGameObject(l.getStart().getLocation());
                        l.addGameObject(p);
                        l.setPlayer(p);
                    }
                    getGame().addLevel(l);
                    i++;
                }
                ois.close();
            }catch(ClassNotFoundException ex){
                System.out.println("Open Cancelled");
            }catch(EOFException ex){
                System.out.println("reached end of file");
            } catch (IOException ex) {
                System.out.println("Open Cancelled");
            }
            
            if(!restart){
                setLevelNumber(getGame().getCurrentLevel());
            }else{
                getGame().setCurrentLevel(getLevelNumber());
            }
            
            Level temp = getGame().getLevel(getLevelNumber()); // for testing, manually set the level
            if(temp != null){
                setLevel(temp);     // set the currentLevel in the levelmanager to the first level
                System.out.println("current level has been set");
                for(int j = 0; j < i; j++){
                    System.out.println(getGame().getLevel(j));
                }
            }else{
                System.out.println("current level could not be set");
            }
                }
        else{
            System.out.println("Open Cancelled");
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
        Point startpoint = null;
        Point endpoint = null;
        
        try {
            ArrayList <GameObject> ago = new ArrayList();
            GameObject[][] go = getCurrentLevel().getGameObject();
            for(int i= 0; i <go.length; i++){
                for(int j = 0; j < go.length; j++){
                    ago.add(go[i][j]);
                }
            }
            
                if(getCurrentLevel().getStart() != null){
                    System.out.println("start not nul");
                }
                if(getCurrentLevel().getEnd() != null){
                    System.out.println("end not nul");
                }
            startpoint = getCurrentLevel().getStart();
            endpoint = getCurrentLevel().getEnd();
            stream.writeObject(ago);
            stream.writeObject(startpoint);
            stream.writeObject(endpoint);
            stream.writeObject(currentLevel.getLevelName());
            stream.writeObject(currentLevel.getPath());
            stream.writeObject(currentLevel.getTimeLimit());
            System.out.println("level saved successfully");
            
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }    
    }

        
    public int buildGame(){
        // returns 0 - successful build
        // returns 1 - general failure
        // returns 2 - if the game had no levels
        int success = 1;    // setup for default general failure
        if(game.getNumberOfLevels() > 0){
            File fileName = selectFile(1, getCurrentLevel().getPath());
            if(fileName != null){   // if the user cancels building, filename will be null
                FileOutputStream fout;
                ObjectOutputStream oos;
                for(int i = 0; i < getGame().getLevels().size(); i++){
                    System.out.println(getGame().getLevels().get(i).getLevelName());
                }
                Level tLevel = this.getCurrentLevel();  // stores the current running level so we can restore after building game
                try {
                     fout = new FileOutputStream(fileName);
                     oos = new ObjectOutputStream(fout); 
                     oos.writeObject(getGame().getCurrentLevel());
                    for(int i = 0; i<getGame().getLevels().size(); i++){
                        this.setCurrentLevel(getGame().getLevels().get(i));    // saveLevel save this.level, so it must be set here
                        this.exportLevel(fileName, oos, true);
                    }

                    oos.close();
                    fout.close();
                    success = 0;
                } catch (IOException ex) {
                    System.err.println(ex);
                }
                for(int i = 0; i < getGame().getLevels().size(); i++){
                    System.out.println(getGame().getLevels().get(i).getLevelName());
                }

                this.setCurrentLevel(tLevel);    // reassign the original level to current level
            }else{
                    System.out.println("Build cancelled");
            }
        }else{
            success = 2;    // set return value to show game had no levels
        }
        return success;
    }   
    
    public abstract void openGame();
    
    public LinkedList<String> getLevelOrder(){
        return getGame().getLevelNames();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getNumOfBlocks() {
        return NUM_OF_BLOCKS;
    }

    public void setNumOfBlocks(int NUM_OF_BLOCKS) {
        this.NUM_OF_BLOCKS = NUM_OF_BLOCKS;
    }

    /**
     * @return the levelNumber
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * @param levelNumber the levelNumber to set
     */
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }
}
