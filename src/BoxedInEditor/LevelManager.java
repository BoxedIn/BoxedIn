package BoxedInEditor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

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
    
    public void drawLevel(){
        if(currentLevel != null){  // only call draw if there is a level 
            currentLevel.drawObjects();
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
        ArrayList<SaveableGameObject> squares = new ArrayList();
        ArrayList<SaveableGameObject> triangles = new ArrayList();
        ArrayList<SaveableGameObject> circles = new ArrayList();
        SaveableGameObject startpoint = null;
        SaveableGameObject endpoint = null;

        if (null != (squares = (ArrayList<SaveableGameObject>) ois.readObject())) {
            triangles = (ArrayList<SaveableGameObject>) ois.readObject();
            circles = (ArrayList<SaveableGameObject>) ois.readObject();
            startpoint = (SaveableGameObject) ois.readObject();
            endpoint = (SaveableGameObject) ois.readObject();

            for (int i = 0; i < squares.size(); i++) {
                GameObject go = new SquareObject(squares.get(i));
                inputLevel.addGameObject(go);
            }
            for (int i = 0; i < triangles.size(); i++) {
                GameObject go = new TriangleObject(triangles.get(i));
                inputLevel.addGameObject(go);
            }
            for (int i = 0; i < circles.size(); i++) {
                GameObject go = new CircleObject(circles.get(i));
                inputLevel.addGameObject(go);
            }
            if (startpoint != null) {
                GameObject go1 = new StartPoint(startpoint);
                inputLevel.addGameObject(go1);
            }
            if (endpoint != null) {
                GameObject go2 = new EndPoint(endpoint);
                inputLevel.addGameObject(go2);
            }

            //ois.close();
            System.out.println("level opened successfully");

            return inputLevel;
        }
        return null;
    }
}
