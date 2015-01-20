/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInEditor;

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author azh5442
 */
public final class ImageUtility {
    private static Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static Image blockImage;
    private static Image boulderImage;
    private static Image woodenBoxImage;
    private static Image deleteImage;
    private static Image startPointImage;
    private static Image endPointImage;
    private static Image backgroundImage;
    private static Image playerImage;
    private static Image waterImage;
    private static Image boxedinImage;
    private static Image padImage;
    
    public ImageUtility(){
        initImages();
    }
    
    private static void initImages(){
        blockImage = toolkit.getImage("block.png");
        boulderImage = toolkit.getImage("boulder.png");
        woodenBoxImage = toolkit.getImage("woodenBox.png");
        deleteImage = toolkit.getImage("remove.png");
        startPointImage = toolkit.getImage("start.png");
        endPointImage = toolkit.getImage("end.png");
        backgroundImage = toolkit.getImage("dirt.png");
        playerImage = toolkit.getImage("cage.png");
        waterImage = toolkit.getImage("water.png");
        boxedinImage = toolkit.getImage("boxedin.png");
        padImage = toolkit.getImage("telepad.png");
    }
    
    public static void scaleContent(int gridSpacing, int numOfBlocks) {
        initImages();       // needed because images would lose resolution with resizing
        blockImage = blockImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        boulderImage = boulderImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        woodenBoxImage = woodenBoxImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        deleteImage = deleteImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        startPointImage = startPointImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        endPointImage = endPointImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        backgroundImage = backgroundImage.getScaledInstance(20*gridSpacing, 20*gridSpacing, Image.SCALE_DEFAULT);
        playerImage = playerImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        waterImage = waterImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        padImage = padImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
    }
    
    public static Image getBlockImage(){
        return blockImage;
    }
    
    public static Image getBoulderImage(){
        return boulderImage;
    }
    
    public static Image getWoodenBoxImage(){
        return woodenBoxImage;
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
    
    public static Image getWaterImage(){
        return waterImage;
    }
    
    public static Image getBoxedinImage(){
        return boxedinImage;
    }

    public static Image getPadImage() {
        return padImage;
    }
}
