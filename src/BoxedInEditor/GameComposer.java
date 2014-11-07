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
import java.util.Vector;
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
    
    public GameComposer(){
        editorDisplay = new LevelEditorDisplay(this);
        editorDisplay.setVisible(true);
        initImages();
    }
    
    private void initImages(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        images[0] = toolkit.getImage("box.gif");
        images[1] = toolkit.getImage("triangle.gif");
        images[2] = toolkit.getImage("circle.gif");
        SquareObject.gOImage = toolkit.getImage("box.gif");
    }
    
    public void scaleImages(int gridSpacing){
        Image i = toolkit.getImage("box.gif");
        SquareObject.gOImage = i.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
    }
    
    public void drawLevel(){
        level.drawObjects();
    }
    
    // this method may be used to dynamically add buttons to the toolbar in the editor
    private void createButton(){
        JButton b = new JButton();
        b.setText("Circle");
        b.setPreferredSize(new java.awt.Dimension(70, 23));
        editorDisplay.addButton(b);
    }
    
    public void addNewObject(int num, Point p){
        SquareObject so = new SquareObject(p);
        level.addGameObject(so);
        //level.drawObjects();
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

  
    
}
