package Entity;

import main.GamePanel;

import java.util.Random;

public class sage extends Entity{

    public sage(GamePanel gamePanel){
        super(gamePanel);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    public void setDialogue(){
        dialogues[0] = "Bonjour jeune homme";
        dialogues[1] = "Alors vous cherchez le tresor perdu?";
        dialogues[2] = "Moi aussi je l'ai longtemps cherché,/n" +
                "mais maintenant je suis trop vieux pour cela";
        dialogues[3] = "J'espère que vous me donnez une partie/n" +
                "de vos gains si vous y arrivez hahah.";
    }

    @Override
    public void parler(){
        if(dialogues[IndexDuDialogue] == null){
            IndexDuDialogue = 3;
        }
        super.parler();
    }

    public void getImage(){
        haut1 = setup("/NPC/_up_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        haut2 = setup("/NPC/_up_2", gamePanel.getTileSize(), gamePanel.getTileSize());
        bas1 = setup("/NPC/_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        bas2 = setup("/NPC/_down_2", gamePanel.getTileSize(), gamePanel.getTileSize());
        gauche1 = setup("/NPC/_left_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        gauche2 = setup("/NPC/_left_2", gamePanel.getTileSize(), gamePanel.getTileSize());
        droit1 = setup("/NPC/_right_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        droit2 = setup("/NPC/_right_2", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    //paramétrage du comprotement du pnj
    @Override
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

}
