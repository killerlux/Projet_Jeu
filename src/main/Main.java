package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        //Création de la fenêtre JFrame contenant le panneau de jeu
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Action à effectuer lorsque la fenêtre est fermée
        window.setResizable(false); //Désactivation de la possibilité de redimensionner la fenêtre
        window.setTitle("Aventure d'exploration"); //Titre de la fenêtre

        //Configuration du panneau de jeu et de la mise en place du jeu, démarrage de l'objet de thread de jeu et affichage de tout cela sur l'écran
        GamePanel gamePanel = new GamePanel(); //Création d'un objet GamePanel
        window.add(gamePanel); //Ajout de l'objet GamePanel à la fenêtre JFrame
        window.pack(); //Configuration de la fenêtre JFrame
        window.setLocationRelativeTo(null); //Centrage de la fenêtre sur l'écran
        window.setVisible(true); //Affichage de la fenêtre
        gamePanel.gameSetup(); //Configuration du jeu
        gamePanel.startGameThread(); //Démarrage de l'objet de thread de jeu
    }
}
