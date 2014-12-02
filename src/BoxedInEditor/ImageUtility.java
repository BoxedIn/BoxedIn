/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author azh5442
 */
public final class ImageUtility {
    private static Graphics levelGraphics;
    private static Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static Image boxImage;
    private static Image boulderImage;
    private static Image triangleImage;
    private static Image deleteImage;
    private static Image startPointImage;
    private static Image endPointImage;
    private static Image backgroundImage;
    private static Image playerImage;

    public ImageUtility(){
        initImages();
    }
    
    private static void initImages(){
        boxImage = toolkit.getImage("block.png");
        boulderImage = toolkit.getImage("boulder.png");
        triangleImage = toolkit.getImage("cage.jpg");
        deleteImage = toolkit.getImage("remove.png");
        startPointImage = toolkit.getImage("start.png");
        endPointImage = toolkit.getImage("end.png");
        backgroundImage = toolkit.getImage("dirt.png");
        playerImage = toolkit.getImage("cage.jpg");
    }
    
    public static void scaleContent(Graphics g, int gridSpacing) {
        levelGraphics = g;
        initImages();
        boxImage = boxImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        boulderImage = boulderImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        triangleImage = triangleImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        deleteImage = deleteImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        startPointImage = startPointImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        endPointImage = endPointImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        backgroundImage = backgroundImage.getScaledInstance(20*gridSpacing, 20*gridSpacing, Image.SCALE_DEFAULT);
        playerImage = playerImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
    }
    
    public static void setGraphics(Graphics g){
        levelGraphics = g;
    }
    
    public static Graphics getGraphics() throws UninitializedGraphicsException{
        if(levelGraphics == null){
            throw new UninitializedGraphicsException("The Graphics object you are tying to use has not been initialized");
        }
        return levelGraphics;
    }
    
    public static Image getBoxImage(){
        return boxImage;
    }
    
    public static Image getBoulderImage(){
        return boulderImage;
    }
    
    public static Image getTriangleImage(){
        return triangleImage;
    }
    
    public static Image getDeleteImage(){
        return deleteImage;
    }
    
    public static Image getStartPointImage(){
        return startPointImage;
    }
    
    public static Image getEndPointImage(){
        return endPointImage;
    }
    
    public static Image getBackgroundImage(){
        return backgroundImage;
    }
    
    public static Image getPlayerImage(){
        return playerImage;
    }
}
