package Entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Cette classe représente une entité dans le jeu, comme un personnage, un ennemi ou un objet.
 */
public class Entity {

    // Panneau de jeu dans lequel se trouve l'entité
    public GamePanel gamePanel;

    // Dialogues de l'entité
    public String[] dialogues = new String[20];

    // Coordonnées de l'entité dans le monde
    public int mondeAxeAbscisse, mondeAxeOrdonnee;

    // Zone de collision de l'entité
    public Rectangle hitboxCollision = new Rectangle(0, 0, 48, 48);

    // Coordonnées par défaut de la zone de collision
    public int hitboxCollisionDefaultX;
    public int hitboxCollisionDefaultY;

    // Zone d'attaque de l'entité
    public Rectangle HitboxDeLattaque = new Rectangle(0, 0, 0, 0);

    // Images de l'entité selon les différentes directions et actions
    public BufferedImage haut1, haut2, bas1, bas2, gauche1, gauche2, droit1, droit2;
    public BufferedImage attackhaut1, attackhaut2, attackbas1, attackbas2, attackgauche1, attackgauche2,
            attackdroit1, attackdroit2;

    // Compteurs et index
    public int IndexDuDialogue = 0;
    public int invincibleCounter = 0;
    public int CompteurSprite = 0;
    public int CompteurAction = 0;
    public int CompteurDeMorts = 0;
    int hpBarCounter = 0;

    // États de l'entité
    public int numeroDuSprite = 1;
    public String direction = "down"; // direction actuelle de l'entité
    public boolean invincible = false; // indique si l'entité est actuellement invulnérable
    public boolean EtatAttaque = false; // indique si l'entité est en train d'attaquer
    public boolean collisionOn = false; // indique si l'entité est en collision avec un autre objet
    public boolean isAlive = true; // indique si l'entité est vivante
    public boolean isDying = false; // indique si l'entité est en train de mourir
    boolean hpBarOn = false; // indique si la barre de vie de l'entité est affichée

    // Types d'entité
    public int type;
    public final int type_Joueur = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;


    //Objet
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;


    //Caractéristiques personnage
    public int PdvMax;
    public int Pdv;
    public int speed;
    public int level;
    public int attack;
    public int strenght;
    public int agilite;
    public int defence;
    public int exp;
    public int niveauSuivantExperience;
    public int Argent;
    public Entity ArmeActuelle;
    public Entity BouclierActuel;

    //Item attributes
    public int attackValue;
    public int defenceValue;
    public String itemDescription = "";

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void SetterAction() {
    }

    public void damageReaction() {
    }

    public void parler() {
        gamePanel.ui.currentDialogue = dialogues[IndexDuDialogue];
        IndexDuDialogue++;

        switch (gamePanel.Joueur.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    public void use(Entity entity) {
    }

    public void update() {
        SetterAction();

        collisionOn = false;
        gamePanel.checker.checkTile(this);
        gamePanel.checker.checkItem(this, false);
        boolean contactJoueur = gamePanel.checker.checkJoueur(this);
        gamePanel.checker.checkEntity(this, gamePanel.npcs);
        gamePanel.checker.checkEntity(this, gamePanel.monsters);

        if (this.type == type_monster && contactJoueur && !gamePanel.Joueur.invincible) {
            gamePanel.playSFX(6);

            int damage = attack - gamePanel.Joueur.defence;
            if (damage < 0) {
                damage = 0;
            }

            gamePanel.Joueur.Pdv -= damage;
            gamePanel.Joueur.invincible = true;
        }

        if (!collisionOn) {
            switch (direction) {
                case "up" -> mondeAxeOrdonnee -= speed;
                case "down" -> mondeAxeOrdonnee += speed;
                case "left" -> mondeAxeAbscisse -= speed;
                case "right" -> mondeAxeAbscisse += speed;
            }
        }
        int fpsAjustement = 12;
        CompteurSprite++;
        if (CompteurSprite > fpsAjustement) {
            if (numeroDuSprite == 1) {
                numeroDuSprite = 2;
            } else if (numeroDuSprite == 2) {
                numeroDuSprite = 1;
            }
            CompteurSprite = 0;
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 30) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        int AxeAbcisse = mondeAxeAbscisse - gamePanel.Joueur.mondeAxeAbscisse + gamePanel.Joueur.AxeAbcisse;
        int AxeOrdonnee = mondeAxeOrdonnee - gamePanel.Joueur.mondeAxeOrdonnee + gamePanel.Joueur.AxeOrdonnee;

        if (mondeAxeAbscisse + gamePanel.getTileSize() > gamePanel.Joueur.mondeAxeAbscisse - gamePanel.Joueur.AxeAbcisse &&
                mondeAxeAbscisse - gamePanel.getTileSize() < gamePanel.Joueur.mondeAxeAbscisse + gamePanel.Joueur.AxeAbcisse &&
                mondeAxeOrdonnee + gamePanel.getTileSize() > gamePanel.Joueur.mondeAxeOrdonnee - gamePanel.Joueur.AxeOrdonnee &&
                mondeAxeOrdonnee - gamePanel.getTileSize() < gamePanel.Joueur.mondeAxeOrdonnee + gamePanel.Joueur.AxeOrdonnee) {

            BufferedImage image = null;
            switch (direction) {
                case "up" -> {
                    if (numeroDuSprite == 1) {
                        image = haut1;
                    }
                    if (numeroDuSprite == 2) {
                        image = haut2;
                    }
                }
                case "down" -> {
                    if (numeroDuSprite == 1) {
                        image = bas1;
                    }
                    if (numeroDuSprite == 2) {
                        image = bas2;
                    }
                }
                case "left" -> {
                    if (numeroDuSprite == 1) {
                        image = gauche1;
                    }
                    if (numeroDuSprite == 2) {
                        image = gauche2;
                    }
                }
                case "right" -> {
                    if (numeroDuSprite == 1) {
                        image = droit1;
                    }
                    if (numeroDuSprite == 2) {
                        image = droit2;
                    }
                }
            }

            //Barre de vie du monstre
            if (type == 2 && hpBarOn) {

                double oneScale = (double) gamePanel.getTileSize() / PdvMax;
                double hpBarValue = oneScale * Pdv;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(AxeAbcisse - 1, AxeOrdonnee - 4, gamePanel.getTileSize() + 2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(AxeAbcisse, AxeOrdonnee - 3, (int) hpBarValue, 10);

                hpBarCounter++;

                //HP bar disappears after x amount of frames
                if (hpBarCounter > 300) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.5f);

            }

            if (isDying) {
                AnimationMort(g2);
            }

            g2.drawImage(image, AxeAbcisse, AxeOrdonnee, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            changeAlpha(g2, 1f);
        }
    }

    public void AnimationMort(Graphics2D g2) {
        CompteurDeMorts++;
        int i = 5;

        if (CompteurDeMorts <= i) {
            changeAlpha(g2, 0f);
        }
        if (CompteurDeMorts > i && CompteurDeMorts <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (CompteurDeMorts > i * 2 && CompteurDeMorts <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (CompteurDeMorts > i * 3 && CompteurDeMorts <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (CompteurDeMorts > i * 4 && CompteurDeMorts <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (CompteurDeMorts > i * 5 && CompteurDeMorts <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (CompteurDeMorts > i * 6 && CompteurDeMorts <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (CompteurDeMorts > i * 7 && CompteurDeMorts <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (CompteurDeMorts > i * 8) {
            isDying = false;
            isAlive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    //Chargement et changement de l'échelle de l'image du joueur
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;
        try {

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    imagePath + ".png")));
            image = utilityTool.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
