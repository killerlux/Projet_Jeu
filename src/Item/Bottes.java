package Item;

import Entity.Entity;
import main.GamePanel;

public class Bottes extends Entity {

    public Bottes(GamePanel gamePanel){
        super(gamePanel);
        name = "Bottes";
        bas1 = setup("/Object/boots", gamePanel.getTileSize(), gamePanel.getTileSize());
    }
}
