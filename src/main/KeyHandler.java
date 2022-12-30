package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 *  KeyHandler est la classe qui gère les entrées clavier de l'utilisateur
 *  Elle implémente l'interface KeyListener afin de recevoir les évènements clavier
 */
public class KeyHandler implements KeyListener {

    /**
     *  Ces variables booléennes vont nous servir à savoir quelles touches sont enfoncées
     */
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    //Debug
    boolean checkDrawTime = false;// On stocke une référence vers GamePanel pour pouvoir agir sur l'état du jeu
    public GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel){

        this.gamePanel = gamePanel;
    }


    @Override
    public void keyTyped(KeyEvent e) {
// doit être implémentée
    }

    //Méthode appelée lorsqu'une touche est pressée
    @Override
    public void keyPressed(KeyEvent e) {
        //Récupère le code de la touche pressée
        int key = e.getKeyCode();

        //Si le jeu se trouve dans l'état "titleState" (écran titre)
        if(gamePanel.gameState == gamePanel.titleState) {
            //Appelle la méthode titleState en lui passant en paramètre le code de la touche pressée
            titleState(key);
        }
        //Si le jeu se trouve dans l'état "EtatJeu" (joueur en jeu)
        else if (gamePanel.gameState == gamePanel.EtatJeu) {
            //Appelle la méthode EtatJeu en lui passant en paramètre le code de la touche pressée
            EtatJeu(key);
        }
        //Si le jeu se trouve dans l'état "EtatPause" (jeu en pause)
        else if (gamePanel.gameState == gamePanel.EtatPause){
            //Appelle la méthode EtatPause en lui passant en paramètre le code de la touche pressée
            EtatPause(key);
        }
        //Si le jeu se trouve dans l'état "EtatDialogue" (boîte de dialogue)
        else if(gamePanel.gameState == gamePanel.EtatDialogue){
            //Appelle la méthode EtatDialogue en lui passant en paramètre le code de la touche pressée
            EtatDialogue(key);
        }
        //Si le jeu se trouve dans l'état "EtatJoueur" (écran de sélection de personnage)
        else if(gamePanel.gameState == gamePanel.EtatJoueur){
            //Appelle la méthode EtatJoueur en lui passant en paramètre le code de la touche pressée
            EtatJoueur(key);
        }
    }

    public void titleState(int key){
        if(gamePanel.ui.titleScreenState == 0){
            if (key == KeyEvent.VK_W) {
                gamePanel.ui.commandNum--;
                if(gamePanel.ui.commandNum<0){
                    gamePanel.ui.commandNum = 2;
                }
            }
            if (key == KeyEvent.VK_S) {
                gamePanel.ui.commandNum++;
                if(gamePanel.ui.commandNum>2){
                    gamePanel.ui.commandNum = 0;
                }
            }
            if (key == KeyEvent.VK_ENTER) {
                switch (gamePanel.ui.commandNum){
                    case 0:
                        gamePanel.ui.titleScreenState = 1;
                        break;
                    case 1:
                        //add later
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }
        else if(gamePanel.ui.titleScreenState == 1){
            if (key == KeyEvent.VK_W) {
                gamePanel.ui.commandNum--;
                if(gamePanel.ui.commandNum<0){
                    gamePanel.ui.commandNum = 3;
                }
            }
            if (key == KeyEvent.VK_S) {
                gamePanel.ui.commandNum++;
                if(gamePanel.ui.commandNum>3){
                    gamePanel.ui.commandNum = 0;
                }
            }
            if (key == KeyEvent.VK_ENTER) {
                switch (gamePanel.ui.commandNum) {
                    case 0 -> {
                        System.out.println("Joue comme un guerrier !");
                        gamePanel.gameState = gamePanel.EtatJeu;
                        //gamePanel.playMusic(0);
                    }
                    case 1 -> {
                        System.out.println("Joue comme un mage ! ");
                        gamePanel.gameState = gamePanel.EtatJeu;
                        gamePanel.playMusic(0);
                    }
                    case 2 -> {
                        System.out.println("Joue comme un sorcier ! ");
                        gamePanel.gameState = gamePanel.EtatJeu;
                        gamePanel.playMusic(0);
                    }
                    case 3 -> gamePanel.ui.titleScreenState = 0;
                }
            }
        }
    }

    public void EtatJeu(int key){
        if(key == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(key == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(key == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(key == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(key == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(key == KeyEvent.VK_SPACE) {
            gamePanel.gameState = gamePanel.EtatJoueur;
        }
        if(key == KeyEvent.VK_P) {
            gamePanel.gameState = gamePanel.EtatPause;
        }

        //Debug
        if(key == KeyEvent.VK_T) {
            if(!checkDrawTime){
                checkDrawTime = true;
            }
            else if(checkDrawTime){
                checkDrawTime = false;
            }
        }
    }

    public void EtatJoueur(int key){
        if(key == KeyEvent.VK_SPACE) {
            gamePanel.gameState = gamePanel.EtatJeu;
        }
        if(key == KeyEvent.VK_W && gamePanel.ui.slotRow != 0){
                gamePanel.ui.slotRow--;
                gamePanel.playSFX(8);
        }
        if(key == KeyEvent.VK_A && gamePanel.ui.slotCol != 0){
            gamePanel.ui.slotCol--;
            gamePanel.playSFX(8);
        }
        if(key == KeyEvent.VK_S && gamePanel.ui.slotRow != 3){
            gamePanel.ui.slotRow++;
            gamePanel.playSFX(8);
        }
        if(key == KeyEvent.VK_D && gamePanel.ui.slotCol != 4){
            gamePanel.ui.slotCol++;
            gamePanel.playSFX(8);
        }
        if(key == KeyEvent.VK_ENTER){
            gamePanel.Joueur.selectiondelObjet();
        }
    }

    public void EtatPause(int key){
        if(key == KeyEvent.VK_P){
            gamePanel.gameState = gamePanel.EtatJeu;
        }

    }

    public void EtatDialogue(int key){
        if(key == KeyEvent.VK_ENTER){
            gamePanel.gameState = gamePanel.EtatJeu;
        }
    }
    //Méthode appelée lorsqu'une touche est relâchée
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(key == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(key == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(key == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
