/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import BoxedInRunner.BoxedInRunner;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author azh5442
 */
public class LevelEditorDisplay extends javax.swing.JFrame {
    private GameComposer gc;
    private Graphics editorGraphics;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private int gridSpacing;
    private int selectedObject = 1;     //variable stores a number representing the object selected from the toolbar
    private Image currentImage;
    
    /**
     * Creates new form LevelEditorDisplay
     */
    public LevelEditorDisplay(GameComposer composer) {
        currentImage = ImageUtility.getBlockImage();
        initComponents();       // init frame componenets
        setButtonImages();
        //hideLevelOrganizer();       // set default state to not visible
        gc = composer;
        this.addWindowListener(new MyWindowListener(gc));
        editorGraphics = editorPanel.getGraphics();
        gridSpacing = editorPanel.getWidth() / gc.getNumOfBlocks();  // divide display panel in to 20 x 20 grid
        gc.getCurrentLevel().boxPixelHeight = gridSpacing; // setting static variable in Level
        gc.getCurrentLevel().boxPixelWidth = gridSpacing;
        gc.setCurrentLevel(new Level(gc.getNumOfBlocks(), gc.getNumOfBlocks()));
        // must initialize a default image. also set cursor to box
        refreshVariables();
        blockButtonActionPerformed(null);      // sets cursor to square
    }
    
    private void setButtonImages(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("new_file.png");
        toolbarNewButton.setIcon(new ImageIcon(image));
        image = toolkit.getImage("open_file.png");
        toolbarOpenButton.setIcon(new ImageIcon(image));
        image = toolkit.getImage("save_file.png");
        toolbarSaveButton.setIcon(new ImageIcon(image));
        image = toolkit.getImage("undo.png");
        toolbarUndoButton.setIcon(new ImageIcon(image));
        image = toolkit.getImage("redo.png");
        toolbarRedoButton.setIcon(new ImageIcon(image));
        image = toolkit.getImage("block.png");
        blockButton.setIcon(new ImageIcon(image));
        image = toolkit.getImage("boulder.png");
        boulderButton.setIcon(new ImageIcon(image));
        image = toolkit.getImage("woodenBox.png");
        woodenBoxButton.setIcon(new ImageIcon(image));
        image = toolkit.getImage("water.png");
        waterButton.setIcon(new ImageIcon(image));
        image = toolkit.getImage("telepad.png");
        teleportButton.setIcon(new ImageIcon(image));
        image = toolkit.getImage("start.png");
        startPointButton.setIcon(new ImageIcon(image));
        image = toolkit.getImage("end.png");
        endPointButton.setIcon(new ImageIcon(image));
        image = toolkit.getImage("remove.png");
        deleteButton.setIcon(new ImageIcon(image));
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        gc.drawBackground(editorGraphics);
        gc.drawLevel(editorGraphics);
        drawGrid();
    }
    
    
    private void drawGrid(){
        for(int i = 0; i <= gc.getNumOfBlocks(); i++){
            editorGraphics.drawLine(i*gridSpacing, 0, i*gridSpacing, gridSpacing*gc.getNumOfBlocks());   // vertical lines
            editorGraphics.drawLine(0, i*gridSpacing, gridSpacing*gc.getNumOfBlocks(), i*gridSpacing); // horizontal lines
        }
    }
    
    private void refreshVariables(){
        editorGraphics = editorPanel.getGraphics(); // update graphics object
        // divide window into even blocks with the smallest dimension of the screen as reference
        int width = editorPanel.getWidth();
        int height = editorPanel.getHeight();
        if(width < height && gc != null){
            System.out.println(width);

                gridSpacing = width / gc.getNumOfBlocks();
            
        }  // divide display panel in to 20 x 20 grid
        else if(gc != null){
            gridSpacing = height / gc.getNumOfBlocks();}  // divide display panel in to 20 x 20 grid
        Level.boxPixelHeight = gridSpacing; // reset static variables in Level
        Level.boxPixelWidth = gridSpacing;
        if(gc != null){
            ImageUtility.scaleContent(gridSpacing, gc.getNumOfBlocks());        // ***** this causes occasionaly NullPoitnerExceptions on startup and is the cause of the buggy drawing after resize ***
        }
    }
    
    public void updateLevelOrganizer(LinkedList<String> levelName){
        levelOrganizer.removeAll();
        for(String s: levelName){
            levelOrganizer.add(s);
        }
    }
    
