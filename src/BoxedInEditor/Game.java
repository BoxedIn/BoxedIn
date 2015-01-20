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
    private LinkedList<Level> levels = new LinkedList();
    private LinkedList<String> levelNames = new LinkedList();
    private int currentLevel = 0;
    private String filePath;
    
    public void Game(){
    }
    
    public void addLevel(Level l){
        // add this element to end of list
        getLevels().add(l);
        getLevelNames().add(l.getLevelNameWithoutExt());
        System.out.println(l.getLevelNameWithoutExt());
    }
    
    public Level getLevel(int i){
        if(i < getLevels().size()){
            return getLevels().get(i);
        }else
            return null;
    }
    
    public void removeLevel(int pos){
        // remove level at given position
        getLevels().remove(pos);
        getLevelNames().remove(pos);
    }
    
    public void moveLevel(int origin, int destination){
        // swap levels in these two positions
        Level temp = getLevels().get(destination);   // get the element at the destination position
        String t = getLevelNames().get(destination);
        getLevels().set(destination, getLevels().get(origin)); // move level from old position to new position
        getLevelNames().set(destination, getLevelNames().get(origin));
        getLevels().set(origin, temp);       // put level from origin to destination
        getLevelNames().set(origin, t);
        temp = null;    // set temp for garbage collection
        t = null;
    }

    public Level nextLevel(){
        setCurrentLevel(getCurrentLevel() + 1);
        if(getCurrentLevel() != this.getLevels().size()){
            return getLevels().get(getCurrentLevel());
        }
        else{
            return null;
        }
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the currentLevel
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * @param currentLevel the currentLevel to set
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * @return the levels
     */
    public LinkedList<Level> getLevels() {
        return levels;
    }

    /**
     * @param levels the levels to set
     */
    public void setLevels(LinkedList<Level> levels) {
        this.levels = levels;
    }

    /**
     * @return the levelNames
     */
    public LinkedList<String> getLevelNames() {
        return levelNames;
    }

    /**
     * @param levelNames the levelNames to set
     */
    public void setLevelNames(LinkedList<String> levelNames) {
        this.levelNames = levelNames;
    }
    
    public int getNumberOfLevels(){
        return levels.size();       // return the size of the levels array
    }
    
}
