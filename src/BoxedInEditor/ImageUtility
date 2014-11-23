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
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static Image boxImage;
    private static Image boulderImage;
    private static Image triangleImage;
    private static Image deleteImage;
    private static Image startPointImage;
    private static Image endPointImage;

    public ImageUtility(){
        initImages();
    }
    
    
    private void initImages(){
        boxImage = toolkit.getImage("block.png");
        boulderImage = toolkit.getImage("boulder.png");
        triangleImage = toolkit.getImage("cage.jpg");
        deleteImage = toolkit.getImage("remove.png");
        startPointImage = toolkit.getImage("start.png");
        endPointImage = toolkit.getImage("end.png");
    }
    
    public static void scaleImages(int gridSpacing) {
        boxImage = boxImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        boulderImage = boulderImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        triangleImage = triangleImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        deleteImage = deleteImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        startPointImage = startPointImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
        endPointImage = endPointImage.getScaledInstance(gridSpacing, gridSpacing, Image.SCALE_DEFAULT);
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
}
