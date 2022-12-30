package Item;

import Entity.Entity;
import main.GamePanel;

public class Hache extends Entity {

    public Hache(GamePanel gamePanel) {
        super(gamePanel);
        name = "Hache en bois";
        type = type_axe;
        bas1 = setup("/Object/axe", gamePanel.getTileSize(), gamePanel.getTileSize());
        attackValue = 2;
        HitboxDeLattaque.width = 30;
        HitboxDeLattaque.height = 30;
        itemDescription = "[" + name + "]/n Petite hache inoffensive";
    }
}
