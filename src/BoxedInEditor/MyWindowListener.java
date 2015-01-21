/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInEditor;

import BoxedInRunner.BoxedInRunner;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author andrew
 */
public class MyWindowListener extends WindowAdapter{
    private LevelManager parent;
    private final String PROMPT = "Are you sure you want to exit?";
    private String title = "";
    
    public MyWindowListener(LevelManager c){
        parent = c;
        if(parent instanceof GameComposer){
            title = "BoxedIn Editor";
        }else if(parent instanceof BoxedInRunner){
            title = "BoxedIn Runner";
        }
    }
    
    @Override
    public void windowClosing(WindowEvent we)
    { 
        if(parent.needsSaved()){
            String ObjButtons[] = {"Save", "Close Without Saving"};
            int promptResult = JOptionPane.showOptionDialog(null, PROMPT, title, 
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
                ObjButtons,ObjButtons[0]);
            if(promptResult == 0)
            {
                if(parent instanceof GameComposer){
                        ((GameComposer)parent).saveLevel();
                }else if(parent instanceof BoxedInRunner){
                    ((BoxedInRunner)parent).saveGame();
                }
            }
        }

        System.exit(0);
    }
}
