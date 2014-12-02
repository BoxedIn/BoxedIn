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
    
    
    public void Game(){
    }
    
    public void addLevel(Level l, String name){
        // add this element to end of list
        levels.add(l);
        levelNames.add(name);
        System.out.println(name);
    }
    
    public Level getLevel(int i){
        if(i > 0 && i <= levels.size()){
            return levels.get(i);
        }else
            return null;
    }
    
    public LinkedList<String> getLevelOrder(){   
        for(String s: levelNames){
            System.out.println(s);
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
    
}
