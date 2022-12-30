package main;

import Entity.Entity;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity){
        int entityLeftmondeAxeAbscisse = entity.mondeAxeAbscisse + entity.hitboxCollision.x;
        int entityRightmondeAxeAbscisse = entity.mondeAxeAbscisse + entity.hitboxCollision.x + entity.hitboxCollision.width;
        int entityTopmondeAxeOrdonnee = entity.mondeAxeOrdonnee + entity.hitboxCollision.y;
        int entityBottommondeAxeOrdonnee = entity.mondeAxeOrdonnee + entity.hitboxCollision.y + entity.hitboxCollision.height;

        int entityLeftCol = entityLeftmondeAxeAbscisse/gamePanel.getTileSize();
        int entityRightCol = entityRightmondeAxeAbscisse/gamePanel.getTileSize();
        int entityTopRow = entityTopmondeAxeOrdonnee/gamePanel.getTileSize();
        int entityBottomRow = entityBottommondeAxeOrdonnee/gamePanel.getTileSize();

        int tileNum1, tileNum2;

        switch (entity.direction){
            case "up":
               entityTopRow = (entityTopmondeAxeOrdonnee - entity.speed)/gamePanel.getTileSize();
               tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
               tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];

               if(gamePanel.tileManager.tile[tileNum1].collision ||
                       gamePanel.tileManager.tile[tileNum2].collision){
                   entity.collisionOn = true;}
                break;
            case "down":
                entityBottomRow = (entityBottommondeAxeOrdonnee + entity.speed)/gamePanel.getTileSize();
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                if(gamePanel.tileManager.tile[tileNum1].collision ||
                        gamePanel.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;}
                break;
            case "left":
                entityLeftCol = (entityLeftmondeAxeAbscisse - entity.speed)/gamePanel.getTileSize();
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];

                if(gamePanel.tileManager.tile[tileNum1].collision ||
                        gamePanel.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;}
                break;
            case "right":
                entityRightCol = (entityRightmondeAxeAbscisse + entity.speed)/gamePanel.getTileSize();
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                if(gamePanel.tileManager.tile[tileNum1].collision ||
                        gamePanel.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;}
                break;
        }
    }

    public int checkItem(Entity entity, boolean Joueur){
        int index = 999;

        for (int i = 0; i < gamePanel.items.length; i++) {
            if(gamePanel.items[i] != null){

                //get entity's hitboxCollision position
                entity.hitboxCollision.x = entity.mondeAxeAbscisse + entity.hitboxCollision.x;
                entity.hitboxCollision.y = entity.mondeAxeOrdonnee + entity.hitboxCollision.y;
                //get item's hitboxCollision position
                gamePanel.items[i].hitboxCollision.x = gamePanel.items[i].mondeAxeAbscisse + gamePanel.items[i].hitboxCollision.x;
                gamePanel.items[i].hitboxCollision.y = gamePanel.items[i].mondeAxeOrdonnee + gamePanel.items[i].hitboxCollision.y;

                //simulating entity movement and check where it will be, after it moved
                entityCollisionDirection(entity);
                if (entity.hitboxCollision.intersects(gamePanel.items[i].hitboxCollision)) {
                    if(gamePanel.items[i].collision){
                        entity.collisionOn = true;
                    }
                    if(Joueur) {
                        index = i;
                    }
                }
                //resetting positions
                entity.hitboxCollision.y = entity.hitboxCollisionDefaultY;
                entity.hitboxCollision.x = entity.hitboxCollisionDefaultX;
                gamePanel.items[i].hitboxCollision.x = gamePanel.items[i].hitboxCollisionDefaultX;
                gamePanel.items[i].hitboxCollision.y = gamePanel.items[i].hitboxCollisionDefaultY;
            }
        }
        return  index;
    }

    public int checkEntity(Entity entity, Entity[] target){
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if(target[i] != null){

                //get entity's hitboxCollision position
                entity.hitboxCollision.x = entity.mondeAxeAbscisse + entity.hitboxCollision.x;
                entity.hitboxCollision.y = entity.mondeAxeOrdonnee + entity.hitboxCollision.y;
                //get item's hitboxCollision position
                target[i].hitboxCollision.x = target[i].mondeAxeAbscisse + target[i].hitboxCollision.x;
                target[i].hitboxCollision.y = target[i].mondeAxeOrdonnee + target[i].hitboxCollision.y;

                //simulating entity movement and check where it will be, after it moved
                entityCollisionDirection(entity);
                if (entity.hitboxCollision.intersects(target[i].hitboxCollision)) {
                    if(target[i] != entity){
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                //resetting positions
                entity.hitboxCollision.y = entity.hitboxCollisionDefaultY;
                entity.hitboxCollision.x = entity.hitboxCollisionDefaultX;
                target[i].hitboxCollision.x = target[i].hitboxCollisionDefaultX;
                target[i].hitboxCollision.y = target[i].hitboxCollisionDefaultY;
            }
        }
        return  index;
    }

    public boolean checkJoueur(Entity entity){

        boolean contactJoueur = false;

        //get entity's hitboxCollision position
        entity.hitboxCollision.x = entity.mondeAxeAbscisse + entity.hitboxCollision.x;
        entity.hitboxCollision.y = entity.mondeAxeOrdonnee + entity.hitboxCollision.y;
        //get item's hitboxCollision position
        gamePanel.Joueur.hitboxCollision.x = gamePanel.Joueur.mondeAxeAbscisse + gamePanel.Joueur.hitboxCollision.x;
        gamePanel.Joueur.hitboxCollision.y = gamePanel.Joueur.mondeAxeOrdonnee + gamePanel.Joueur.hitboxCollision.y;

        //simulating entity movement and check where it will be, after it moved
        entityCollisionDirection(entity);

        if (entity.hitboxCollision.intersects(gamePanel.Joueur.hitboxCollision)) {
            entity.collisionOn = true;
            contactJoueur = true;
        }

        //resetting positions
        entity.hitboxCollision.y = entity.hitboxCollisionDefaultY;
        entity.hitboxCollision.x = entity.hitboxCollisionDefaultX;
        gamePanel.Joueur.hitboxCollision.x = gamePanel.Joueur.hitboxCollisionDefaultX;
        gamePanel.Joueur.hitboxCollision.y = gamePanel.Joueur.hitboxCollisionDefaultY;

        return contactJoueur;

    }

    private void entityCollisionDirection(Entity entity) {
        switch (entity.direction) {
            case "up" -> entity.hitboxCollision.y -= entity.speed;
            case "down" -> entity.hitboxCollision.y += entity.speed;
            case "left" -> entity.hitboxCollision.x -= entity.speed;
            case "right" -> entity.hitboxCollision.x += entity.speed;
        }
    }
}
