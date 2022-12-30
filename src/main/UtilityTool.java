package main;

import java.awt.*;
import java.awt.image.BufferedImage;
/**UtilityTool est une classe qui contient une méthode permettant de redimensionner une image
mise à l'echelle des images */
public class UtilityTool {




    /**
    La méthode "scaleImage" prend en paramètres l'image à redimensionner, ainsi que
     la largeur et la hauteur souhaitées pour la nouvelle image

     Elle crée une nouvelle image de la taille souhaitée et utilise la classe
     Graphics2D pour dessiner l'image originale dans la nouvelle image redimensionnée
*/
    public BufferedImage scaleImage(BufferedImage original, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0,0, width, height, null);
        g2.dispose();

        return scaledImage; // retourne ensuite la nouvelle image redimensionnée
    }
}
