package Item;

import Entity.Entity;
import main.GamePanel;

public class BouclierBleu extends Entity {

    public BouclierBleu(GamePanel gamePanel) {
        super(gamePanel);
        name = "Bouclier Bleu";
        type = type_shield;
        bas1 = setup("/Object/shield_blue", gamePanel.getTileSize(), gamePanel.getTileSize());
        defenceValue = 2;
        itemDescription = "[" + name + "]/nBouclier bleu , un peu pourri";
    }
}
