package monster;

import Entity.Entity;
import main.GamePanel;

import java.util.Random;


public class MON_GreenSlime extends Entity {

    // Constructeur de la classe MON_GreenSlime
    public MON_GreenSlime(GamePanel gamePanel) {
        // Appel au constructeur de la classe mère (Entity)
        super(gamePanel);

        // Définition des attributs de l'objet MON_GreenSlime
        name = "Slime vert";
        type = type_monster;
        speed = 1;
        PdvMax = 5;
        Pdv = PdvMax;
        attack = 5;
        defence = 0;
        exp = 2;

        // Définition de la hitbox de collision de l'objet MON_GreenSlime
        hitboxCollision.x = 3;
        hitboxCollision.y = 18;
        hitboxCollision.width = 42;
        hitboxCollision.height = 30;
        hitboxCollisionDefaultX = hitboxCollision.x;
        hitboxCollisionDefaultY = hitboxCollision.y;

        // Chargement des images de l'objet MON_GreenSlime
        getImage();
    }

    // Méthode pour charger les images de l'objet MON_GreenSlime
    public void getImage(){
        // Chemin d'accès aux images du monstre
        String monster1 = "/Monster/greenslime_down_1";
        String monster2 = "/Monster/greenslime_down_2";

        // Chargement des images du monstre dans les variables d'instance
        haut1 = setup(monster1, gamePanel.getTileSize(), gamePanel.getTileSize());
        haut2 = setup(monster2, gamePanel.getTileSize(), gamePanel.getTileSize());
        bas1 = setup(monster1, gamePanel.getTileSize(), gamePanel.getTileSize());
        bas2 = setup(monster2, gamePanel.getTileSize(), gamePanel.getTileSize());
        gauche1 = setup(monster1, gamePanel.getTileSize(), gamePanel.getTileSize());
        gauche2 = setup(monster2, gamePanel.getTileSize(), gamePanel.getTileSize());
        droit1 = setup(monster1, gamePanel.getTileSize(), gamePanel.getTileSize());
        droit2 = setup(monster2, gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    // Méthode pour définir l'action de l'objet MON_GreenSlime
    public void SetterAction(){
        CompteurAction++;

        if(CompteurAction == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i <= 25){
                direction = "up";
            }
            if(i > 25 && i <= 50){
                direction = "down";
            }
            if(i > 50 && i <= 75){
                direction = "left";
            }
            if(i > 75 && i <= 100){
                direction = "right";
            }

            CompteurAction = 0;
        }
    }

    @Override
    public void damageReaction(){

        CompteurAction = 0;
        // le monstre bouge losrqu'il recoit des degats

        direction = gamePanel.Joueur.direction;
    }
}
