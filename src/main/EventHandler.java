package main;

public class EventHandler {

    GamePanel gamePanel;
    EventRect[][] eventRect;
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        //Setting small event trigger in the middle of tiles, Making it possible to trigger events where necessary.
        eventRect = new EventRect[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];

        int col = 0;
        int row = 0;
        while(col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gamePanel.getMaxWorldCol()){
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent(){
        //Check if Joueur is more than one tile away from last event.
        int xDistance = Math.abs(gamePanel.Joueur.mondeAxeAbscisse - previousEventX);
        int yDistance = Math.abs(gamePanel.Joueur.mondeAxeOrdonnee - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gamePanel.getTileSize()){
            canTouchEvent = true;
        }

        if(canTouchEvent){
            if(hit(27, 16, "right")){squirrelAttack(27, 16, gamePanel.EtatDialogue);}
            if(hit(23, 12, "up")){healingPool(23, 12, gamePanel.EtatDialogue);}
        }
    }

    public boolean hit(int col, int row, String reqDirection){
        boolean hit = false;

        //getting Joueur position
        gamePanel.Joueur.hitboxCollision.x = gamePanel.Joueur.mondeAxeAbscisse + gamePanel.Joueur.hitboxCollision.x;
        gamePanel.Joueur.hitboxCollision.y = gamePanel.Joueur.mondeAxeOrdonnee + gamePanel.Joueur.hitboxCollision.y;

        //getting event trigger position
        eventRect[col][row].x = col* gamePanel.getTileSize() + eventRect[col][row].x;
        eventRect[col][row].y = row* gamePanel.getTileSize() + eventRect[col][row].y;

        //checking for collision, event only happens if eventDone returns false
        if(gamePanel.Joueur.hitboxCollision.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone){
            if(gamePanel.Joueur.direction.contentEquals(reqDirection) ||
            reqDirection.contentEquals("any")){
                hit = true;

                //record Joueur position after hit
                previousEventX = gamePanel.Joueur.mondeAxeAbscisse;
                previousEventY = gamePanel.Joueur.mondeAxeOrdonnee;
            }
        }

        //after checking for collision, reset x and y on eventRect and hitboxCollision
        gamePanel.Joueur.hitboxCollision.x = gamePanel.Joueur.hitboxCollisionDefaultX;
        gamePanel.Joueur.hitboxCollision.y = gamePanel.Joueur.hitboxCollisionDefaultY;
        eventRect[col][row].x =  eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y =  eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void squirrelAttack(int col, int row, int gameState){
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You got bitten by an angry squirrel./nHe ran away before you could get your revenge.";
        gamePanel.Joueur.Pdv -= 1;
        //eventRect[col][row].eventDone = true;
        canTouchEvent = false;
        gamePanel.playSFX(5);
    }

    public void healingPool(int col, int row,int gameState){

        if(gamePanel.keyHandler.enterPressed){
            gamePanel.gameState = gameState;
            gamePanel.ui.currentDialogue = "Pdv was restored. You feel refreshed.";
            gamePanel.Joueur.Pdv = gamePanel.Joueur.PdvMax;
            gamePanel.Joueur.AnnulationAttaque = true;
            gamePanel.playSFX(2);

            //respawn monsters
            gamePanel.SetterRessources.SetterMob();
        }
    }

    public void teleport(int col, int row,int gameState){
        gamePanel.gameState = gameState;
        gamePanel.Joueur.mondeAxeAbscisse = gamePanel.getTileSize()*37; //set to specific tile
        gamePanel.Joueur.mondeAxeOrdonnee = gamePanel.getTileSize()*10; //set to specific tile
    }
}
