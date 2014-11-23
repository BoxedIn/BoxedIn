/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInRunner;

import BoxedInEditor.Game;
import BoxedInEditor.Level;
import BoxedInEditor.LevelManager;
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
    
    public boolean levelIsOpen(){
        if(currentLevel != null){
            return true;
        }else{
            return false;
        }
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
        
        fin = new FileInputStream(fileName);
        ois = new ObjectInputStream(fin);
        
        int i = 0;
        try{
            while((l = readLevel(ois))!= null){
                g.addLevel(l, Integer.toString(i));
                i++;
            }
        }catch(ClassNotFoundException ex){
            System.err.println(ex);
        }catch(EOFException ex){
            System.out.println("reached end of file");
        }
        ois.close(); 
        
        Level temp = g.getLevel(1); // for testing, manually set the level
        if(temp != null){
            setLevel(temp);     // set the currentLevel in the levelmanager to the first level
            System.out.println("current level has been set");
        }else
            System.out.println("current level could not be set");
    }
    
    public void saveGame(){
        
    }
    public void continueGame(){
        
    }
    public void pauseGame(){
        
    }
    public void beginGame(){
    }
    public void processMove(){
 
    }
    public File selectFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(birui);
        File fileName = fileChooser.getSelectedFile();
        return fileName;
    }
}
