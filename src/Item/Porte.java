package Item;

import Entity.Entity;
import main.GamePanel;

public class Porte extends Entity {

    public Porte(GamePanel gamePanel){
        super(gamePanel);
        name = "Porte";
        bas1 = setup("/Object/door", gamePanel.getTileSize(), gamePanel.getTileSize());
        collision = true;

        hitboxCollision.x = 0;
        hitboxCollision.y = 16;
        hitboxCollision.width = 48;
        hitboxCollision.height = 32;
        hitboxCollisionDefaultX = hitboxCollision.x;
        hitboxCollisionDefaultY = hitboxCollision.y;
    }
}
