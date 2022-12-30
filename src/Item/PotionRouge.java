package Item;

import Entity.Entity;
import main.GamePanel;

public class PotionRouge extends Entity {

    int healingValue = 5;

    public PotionRouge(GamePanel gamePanel) {
        super(gamePanel);
        name = "Red Potion";
        type = type_consumable;
        bas1 = setup("/Object/potion_red", gamePanel.getTileSize(), gamePanel.getTileSize());
        itemDescription = "[Red Potion]/nReplenishes HP by" + healingValue + ".";
    }

    @Override
    public void use(Entity entity){
        gamePanel.gameState = gamePanel.EtatDialogue;
        gamePanel.ui.currentDialogue = "You drank the " + name + "!/n" +
        "You feel refreshed";

        entity.Pdv += healingValue;
        if(gamePanel.Joueur.Pdv > gamePanel.Joueur.PdvMax){
            gamePanel.Joueur.Pdv = gamePanel.Joueur.PdvMax;
        }
        gamePanel.playSFX(2);
    }
}
