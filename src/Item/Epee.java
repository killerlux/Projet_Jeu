package Item;

import Entity.Entity;
import main.GamePanel;

public class Epee extends Entity {

    public Epee(GamePanel gamePanel) {
        super(gamePanel);
        name = "Epee";
        type = type_sword;
        bas1 = setup("/Object/sword_normal", gamePanel.getTileSize(), gamePanel.getTileSize());
        attackValue = 1;
        HitboxDeLattaque.width = 36;
        HitboxDeLattaque.height = 36;
        itemDescription = "[" + name + "]/n Une belle petite épée";
    }
}
