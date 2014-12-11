package BoxedInEditor;


import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andrew
 */
public class Game {
    LinkedList<Level> levels = new LinkedList();
    LinkedList<String> levelNames = new LinkedList();
    int currentLevel = 0;
    private String filePath;
    
    public void Game(){
    }
    
    public void addLevel(Level l){
        // add this element to end of list
        levels.add(l);
        levelNames.add(l.getLevelNameWithoutExt());
        System.out.println(l.getLevelNameWithoutExt());
    }
    
    public Level getLevel(int i){
        if(i < levels.size()){
            return levels.get(i);
        }else
            return null;
    }
    
    public LinkedList<String> getLevelOrder(){  
        // just debugging
        for(Level l: levels){
            System.out.println(l.getLevelNameWithoutExt());
        }
        return levelNames;
    }
    
    public void removeLevel(int pos){
        // remove level at given position
        levels.remove(pos);
        levelNames.remove(pos);
    }
    
    public void moveLevel(int origin, int destination){
        // swap levels in these two positions
        Level temp = levels.get(destination);   // get the element at the destination position
        String t = levelNames.get(destination);
        levels.set(destination, levels.get(origin)); // move level from old position to new position
        levelNames.set(destination, levelNames.get(origin));
        levels.set(origin, temp);       // put level from origin to destination
        levelNames.set(origin, t);
        temp = null;    // set temp for garbage collection
        t = null;
    }

    public Level nextLevel(){
        currentLevel++;
        if(currentLevel != this.levels.size()){
            return levels.get(currentLevel);
        }
        else{
            return null;
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
}
