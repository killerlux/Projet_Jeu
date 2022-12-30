package Item;

import Entity.Entity;
import main.GamePanel;

public class Cle extends Entity {

    public Cle(GamePanel gamePanel) {
        super(gamePanel);
        name = "Key";
        bas1 = setup("/Object/key", gamePanel.getTileSize(), gamePanel.getTileSize());
        collision = true;
    }

}
