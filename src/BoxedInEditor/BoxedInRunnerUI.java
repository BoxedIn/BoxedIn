/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BoxedInRunner;

import BoxedInEditor.ImageUtility;
import BoxedInEditor.Level;
import BoxedInEditor.MyWindowListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

/** 
 *
 * @author tmb5577
 */
public class BoxedInRunnerUI extends javax.swing.JFrame implements TimerObserver{
    private BoxedInRunner runner;
    private Graphics levelGraphics;
    private Image bg;
   
    public BoxedInRunnerUI(BoxedInRunner bir) {
        runner = bir;
        initComponents();
        levelGraphics = levelPanel.getGraphics();       // set the graphics object in the ImageUtility class
        bg = levelPanel.createImage(levelPanel.getWidth(), levelPanel.getHeight());
        KeyControl listener = new KeyControl();
	addKeyListener(listener);
        this.addWindowListener(new MyWindowListener(runner));
        setFocusable(true);     // make this jFrame focusable, so the user can have their key pressed go to the listener
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        bg.getGraphics().setColor(levelPanel.getBackground());
        runner.drawBackground(bg.getGraphics());
        runner.drawLevel(bg.getGraphics());
        levelPanel.getGraphics().drawImage(bg, 0, 0, null);
    }
    
    private void showPause(){

        this.setVisible(false);
        
        String prompt = "PAUSE MENU - Please remain calm\n";
        String title = "BoxedIn Runner";
        String ObjButtons[] = {"Resume", "Save and Exit", "Exit"};
        int x;
        
        while(runner.isPaused()){
            x = JOptionPane.showOptionDialog(null, prompt, title, 
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
                ObjButtons, ObjButtons[0]);
            if(x == 0){
                break;
            }
            else if(x == 1){
                runner.saveGame();
                quitRunner();
            }
            else if(x == 2){
                quitRunner();
            }
        }
        
        this.setVisible(true);
    }
    
    public void refreshVariables(){
        // if there is a current level opened, update all the variables needed to draw the level
        if(runner.levelIsOpen()){
            levelGraphics = levelPanel.getGraphics();
            bg = levelPanel.createImage(levelPanel.getWidth(), levelPanel.getHeight());
            int gridSpacing;
            int numberOfBlocks = runner.getLevelWidth();
            int width = levelPanel.getWidth();
            int height = levelPanel.getHeight();
            if(width < height){
                gridSpacing = width / numberOfBlocks;}  // divide display panel in to 20 x 20 grid
            else{
                gridSpacing = height / numberOfBlocks;}  // divide display panel in to 20 x 20 grid
            Level.boxPixelHeight = gridSpacing; // reset static variables in Level
            Level.boxPixelWidth = gridSpacing;
            ImageUtility.scaleContent(gridSpacing, runner.getNumOfBlocks());        // ***** this causes occasionaly NullPoitnerExceptions on startup and is the cause of the buggy drawing after resize ***
        }
    }
    
    @Override
    public void updateTime(int seconds){
        int s = seconds % 60;   // number of seconds
        int m = seconds / 60;   // number of minutes
        String time = Integer.toString(m);
        if(s < 10){
            time += ":0" + Integer.toString(s);
        }else{
            time += ":" + Integer.toString(s);
        } 
        timeLabel.setText(time);
    }
    
    private void hideMenuToolbar(){
        menuPanel.setVisible(false);
        this.setSize(new Dimension(this.getWidth() - 150, this.getHeight()));
    }
    
    private void showMenuToolbar(){
        menuPanel.setVisible(true);
        this.setSize(new Dimension(this.getWidth() + 150, this.getHeight()));
    }
    
    private void quitRunner(){
        // other things to check if the game has been saved?????
        System.exit(0); // quit the runner program
    }
    
    public void enableSaveButton(){
        // used to enable the save buttons when a game has been loaded
        saveGameButton.setEnabled(true);
        fileMenuSave.setEnabled(true);
    }
    
