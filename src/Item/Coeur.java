package Item;

import Entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Coeur extends Entity {

    public Coeur(GamePanel gamePanel) {
        super(gamePanel);
        name = "Coeur";
        image = setup("/Object/heart_full", gamePanel.getTileSize(), gamePanel.getTileSize());
        image2 = setup("/Object/heart_half", gamePanel.getTileSize(), gamePanel.getTileSize());
        image3 = setup("/Object/heart_blank", gamePanel.getTileSize(), gamePanel.getTileSize());
    }
}
