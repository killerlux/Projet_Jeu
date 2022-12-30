package main;

import Entity.sage;
import Item.Cle;
import monster.MON_GreenSlime;

/**
 * Cette classe sert à définir les différents éléments du jeu, comme les objets, les personnages non-joueurs (PNJ) et les monstres.
 */
public class SetterRessources {

    // Panneau de jeu dans lequel se trouvent les éléments
    GamePanel gamePanel;

    public SetterRessources(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Crée et positionne les objets dans le jeu.
     */
    public void SetterObjet() {

        int i = 0;

        gamePanel.items[i] = new Cle(gamePanel);
        gamePanel.items[i].mondeAxeAbscisse = gamePanel.getTileSize()*25;
        gamePanel.items[i].mondeAxeOrdonnee = gamePanel.getTileSize()*23;
        i++;

        gamePanel.items[i] = new Cle(gamePanel);
        gamePanel.items[i].mondeAxeAbscisse = gamePanel.getTileSize()*21;
        gamePanel.items[i].mondeAxeOrdonnee = gamePanel.getTileSize()*19;
        i++;

        gamePanel.items[i] = new Cle(gamePanel);
        gamePanel.items[i].mondeAxeAbscisse = gamePanel.getTileSize()*26;
        gamePanel.items[i].mondeAxeOrdonnee = gamePanel.getTileSize()*21;
    }

    /**
     * Crée et positionne les PNJ dans le jeu.
     */
    public void SetterPNJ() {
        gamePanel.npcs[0] = new sage(gamePanel);
        gamePanel.npcs[0].mondeAxeAbscisse = gamePanel.getTileSize() * 21;
        gamePanel.npcs[0].mondeAxeOrdonnee = gamePanel.getTileSize() * 21;
    }

    /**
     * Crée et positionne les monstres dans le jeu.
     */
    public void SetterMob(){
        int i = 0;

        gamePanel.monsters[i] = new MON_GreenSlime(gamePanel);
        gamePanel.monsters[i].mondeAxeAbscisse = gamePanel.getTileSize()*23;
        gamePanel.monsters[i].mondeAxeOrdonnee = gamePanel.getTileSize()*36;

        i++;

        gamePanel.monsters[i] = new MON_GreenSlime(gamePanel);
        gamePanel.monsters[i].mondeAxeAbscisse = gamePanel.getTileSize()*23;
        gamePanel.monsters[i].mondeAxeOrdonnee = gamePanel.getTileSize()*37;

        i++;

        gamePanel.monsters[i] = new MON_GreenSlime(gamePanel);
        gamePanel.monsters[i].mondeAxeAbscisse = gamePanel.getTileSize()*23;
        gamePanel.monsters[i].mondeAxeOrdonnee = gamePanel.getTileSize()*38;

        i++;

        gamePanel.monsters[i] = new MON_GreenSlime(gamePanel);
        gamePanel.monsters[i].mondeAxeAbscisse = gamePanel.getTileSize()*23;
        gamePanel.monsters[i].mondeAxeOrdonnee = gamePanel.getTileSize()*39;
    }
}