    public void disableSaveButton(){
        // used to disable the save button when the game has been won
        saveGameButton.setEnabled(false);
        fileMenuSave.setEnabled(false);
    }
    
    private class KeyControl implements KeyListener {
    @Override   
    public void keyPressed(KeyEvent e) {
        if(!runner.isPaused() && runner.levelIsOpen()){    // if the game is not paused
            if(e.getKeyCode() == KeyEvent.VK_UP) {       // if up key
                if(runner.processMove(Level.MOVE_UP)){
                    runner.movePlayer(Level.MOVE_UP);
                    repaint();
                }
            }else if(e.getKeyCode() == KeyEvent.VK_DOWN) {   // if down key
                if(runner.processMove(Level.MOVE_DOWN)){
                    runner.movePlayer(Level.MOVE_DOWN);
                    repaint();
                }
            }else if(e.getKeyCode() == KeyEvent.VK_LEFT) {   // if left key
                if(runner.processMove(Level.MOVE_LEFT)){
                    runner.movePlayer(Level.MOVE_LEFT);
                    repaint();
                }
            }else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {  // if right key
                if(runner.processMove(Level.MOVE_RIGHT)){
                    runner.movePlayer(Level.MOVE_RIGHT);
                    repaint();
                }
            }
        }
    }

        @Override
        public void keyTyped(KeyEvent e) {
            // do nothing
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // do nothing
        }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        levelPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        openGameButton = new javax.swing.JButton();
        restartButton = new javax.swing.JButton();
        saveGameButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        helpButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileDropdown = new javax.swing.JMenu();
        fileMenuOpen = new javax.swing.JMenuItem();
        fileMenuSave = new javax.swing.JMenuItem();
        fileMenuRestart = new javax.swing.JMenuItem();
        fileMenuExit = new javax.swing.JMenuItem();
        viewDropdown = new javax.swing.JMenu();
        menuToolbarCheckbox = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("BoxedIn Runner");
        setPreferredSize(new java.awt.Dimension(750, 600));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        levelPanel.setForeground(new java.awt.Color(102, 255, 102));
        levelPanel.setPreferredSize(new java.awt.Dimension(600, 300));

        javax.swing.GroupLayout levelPanelLayout = new javax.swing.GroupLayout(levelPanel);
        levelPanel.setLayout(levelPanelLayout);
        levelPanelLayout.setHorizontalGroup(
            levelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        levelPanelLayout.setVerticalGroup(
            levelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        getContentPane().add(levelPanel);

        menuPanel.setMaximumSize(new java.awt.Dimension(150, 32767));
        menuPanel.setMinimumSize(new java.awt.Dimension(150, 0));
        menuPanel.setPreferredSize(new java.awt.Dimension(150, 300));

        openGameButton.setText("Open Game");
        openGameButton.setFocusable(false);
        openGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openGameButtonActionPerformed(evt);
            }
        });

