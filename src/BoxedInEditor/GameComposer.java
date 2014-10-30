/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

/**
 *
 * @author tmb5577
 */
public class GameComposer {
    FileOutputStream fout; 
    ObjectOutputStream oos; 
    FileInputStream fin; 
    ObjectInputStream ois; 
    Level level;
    
    
    public void saveLevel() throws IOException {
        try {
            fout = new FileOutputStream("P:\\SWENG_411\\FinalProject\\test.ser");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(level);
            oos.close();
            System.out.println("saved successfully");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameComposer.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public void openLevel() throws IOException, ClassNotFoundException{
        try {
            fin = new FileInputStream("P:\\SWENG_411\\FinalProject\\test.ser");
            ois = new ObjectInputStream(fin);
            level = (Level)ois.readObject();
            ois.close();
            System.out.println("opened successfully");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameComposer.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
  
    
}
