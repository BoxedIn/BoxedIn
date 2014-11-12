/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 *
 * @author tmb5577
 */
public class GameComposer {
    private LevelEditorDisplay editorDisplay;
    GameObject[] availableObjects = new GameObject[5];
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image[] images = new Image[3];
    FileOutputStream fout; 
    ObjectOutputStream oos; 
    FileInputStream fin; 
    ObjectInputStream ois; 
    Level level;
    Image square = toolkit.getImage("box.gif");
    Image triangle = toolkit.getImage("triangle.gif");
    Image circle  = toolkit.getImage("circle.gif");
    LinkedList <Command> undoCom = new LinkedList();
    LinkedList <Command>  doCom = new LinkedList();
    public GameComposer(){
        editorDisplay = new LevelEditorDisplay(this);
        editorDisplay.setVisible(true);
        initImages();
    }
    
    private void initImages(){
        SquareObject.gOImage = toolkit.getImage("box.gif");
        TriangleObject.gOImage = toolkit.getImage("triangle.gif");
        CircleObject.gOImage = toolkit.getImage("circle.gif");
    }
    
    public void scaleImages(int gridSpacing){
        SquareObject.gOImage = square.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        TriangleObject.gOImage = triangle.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        CircleObject.gOImage = circle.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
    }
    
    public void drawLevel(){
        level.drawObjects();
    }
    
    // this method may be used to dynamically add buttons to the toolbar in the editor
//    private void createButton(){
//        JButton b = new JButton();
//        b.setText("Circle");
//        b.setPreferredSize(new java.awt.Dimension(70, 23));
//        editorDisplay.addButton(b);
//    }
    
    public void addNewObject(int num, Point p){
        GameObject o = null;
        switch (num){
            case 1: o = new SquareObject(p);
                    break;
            case 2: o = new TriangleObject(p);
                    break;
            case 3: o = new CircleObject(p);
                    break;
            default:
                    break;
        }
        if(o != null){
            level.addGameObject(o);
            AddCommand a = new AddCommand(num, o.getLocation());
            undoCom.push(a);
        }
        //level.drawObjects();
    }
    
    public void removeObject(Point p){
        level.removeGameObject(p);      // pass the point to the level to remove the object
    }
    
    public void saveLevel() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(editorDisplay);
        File fileName = fileChooser.getSelectedFile();
        ArrayList <SaveableGameObject> squares = new ArrayList();
        ArrayList <SaveableGameObject> triangles = new ArrayList();
        try {
            System.out.println("got in");
            fout = new FileOutputStream(fileName);
            System.out.println("pass fout");
            oos = new ObjectOutputStream(fout);
            System.out.println("pass oos");
            GameObject[][] go = level.getGameObject();
            
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
                }
            }
            
            oos.writeObject(squares);
            oos.writeObject(triangles);
            System.out.println("pass oos = level.get");
            oos.close();
            System.out.println("saved successfully");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameComposer.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public void openLevel() throws IOException, ClassNotFoundException{
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(editorDisplay);
        File fileName = fileChooser.getSelectedFile();
        ArrayList <SaveableGameObject> squares = new ArrayList();
        ArrayList <SaveableGameObject> triangles = new ArrayList();
        try {
            System.out.println("got in");
            fin = new FileInputStream(fileName);
            System.out.println("pass fin");
            ois = new ObjectInputStream(fin);
            System.out.println("pass ois");
            
            
            squares = (ArrayList <SaveableGameObject>) ois.readObject();
            triangles = (ArrayList <SaveableGameObject>) ois.readObject();
            level = new Level(20, 20);
            
            for(int i = 0; i < squares.size(); i++){
                GameObject go = new SquareObject(squares.get(i));
                level.addGameObject(go);
            }
            for(int i = 0; i < triangles.size(); i++){
                GameObject go = new TriangleObject(triangles.get(i));
                level.addGameObject(go);
            }
            
            ois.close();
            System.out.println("opened successfully");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameComposer.class.getName()).log(Level.SEVERE, null, ex);
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
    
}
