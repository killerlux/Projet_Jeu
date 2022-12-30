package main;

import Entity.Entity;
import Item.Coeur;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2;
    Font arial_40, arial_60B;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue;
    public int commandNum = 0;
    public int titleScreenState = 0; //0: first screen, 1: second screen
    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_60B = new Font("Arial", Font.BOLD, 60);

        //Create HUD object
        Entity heart = new Coeur(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        //Title State
        if(gamePanel.gameState == gamePanel.titleState){
            drawTitleScreen();
        }

        //Play state
        if(gamePanel.gameState == gamePanel.EtatJeu){
            drawJoueurPdv();
            drawMessage();
        }

        //Pause state
        if(gamePanel.gameState == gamePanel.EtatPause){
            drawPauseScreen();
            drawJoueurPdv();
        }

        //Dialogue state
        if(gamePanel.gameState == gamePanel.EtatDialogue){
            drawDialogueScreen();
            drawJoueurPdv();
        }

        //Character state
        if(gamePanel.gameState == gamePanel.EtatJoueur){
            drawCharacterScreen();
            drawinventaire();
        }
    }

    public void drawJoueurPdv(){
        int x = gamePanel.getTileSize()/5;
        int y = gamePanel.getTileSize()*11;
        int i = 0;

        //Draw max Pdv
        while(i < gamePanel.Joueur.PdvMax/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x+= gamePanel.getTileSize();
        }

        //resetting values
        x = gamePanel.getTileSize()/5;
        y = gamePanel.getTileSize()*11;
        i = 0;

        //Draw current Pdv
        while(i < gamePanel.Joueur.Pdv){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gamePanel.Joueur.Pdv){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x+= gamePanel.getTileSize();
        }

    }

    public void drawMessage(){
        int messageX = gamePanel.getTileSize();
        int messageY = gamePanel.getTileSize()* 4;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));


        for (int i = 0; i < message.size(); i++) {
            if(message.get(i) != null){
                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX+2, messageY +2);

                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) +1; //messageCounter++
                messageCounter.set(i, counter); //set the counter to the array
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen(){
        if(titleScreenState == 0){
            //Title screen color
            g2.setColor(new Color(30, 144, 255));
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            //Title name
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 70F));
            String text = "Blue Boy Adventure";
            int x = getXForCenteredText(text);
            int y = gamePanel.getTileSize()*3;

            //Text shadow
            g2.setColor(Color.BLACK);
            g2.drawString(text, x+3, y+3);

            //Main text color
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //Blue boy image
            x = gamePanel.getScreenWidth()/2 - ((gamePanel.getTileSize()*2)/2);
            y += gamePanel.getTileSize()*2;
            g2.drawImage(gamePanel.Joueur.bas1, x, y, gamePanel.getTileSize()*2, gamePanel.getTileSize()*2, null);

            //Menu
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));

            text = "NOUVELLE PARTIE";
            x = getXForCenteredText(text);
            y += gamePanel.getTileSize()*3.5;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-gamePanel.getTileSize(), y);}


            text = "CHARGER SAUVEGARDE";
            x = getXForCenteredText(text);
            y += gamePanel.getTileSize();
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-gamePanel.getTileSize(), y);}


            text = "QUITTER";
            x = getXForCenteredText(text);
            y += gamePanel.getTileSize();
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-gamePanel.getTileSize(), y);}
        }
        else if (titleScreenState == 1){

            //Character class selection screen
            g2.setColor(new Color(30, 144, 255));
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(25F));

                String text = "SELECT YOUR STARTING CLASS";
            int x = getXForCenteredText(text);
            int y = gamePanel.getTileSize()*3;
            g2.drawString(text, x, y);

            text = "GUERRIER";
            x = getXForCenteredText(text);
            y += gamePanel.getTileSize()*2;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x- gamePanel.getTileSize(), y);
            }

            text = "MAGE";
            x = getXForCenteredText(text);
            y += gamePanel.getTileSize();
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x- gamePanel.getTileSize(), y);
            }

            text = "SORCIER";
            x = getXForCenteredText(text);
            y += gamePanel.getTileSize();
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x- gamePanel.getTileSize(), y);
            }

            text = "RETOUR";
            x = getXForCenteredText(text);
            y += gamePanel.getTileSize()*2;
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawString(">", x- gamePanel.getTileSize(), y);
            }
        }
    }

    public void drawPauseScreen(){
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gamePanel.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        //window
        int x = gamePanel.getTileSize()*2;
        int y = gamePanel.getTileSize()/2;
        int width = gamePanel.getScreenWidth() - (gamePanel.getTileSize()*4);
        int height = gamePanel.getTileSize()*4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20));
        x += gamePanel.getTileSize();
        y += gamePanel.getTileSize();

        for(String line : currentDialogue.split("/n")){
            g2.drawString(line, x, y);
            y+=40;
        }
    }

    public void drawCharacterScreen(){
        //Create a frame
        final int frameX = gamePanel.getTileSize()*10;
        final int frameY = gamePanel.getTileSize();
        final int frameWidth = gamePanel.getTileSize() * 5;
        final int frameHeight = gamePanel.getTileSize() * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //Text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(25F));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.getTileSize();
        final int lineHeight = 40;

        //Attributes
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Pdv", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("agilite", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defence", textX, textY);
        textY += lineHeight;
        g2.drawString("EXP", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Argent", textX, textY);
        textY += lineHeight + 8;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight;
        g2.drawString("Shield", textX, textY);

        //Values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gamePanel.getTileSize();
        String value;

        value = String.valueOf(gamePanel.Joueur.level);
        textX = getXForAlignment(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = gamePanel.Joueur.Pdv + "/" + gamePanel.Joueur.PdvMax;
        textX = getXForAlignment(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.Joueur.strenght);
        textX = getXForAlignment(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.Joueur.agilite);
        textX = getXForAlignment(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.Joueur.attack);
        textX = getXForAlignment(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.Joueur.defence);
        textX = getXForAlignment(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.Joueur.exp);
        textX = getXForAlignment(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.Joueur.niveauSuivantExperience);
        textX = getXForAlignment(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.Joueur.Argent);
        textX = getXForAlignment(value, tailX);
        g2.drawString(value, textX, textY);
        textY += 10;

        g2.drawImage(gamePanel.Joueur.ArmeActuelle.bas1, tailX - gamePanel.getTileSize(), textY, null);
        textY += gamePanel.getTileSize();

        g2.drawImage(gamePanel.Joueur.BouclierActuel.bas1, tailX - gamePanel.getTileSize(), textY - 6, null);
    }

    public void drawinventaire(){
        //Frame
        int frameX = gamePanel.getTileSize();
        int frameY = gamePanel.getTileSize();
        int frameWidth = gamePanel.getTileSize()*6;
        int frameHeight = gamePanel.getTileSize()*5;
        drawSubWindow(frameX, frameY, frameWidth,frameHeight);

        //Slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;


        //Draw Joueur's items
        for (int i = 0; i < gamePanel.Joueur.inventaire.size(); i++) {

            //Equip cursor
            if(gamePanel.Joueur.inventaire.get(i) == gamePanel.Joueur.ArmeActuelle ||
            gamePanel.Joueur.inventaire.get(i) == gamePanel.Joueur.BouclierActuel){
                g2.setColor(Color.orange);
                g2.fillRoundRect(slotX, slotY, 46, 46, 10, 10);
            }

            g2.drawImage(gamePanel.Joueur.inventaire.get(i).bas1, slotX, slotY, null);
            slotX += gamePanel.getTileSize();

            if(i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += gamePanel.getTileSize();
            }
        }

        //Cursor
        int cursorX = slotXstart + (gamePanel.getTileSize() * slotCol);
        int cursorY = slotYstart + (gamePanel.getTileSize() * slotRow);
        int cursorWidth = gamePanel.getTileSize();
        int cursorHeight = gamePanel.getTileSize();

        //Draw cursor
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //Item description frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gamePanel.getTileSize()*4;


        //Draw item text
        int textX = dFrameX + 15;
        int textY = dFrameY + 40;
        g2.setFont(g2.getFont().deriveFont(20F));

        int itemIndex = getItemIndexOnSlot();

        if(itemIndex < gamePanel.Joueur.inventaire.size()){
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            for(String line: gamePanel.Joueur.inventaire.get(itemIndex).itemDescription.split("/n")){
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    public int getItemIndexOnSlot(){
        return slotCol + (slotRow * 5);
    }

    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0,0,0,210); //a = opacity
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    public int getXForCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.screenWidth/2 - length/2;
    }

    public int getXForAlignment(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }
}