        restartButton.setText("Restart Level");
        restartButton.setFocusable(false);
        restartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartButtonActionPerformed(evt);
            }
        });

        saveGameButton.setText("Save Game");
        saveGameButton.setEnabled(false);
        saveGameButton.setFocusable(false);
        saveGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGameButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Time Remaining");

        timeLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        timeLabel.setForeground(new java.awt.Color(204, 51, 0));
        timeLabel.setText("0:00");

        helpButton.setText("HELP");
        helpButton.setFocusable(false);
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        pauseButton.setText("Pause Game");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openGameButton)
                            .addComponent(restartButton)
                            .addComponent(saveGameButton)))
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timeLabel)
                            .addComponent(jLabel1)))
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(helpButton))
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pauseButton)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(openGameButton)
                .addGap(18, 18, 18)
                .addComponent(pauseButton)
                .addGap(18, 18, 18)
                .addComponent(restartButton)
                .addGap(18, 18, 18)
                .addComponent(saveGameButton)
                .addGap(18, 18, 18)
                .addComponent(helpButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(timeLabel)
                .addGap(23, 23, 23))
        );

        getContentPane().add(menuPanel);

        fileDropdown.setText("File");

        fileMenuOpen.setText("Open Game");
        fileMenuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuOpenActionPerformed(evt);
            }
        });
        fileDropdown.add(fileMenuOpen);

        fileMenuSave.setText("Save Game");
        fileMenuSave.setEnabled(false);
        fileMenuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuSaveActionPerformed(evt);
            }
        });
        fileDropdown.add(fileMenuSave);

        fileMenuRestart.setText("Restart Level");
        fileMenuRestart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuRestartActionPerformed(evt);
            }
        });
        fileDropdown.add(fileMenuRestart);

        fileMenuExit.setText("Exit");
        fileMenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuExitActionPerformed(evt);
            }
        });
        fileDropdown.add(fileMenuExit);

        jMenuBar1.add(fileDropdown);

        viewDropdown.setText("View");

        menuToolbarCheckbox.setSelected(true);
        menuToolbarCheckbox.setText("Menu Toolbar");
        menuToolbarCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuToolbarCheckboxActionPerformed(evt);
            }
        });
        viewDropdown.add(menuToolbarCheckbox);

        jMenuBar1.add(viewDropdown);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>                        

    private void menuToolbarCheckboxActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // menuToolbar checkbox in view menu clicked
        if(menuToolbarCheckbox.isSelected()){
            showMenuToolbar();
        }else{
            hideMenuToolbar();
        }
    }                                                   

    private void openGameButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // menuToolbar open level button pressed
        runner.openGame();
        repaint();
    }                                              

    private void restartButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // restartLevel button pressed
        runner.restartLevel();
        repaint();
    }                                             

    private void formComponentResized(java.awt.event.ComponentEvent evt) {                                      
        // window is resized
        refreshVariables();
        if(levelGraphics!= null){
            runner.drawBackground(levelGraphics);
            runner.drawLevel(levelGraphics);
        }
        repaint();
        if(levelGraphics!= null){
            runner.drawBackground(levelGraphics);
            runner.drawLevel(levelGraphics);
        }
    }                                     

    private void formMouseClicked(java.awt.event.MouseEvent evt) {                                  
        // TODO add your handling code here:
        repaint();
        if(levelGraphics!= null){
            runner.drawBackground(levelGraphics);
            runner.drawLevel(levelGraphics);
        }
        
    }                                 

    private void fileMenuExitActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // file menu exit button
        quitRunner();
    }                                            

    private void fileMenuRestartActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // file menu restart level
        runner.restartLevel();
    }                                               

    private void fileMenuSaveActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // file menu save game button
        runner.saveGame();
    }                                            

    private void fileMenuOpenActionPerformed(java.awt.event.ActionEvent evt) {                                             
            // file menu open game button
            runner.openGame();
            runner.drawBackground(levelGraphics);
    }                                            

    private void saveGameButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
        runner.saveGame();
    }                                              

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // sidebar help menu button
        runner.helpMessage();
    }                                          

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        runner.pauseGame();
        showPause();
        runner.resumeGame();
        this.requestFocus(true);
    }                                           


    // Variables declaration - do not modify                     
    private javax.swing.JMenu fileDropdown;
    private javax.swing.JMenuItem fileMenuExit;
    private javax.swing.JMenuItem fileMenuOpen;
    private javax.swing.JMenuItem fileMenuRestart;
    private javax.swing.JMenuItem fileMenuSave;
    private javax.swing.JButton helpButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel levelPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JRadioButtonMenuItem menuToolbarCheckbox;
    private javax.swing.JButton openGameButton;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton restartButton;
    private javax.swing.JButton saveGameButton;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JMenu viewDropdown;
    // End of variables declaration                   
}
