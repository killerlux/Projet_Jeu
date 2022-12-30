package Item;

import Entity.Entity;
import main.GamePanel;

public class BouclierBois extends Entity {

    public BouclierBois(GamePanel gamePanel) {
        super(gamePanel);
        name = "Bouclier en Bois";
        type = type_shield;
        bas1 = setup("/Object/shield_wood", gamePanel.getTileSize(), gamePanel.getTileSize());
        defenceValue = 1;
        itemDescription = "[" + name + "]/n Ancien bouclier en bois.";
    }
}