    private void updateCursor(boolean on){
        //currentImage = currentImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        Point hotSpot = new Point(12, 12);
        if(on){
            if(currentImage != null){
            Cursor newCursor = toolkit.createCustomCursor(currentImage, hotSpot, "Cursor");
            setCursor(newCursor);
            }
        }else{
            setCursor(DEFAULT_CURSOR);
        }
    }
    
    private Point snapToGrid(int x, int y){
        int closestX = x / gridSpacing;
        int closestY = y / gridSpacing;
        Point p = new Point(closestX, closestY);
        return p;
    }
    
    private void hideLevelOrganizer(){
        jPanel5.setVisible(false);
        this.setSize(new Dimension(this.getWidth() - 150, this.getHeight()));
    }
    
    private void showLevelOrganizer(){
        jPanel5.setVisible(true);
        this.setSize(new Dimension(this.getWidth() + 150, this.getHeight()));
    }
    
    private void quitEditor(){
        if(gc.needsSaved()){
            gc.saveLevel();
        }
        System.exit(0); // quit the levelEditor program
    }

    public int getTimeLimit(){
        int time = 0;
        if(!timeLimitBox.getText().equals("")){
            time = Integer.parseInt(timeLimitBox.getText());
        }
        return time;
    }
    
    public void updateTimeLimitBoxText(int time){
        timeLimitBox.setText(Integer.toString(time));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        toolbarNewButton = new javax.swing.JButton();
        toolbarOpenButton = new javax.swing.JButton();
        toolbarSaveButton = new javax.swing.JButton();
        toolbarUndoButton = new javax.swing.JButton();
        toolbarRedoButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        blockButton = new javax.swing.JButton();
        boulderButton = new javax.swing.JButton();
        woodenBoxButton = new javax.swing.JButton();
        waterButton = new javax.swing.JButton();
        startPointButton = new javax.swing.JButton();
        endPointButton = new javax.swing.JButton();
        teleportButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        timeLimitBox = new javax.swing.JTextField();
        editorPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        levelOrganizer = new java.awt.List();
        moveUpButton = new javax.swing.JButton();
        moveDownButton = new javax.swing.JButton();
        addLevelButton = new javax.swing.JButton();
        removeLevelButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileDropdown = new javax.swing.JMenu();
        NewFile = new javax.swing.JMenuItem();
        OpenFile = new javax.swing.JMenuItem();
        OpenGame = new javax.swing.JMenuItem();
        SaveFile = new javax.swing.JMenuItem();
        SaveFileAs = new javax.swing.JMenuItem();
        Exit = new javax.swing.JMenuItem();
        buildDropdown = new javax.swing.JMenu();
        buildGame = new javax.swing.JMenuItem();
        buildRun = new javax.swing.JMenuItem();
        levelOrganizerRadioButton = new javax.swing.JRadioButtonMenuItem();

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("BoxedIn Editor");

        jPanel1.setBackground(new java.awt.Color(114, 164, 210));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 50));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        toolbarNewButton.setPreferredSize(new java.awt.Dimension(50, 63));
        toolbarNewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarNewButtonActionPerformed(evt);
            }
        });
        jPanel1.add(toolbarNewButton);

        toolbarOpenButton.setPreferredSize(new java.awt.Dimension(50, 63));
        toolbarOpenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarOpenButtonActionPerformed(evt);
            }
        });
        jPanel1.add(toolbarOpenButton);

        toolbarSaveButton.setPreferredSize(new java.awt.Dimension(50, 63));
        toolbarSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarSaveButtonActionPerformed(evt);
            }
        });
        jPanel1.add(toolbarSaveButton);

        toolbarUndoButton.setMaximumSize(new java.awt.Dimension(50, 45));
        toolbarUndoButton.setMinimumSize(new java.awt.Dimension(50, 45));
        toolbarUndoButton.setPreferredSize(new java.awt.Dimension(50, 45));
        toolbarUndoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarUndoButtonActionPerformed(evt);
            }
        });
        jPanel1.add(toolbarUndoButton);

        toolbarRedoButton.setMaximumSize(new java.awt.Dimension(50, 45));
        toolbarRedoButton.setMinimumSize(new java.awt.Dimension(50, 45));
        toolbarRedoButton.setPreferredSize(new java.awt.Dimension(50, 45));
        toolbarRedoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarRedoButtonActionPerformed(evt);
            }
        });
        jPanel1.add(toolbarRedoButton);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(700, 471));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jPanel3.setBackground(new java.awt.Color(114, 164, 210));
        jPanel3.setMaximumSize(new java.awt.Dimension(80, 32767));
        jPanel3.setMinimumSize(new java.awt.Dimension(80, 47));
        jPanel3.setPreferredSize(new java.awt.Dimension(80, 229));

        blockButton.setPreferredSize(new java.awt.Dimension(35, 35));
        blockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blockButtonActionPerformed(evt);
            }
        });
        jPanel3.add(blockButton);

        boulderButton.setPreferredSize(new java.awt.Dimension(35, 35));
        boulderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boulderButtonActionPerformed(evt);
            }
        });
        jPanel3.add(boulderButton);

        woodenBoxButton.setPreferredSize(new java.awt.Dimension(35, 35));
        woodenBoxButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                woodenBoxButtonActionPerformed(evt);
            }
        });
        jPanel3.add(woodenBoxButton);

        waterButton.setPreferredSize(new java.awt.Dimension(35, 35));
        waterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                waterButtonActionPerformed(evt);
            }
        });
        jPanel3.add(waterButton);

        startPointButton.setPreferredSize(new java.awt.Dimension(35, 35));
        startPointButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startPointButtonActionPerformed(evt);
            }
        });
        jPanel3.add(startPointButton);

        endPointButton.setPreferredSize(new java.awt.Dimension(35, 35));
        endPointButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endPointButtonActionPerformed(evt);
            }
        });
        jPanel3.add(endPointButton);

        teleportButton.setPreferredSize(new java.awt.Dimension(35, 35));
        teleportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teleportButtonActionPerformed(evt);
            }
        });
        jPanel3.add(teleportButton);

        deleteButton.setPreferredSize(new java.awt.Dimension(35, 35));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        jPanel3.add(deleteButton);

        jLabel1.setText("Time Limit");
        jPanel3.add(jLabel1);

        timeLimitBox.setPreferredSize(new java.awt.Dimension(50, 20));
        jPanel3.add(timeLimitBox);

        jPanel2.add(jPanel3);

        editorPanel.setBackground(new java.awt.Color(114, 164, 210));
        editorPanel.setPreferredSize(new java.awt.Dimension(470, 229));
        editorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editorPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                editorPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                editorPanelMouseExited(evt);
            }
        });
        editorPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                editorPanelComponentResized(evt);
            }
        });

        javax.swing.GroupLayout editorPanelLayout = new javax.swing.GroupLayout(editorPanel);
        editorPanel.setLayout(editorPanelLayout);
        editorPanelLayout.setHorizontalGroup(
            editorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 361, Short.MAX_VALUE)
        );
        editorPanelLayout.setVerticalGroup(
            editorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 469, Short.MAX_VALUE)
        );

        jPanel2.add(editorPanel);

        jPanel5.setBackground(new java.awt.Color(114, 164, 210));
        jPanel5.setMaximumSize(new java.awt.Dimension(150, 32767));
        jPanel5.setMinimumSize(new java.awt.Dimension(150, 100));
        jPanel5.setPreferredSize(new java.awt.Dimension(150, 227));

        moveUpButton.setText("Move Up");
        moveUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUpButtonActionPerformed(evt);
            }
        });

        moveDownButton.setText("Move Down");
        moveDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDownButtonActionPerformed(evt);
            }
        });

        addLevelButton.setText("Add Level");
        addLevelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLevelButtonActionPerformed(evt);
            }
        });

        removeLevelButton.setText("Remove Level");
        removeLevelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeLevelButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Level Organizer");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(moveDownButton)
                            .addComponent(moveUpButton)
                            .addComponent(addLevelButton)
                            .addComponent(removeLevelButton))
                        .addContainerGap(39, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(levelOrganizer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(38, 38, 38)
                .addComponent(levelOrganizer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(moveUpButton)
                .addGap(31, 31, 31)
                .addComponent(moveDownButton)
                .addGap(42, 42, 42)
                .addComponent(addLevelButton)
                .addGap(32, 32, 32)
                .addComponent(removeLevelButton)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel5);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        fileDropdown.setText("File");

        NewFile.setText("New File");
        NewFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewFileActionPerformed(evt);
            }
        });
        fileDropdown.add(NewFile);

        OpenFile.setText("OpenFile");
        OpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenFileActionPerformed(evt);
            }
        });
        fileDropdown.add(OpenFile);

        OpenGame.setText("OpenGame");
        OpenGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenGameActionPerformed(evt);
            }
        });
        fileDropdown.add(OpenGame);

        SaveFile.setText("Save");
        SaveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveFileActionPerformed(evt);
            }
        });
        fileDropdown.add(SaveFile);

        SaveFileAs.setText("SaveAs");
        SaveFileAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveFileAsActionPerformed(evt);
            }
        });
        fileDropdown.add(SaveFileAs);

        Exit.setText("Exit");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });
        fileDropdown.add(Exit);

        jMenuBar1.add(fileDropdown);

        buildDropdown.setText("Build");

        buildGame.setText("Build");
        buildGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildGameActionPerformed(evt);
            }
        });
        buildDropdown.add(buildGame);

        buildRun.setText("Build and Run");
        buildRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildRunActionPerformed(evt);
            }
        });
        buildDropdown.add(buildRun);

        levelOrganizerRadioButton.setSelected(true);
        levelOrganizerRadioButton.setText("Level Organizer");
        levelOrganizerRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelOrganizerRadioButtonActionPerformed(evt);
            }
        });
        buildDropdown.add(levelOrganizerRadioButton);

        jMenuBar1.add(buildDropdown);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>                        

    private void blockButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // user presses square button
        currentImage = ImageUtility.getBlockImage();
        selectedObject = 1;     //to represent square
    }                                           

    private void boulderButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // user presses triangle button
        currentImage = ImageUtility.getBoulderImage();
        selectedObject = 2;     //to represent triangle
    }                                             

    private void woodenBoxButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // user presses circle button
        currentImage = ImageUtility.getWoodenBoxImage();
        selectedObject = 3;     //to represent circle
    }                                               

    private void editorPanelMouseClicked(java.awt.event.MouseEvent evt) {                                         
        // user clicks inside editorPanel
        Point p = snapToGrid(evt.getX(), evt.getY());
        gc.canvasAction(selectedObject, p);
        repaint();
    }                                        

    private void toolbarSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // toolbar save button
        gc.saveLevelAs();
    }                                                 

    private void toolbarOpenButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // toolbar open button
        try{
            gc.openLevel();
        }catch(ClassCastException ex){
            System.err.println("incompatable filetype selected");
            JOptionPane.showMessageDialog(this, "Incompatible filetype was selected");
        }
        repaint();
    }                                                 

    private void startPointButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // user selects the startPoint tool
        currentImage = ImageUtility.getStartPointImage();
        selectedObject = 4;     //to represent start point
    }                                                

    private void endPointButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // user selects the endPoint tool
        currentImage = ImageUtility.getEndPointImage();
        selectedObject = 5;     //to represent end point
    }                                              

    private void toolbarNewButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // toolbar new level button pressed
        // might want to prompt a message to ask them if they want to save any unfinished work or else data will be lost
        gc.newLevel(gridSpacing, gridSpacing);      // create new level
        repaint();
    }                                                

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // toolbar delete button
        currentImage = ImageUtility.getDeleteImage();
        selectedObject = 9;     //to represent delete 
    }                                            

    private void addLevelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // Game organizer add level button
        gc.addLevelToGame();
        updateLevelOrganizer(gc.getLevelOrder());
    }                                              

    private void removeLevelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // levelOrganizer remove level button
        if(levelOrganizer.getSelectedIndex() > 0){
            gc.removeLevelFromGame(levelOrganizer.getSelectedIndex());
        }
    }                                                 

    private void moveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // levelOrganizer move up button
        int pos = levelOrganizer.getSelectedIndex();
        if(pos > 0){            // only if this isnt the first element in the list
            gc.moveLevel(pos, pos - 1);
            levelOrganizer.select(pos - 1);         // sets the selection to the items new position
        }
    }                                            

    private void moveDownButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // levelOrganizer move down button
        int pos = levelOrganizer.getSelectedIndex();
        if(pos >= 0 && pos < (levelOrganizer.getItemCount()) - 1){        // only if this isnt the last element in the list
            gc.moveLevel(pos, pos + 1);
            levelOrganizer.select(pos + 1);         // sets the selection to the items new position
        }
    }                                              

    private void levelOrganizerRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                          
        // levelOrganizer checkbox in build menu clicked
        if(levelOrganizerRadioButton.isSelected()){
            showLevelOrganizer();
        }else{
            hideLevelOrganizer();
        }
    }                                                         

    private void toolbarUndoButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // toolbar undo button pressed
        gc.undo();
        repaint();
    }                                                 

    private void toolbarRedoButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // toolbar redo button pressed
        gc.doCom();
        repaint();
    }                                                 

    private void buildGameActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // menu bar build pressed
        int result = gc.buildGame();
        if(result == 0){
            JOptionPane.showMessageDialog(this, "Build Successful");
        }else if(result == 1){
            JOptionPane.showMessageDialog(this, "Failed to build game");
        }else if(result == 2){
            JOptionPane.showMessageDialog(this, "Failed to build game\nGame contains no levels");
        }
    }                                         

    private void NewFileActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // toolbar new file button pressed
        gc.newLevel(gridSpacing, gridSpacing);      // create new level
        repaint();
    }                                       

    private void OpenFileActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // toolbar open file button pressed
        try{
            gc.openLevel();
        }catch(ClassCastException ex){
            System.err.println("incompatable filetype selected");
            JOptionPane.showMessageDialog(this, "Incompatible filetype was selected");
        }
        repaint();
    }                                        

    private void SaveFileActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // file menu save button, NOT SaveAS
        gc.saveLevel();
    }                                        

    private void SaveFileAsActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // file menu save button
        gc.saveLevelAs();
    }                                          

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {                                     
        // file menu exit button pressed
        quitEditor();
    }                                    

    private void editorPanelMouseEntered(java.awt.event.MouseEvent evt) {                                         
        // mouse entered level panel
        updateCursor(true);
    }                                        

    private void editorPanelMouseExited(java.awt.event.MouseEvent evt) {                                        
        // mouse exited level panel
        updateCursor(false);
    }                                       

    private void buildRunActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // build menu build and run item
        int result = gc.buildGame();
        if(result == 0){
            JOptionPane.showMessageDialog(this, "Build Successful");
            BoxedInRunner br = new BoxedInRunner();
        }else if(result == 1){
            JOptionPane.showMessageDialog(this, "Failed to build game");
        }else if(result == 2){
            JOptionPane.showMessageDialog(this, "Failed to build game\nGame contains no levels");
        }
    }                                        

    private void waterButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // user presses water button
        currentImage = ImageUtility.getWaterImage();
        selectedObject = 6;     //to represent water
    }                                           

    private void OpenGameActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // file menu open game button
        gc.openGame();
    }                                        

    private void editorPanelComponentResized(java.awt.event.ComponentEvent evt) {                                             
        // editor panel is resized
        refreshVariables();
        repaint();
        
    }                                            

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
      
        
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
    
    }                                        

    private void teleportButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // teleport pad button pressed
        currentImage = ImageUtility.getPadImage();
        selectedObject = 7;
    }                                              

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify                     
    private javax.swing.JMenuItem Exit;
    private javax.swing.JMenuItem NewFile;
    private javax.swing.JMenuItem OpenFile;
    private javax.swing.JMenuItem OpenGame;
    private javax.swing.JMenuItem SaveFile;
    private javax.swing.JMenuItem SaveFileAs;
    private javax.swing.JButton addLevelButton;
    private javax.swing.JButton blockButton;
    private javax.swing.JButton boulderButton;
    private javax.swing.JMenu buildDropdown;
    private javax.swing.JMenuItem buildGame;
    private javax.swing.JMenuItem buildRun;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel editorPanel;
    private javax.swing.JButton endPointButton;
    private javax.swing.JMenu fileDropdown;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private java.awt.List levelOrganizer;
    private javax.swing.JRadioButtonMenuItem levelOrganizerRadioButton;
    private javax.swing.JButton moveDownButton;
    private javax.swing.JButton moveUpButton;
    private javax.swing.JButton removeLevelButton;
    private javax.swing.JButton startPointButton;
    private javax.swing.JButton teleportButton;
    private javax.swing.JTextField timeLimitBox;
    private javax.swing.JButton toolbarNewButton;
    private javax.swing.JButton toolbarOpenButton;
    private javax.swing.JButton toolbarRedoButton;
    private javax.swing.JButton toolbarSaveButton;
    private javax.swing.JButton toolbarUndoButton;
    private javax.swing.JButton waterButton;
    private javax.swing.JButton woodenBoxButton;
    // End of variables declaration                   
}
