package Entity;

import Item.*;
import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Joueur extends Entity{

    KeyHandler keyHandler;
    public final int AxeAbcisse;
    public final int AxeOrdonnee;
    public boolean AnnulationAttaque = false;
    public ArrayList<Entity> inventaire = new ArrayList<Entity>();
    public final int inventaireCapacity = 20;

    public Joueur(GamePanel gamePanel, KeyHandler keyHandler){
        super(gamePanel);
        this.keyHandler = keyHandler;

        AxeAbcisse = gamePanel.getScreenWidth()/2 - (gamePanel.getTileSize()/2);
        AxeOrdonnee = gamePanel.getScreenHeight()/2 - (gamePanel.getTileSize()/2);

        hitboxCollision = new Rectangle(8, 16, 32, 32);
        hitboxCollisionDefaultX = hitboxCollision.x;
        hitboxCollisionDefaultY = hitboxCollision.y;

        setDefaultValues();
        getJoueurImage();
        getJoueurAttackImage();
        setterObjet();
    }

    public void setDefaultValues(){
        //intiilisation de la position du joueur
        mondeAxeAbscisse = gamePanel.getTileSize() * 20;
        mondeAxeOrdonnee = gamePanel.getTileSize() * 20;
        speed = 4;
        direction = "down";

        //Joueur status
        level = 1;
        PdvMax = 6;
        Pdv = PdvMax;//points de vie
        strenght = 1; //plus de force = plus de dégats
        agilite = 1; //plus d'agilité = moisn de degats subis
        exp = 0;
        niveauSuivantExperience = 5;
        Argent = 0;
        ArmeActuelle = new Epee(gamePanel);
        BouclierActuel = new BouclierBois(gamePanel);
        attack = AttaqueDegats(); //total attack value is decided by strength and weapon
        defence = calculateDefence(); //vice versa with dex and shield
    }

    public void setterObjet(){
        inventaire.add(ArmeActuelle);
        inventaire.add(BouclierActuel);
        inventaire.add(new Hache(gamePanel));
        inventaire.add(new BouclierBleu(gamePanel));
        inventaire.add(new PotionRouge(gamePanel));
    }

    public int AttaqueDegats(){
        HitboxDeLattaque = ArmeActuelle.HitboxDeLattaque;
        return attack = strenght * ArmeActuelle.attackValue;
    }

    public int calculateDefence(){

        return defence = agilite * BouclierActuel.defenceValue;
    }

    public void getJoueurImage(){
        haut1 = setup("/Joueur/Walking sprites/boy_up_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        haut2 = setup("/Joueur/Walking sprites/boy_up_2", gamePanel.getTileSize(), gamePanel.getTileSize());
        bas1 = setup("/Joueur/Walking sprites/boy_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        bas2 = setup("/Joueur/Walking sprites/boy_down_2", gamePanel.getTileSize(), gamePanel.getTileSize());
        gauche1 = setup("/Joueur/Walking sprites/boy_left_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        gauche2 = setup("/Joueur/Walking sprites/boy_left_2", gamePanel.getTileSize(), gamePanel.getTileSize());
        droit1 = setup("/Joueur/Walking sprites/boy_right_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        droit2 = setup("/Joueur/Walking sprites/boy_right_2", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    public void getJoueurAttackImage(){

        if(ArmeActuelle.type == type_sword){
            attackhaut1 = setup("/Joueur/Attacking sprites/boy_attack_up_1", gamePanel.getTileSize(), gamePanel.getTileSize()*2);
            attackhaut2 = setup("/Joueur/Attacking sprites/boy_attack_up_2", gamePanel.getTileSize(), gamePanel.getTileSize()*2);
            attackbas1 = setup("/Joueur/Attacking sprites/boy_attack_down_1", gamePanel.getTileSize(), gamePanel.getTileSize()*2);
            attackbas2 = setup("/Joueur/Attacking sprites/boy_attack_down_2", gamePanel.getTileSize(), gamePanel.getTileSize()*2);
            attackgauche1 = setup("/Joueur/Attacking sprites/boy_attack_left_1", gamePanel.getTileSize()*2, gamePanel.getTileSize());
            attackgauche2 = setup("/Joueur/Attacking sprites/boy_attack_left_2", gamePanel.getTileSize()*2, gamePanel.getTileSize());
            attackdroit1 = setup("/Joueur/Attacking sprites/boy_attack_right_1", gamePanel.getTileSize()*2, gamePanel.getTileSize());
            attackdroit2 = setup("/Joueur/Attacking sprites/boy_attack_right_2", gamePanel.getTileSize()*2, gamePanel.getTileSize());
        }
        if(ArmeActuelle.type == type_axe){
            attackhaut1 = setup("/Joueur/Attacking sprites/boy_axe_up_1", gamePanel.getTileSize(), gamePanel.getTileSize()*2);
            attackhaut2 = setup("/Joueur/Attacking sprites/boy_axe_up_2", gamePanel.getTileSize(), gamePanel.getTileSize()*2);
            attackbas1 = setup("/Joueur/Attacking sprites/boy_axe_down_1", gamePanel.getTileSize(), gamePanel.getTileSize()*2);
            attackbas2 = setup("/Joueur/Attacking sprites/boy_axe_down_2", gamePanel.getTileSize(), gamePanel.getTileSize()*2);
            attackgauche1 = setup("/Joueur/Attacking sprites/boy_axe_left_1", gamePanel.getTileSize()*2, gamePanel.getTileSize());
            attackgauche2 = setup("/Joueur/Attacking sprites/boy_axe_left_2", gamePanel.getTileSize()*2, gamePanel.getTileSize());
            attackdroit1 = setup("/Joueur/Attacking sprites/boy_axe_right_1", gamePanel.getTileSize()*2, gamePanel.getTileSize());
            attackdroit2 = setup("/Joueur/Attacking sprites/boy_axe_right_2", gamePanel.getTileSize()*2, gamePanel.getTileSize());
        }


    }

    @Override
    public void update(){

        if(EtatAttaque){
            EtatAttaque();
        }
        //si joueur bouge pas , incrémentation compteur
        else if (keyHandler.upPressed || keyHandler.downPressed ||
                keyHandler.rightPressed || keyHandler.leftPressed || keyHandler.enterPressed){

            // touches pour faire bouger le joueur
            if(keyHandler.upPressed){
                direction = "up";
            }
            else if(keyHandler.downPressed){
                direction = "down";
            }
            else if(keyHandler.leftPressed){
                direction = "left";
            }
            else if(keyHandler.rightPressed){
                direction = "right";
            }

            // test collision
            collisionOn = false;
            gamePanel.checker.checkTile(this);

            //test   collision objet
            int itemIndex = gamePanel.checker.checkItem(this, true);
            Ramassageobjet(itemIndex);

            //test collision pnj
            int npcIndex = gamePanel.checker.checkEntity(this, gamePanel.npcs);
            PNJInteractions(npcIndex);

            //test collision monstre
            int monsterIndex = gamePanel.checker.checkEntity(this, gamePanel.monsters);
            CollisionMonstre(monsterIndex);

            //test evenement
            gamePanel.eventHandler.checkEvent();

            //si collision , le joueur bouge plus
            if(!collisionOn && !keyHandler.enterPressed){
                switch (direction) {
                    case "up" -> mondeAxeOrdonnee -= speed;
                    case "down" -> mondeAxeOrdonnee += speed;
                    case "left" -> mondeAxeAbscisse -= speed;
                    case "right" -> mondeAxeAbscisse += speed;
                }
            }

            if(keyHandler.enterPressed && !AnnulationAttaque){
                gamePanel.playSFX(7);
                EtatAttaque = true;
                CompteurSprite = 0;
            }

            AnnulationAttaque = false;
            gamePanel.keyHandler.enterPressed = false;

            //changement de l'image du joueur tous les x fps
            int fpsAjustement = 12;
            CompteurSprite++;
            if(CompteurSprite > fpsAjustement){
                if(numeroDuSprite == 1){
                    numeroDuSprite = 2;
                } else if (numeroDuSprite == 2){
                    numeroDuSprite = 1;
                }
                CompteurSprite = 0;
            }
        }

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter>30){
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public void EtatAttaque(){
        CompteurSprite++;

        //Montrer l'attaque du sprite numéro 1 pour un nombre de X de pixels 
        if(CompteurSprite <= 5){
            numeroDuSprite = 1;
        }
        //Montrer l'attaque du sprite numéro 2 pour un nombre de X de pixels 
        if(CompteurSprite > 5 && CompteurSprite <= 25){
            numeroDuSprite = 2;


            //sauvegarder le monde mondeX et Y en hitboxCollision
            int currentmondeAxeAbscisse = mondeAxeAbscisse;
            int currentmondeAxeOrdonnee = mondeAxeOrdonnee;
            int hitboxCollisionWidth = hitboxCollision.width;
            int hitboxCollisionHeight = hitboxCollision.height;

            //Ajustement du monde du joueur X et Y pour attackAdjust Joueur's world(X and Y) for the HitboxDeLattaque
            switch (direction) {
                case "up" -> mondeAxeOrdonnee -= HitboxDeLattaque.height;
                case "down" -> mondeAxeOrdonnee += HitboxDeLattaque.height;
                case "left" -> mondeAxeAbscisse -= HitboxDeLattaque.width;
                case "right" -> mondeAxeAbscisse += HitboxDeLattaque.width;
            }

            //HitboxDeLattaque devient hitboxCollision
            hitboxCollision.width = HitboxDeLattaque.width;
            hitboxCollision.height = HitboxDeLattaque.height;

            //teste si y'a collision de monstre avec worldX et Y
            int monsterIndex = gamePanel.checker.checkEntity(this, gamePanel.monsters);
            damageMonster(monsterIndex);

            //Après avoir testé la collision , retour aux valeurs d'origine
            mondeAxeAbscisse = currentmondeAxeAbscisse;
            mondeAxeOrdonnee = currentmondeAxeOrdonnee;
            hitboxCollision.width = hitboxCollisionWidth;
            hitboxCollision.height = hitboxCollisionHeight;
        }
        //reset du sprite de l'a'ttaque
        if(CompteurSprite > 25){
            numeroDuSprite = 1;
            CompteurSprite = 0;
            EtatAttaque = false;
        }
    }

    public void Ramassageobjet(int index){
        if(index != 999) {

            String text = "";

            if(inventaire.size() != inventaireCapacity) {
                inventaire.add(gamePanel.items[index]);
                gamePanel.playSFX(1);
                text = "Un  " + gamePanel.items[index].name + " a été obtenu !";
            }
            else{
                gamePanel.gameState = gamePanel.EtatDialogue;
                gamePanel.ui.currentDialogue = "Inventaire plein ! ";
            }
            gamePanel.ui.addMessage(text);
            gamePanel.items[index] = null;
        }
    }

    public void PNJInteractions(int index) {

        if (gamePanel.keyHandler.enterPressed) {
            if (index != 999) {
                AnnulationAttaque = true;
                gamePanel.gameState = gamePanel.EtatDialogue;
                gamePanel.npcs[index].parler();
            }
        }
    }

    public void CollisionMonstre(int index){
        if(index != 999 && !invincible) {
            gamePanel.playSFX(6);

            int damage = gamePanel.monsters[index].attack - defence;
            if(damage < 0){
                damage = 0;
            }

            Pdv -= damage;
            gamePanel.ui.addMessage(damage + " Dégâts reçus !");

            invincible = true;
        }
    }

    public void damageMonster(int index){
        if(index != 999){
            if(!gamePanel.monsters[index].invincible){
                gamePanel.playSFX(5);

                int damage = attack - gamePanel.monsters[index].defence;
                if(damage < 0){
                    damage = 0;
                }
                //damaging monster
                gamePanel.monsters[index].Pdv -= damage;
                gamePanel.monsters[index].invincible = true;
                gamePanel.monsters[index].damageReaction();

                //killing monster
                if(gamePanel.monsters[index].Pdv <= 0){
                  gamePanel.monsters[index].isDying = true;
                  gamePanel.ui.addMessage(gamePanel.monsters[index].name + " tué!");
                  gamePanel.ui.addMessage(gamePanel.monsters[index].exp + " points d'expérience ");
                  exp += gamePanel.monsters[index].exp;
                  NiveauSuivantTest();
                }
            }
        }
    }

    public void NiveauSuivantTest(){
        if(exp >= niveauSuivantExperience){
            level++;
            niveauSuivantExperience = niveauSuivantExperience*3;
            PdvMax += 2;
            strenght++;
            agilite++;
            attack = AttaqueDegats();
            defence = calculateDefence();
            gamePanel.playSFX(8);
            gamePanel.gameState = gamePanel.EtatDialogue;
            gamePanel.ui.currentDialogue = "Le niveau " + level + " Atteint!/n" +
                    "Vous etes devenu puissant !";
        }
    }

    public void selectiondelObjet(){

        int itemIndex = gamePanel.ui.getItemIndexOnSlot();

        if(itemIndex < inventaire.size()){

            Entity ObjetSelectionne = inventaire.get(itemIndex);

            if(ObjetSelectionne.type == type_sword || ObjetSelectionne.type == type_axe){
                ArmeActuelle = ObjetSelectionne;
                attack = AttaqueDegats();
                getJoueurAttackImage();
            }

            if(ObjetSelectionne.type == type_shield){
                BouclierActuel = ObjetSelectionne;
                defence = calculateDefence();
            }
            if(ObjetSelectionne.type == type_consumable){
                ObjetSelectionne.use(this);
                inventaire.remove(itemIndex);
            }
        }
    }

    public void draw(Graphics2D g2){
        //tracage Joueur sprite
        BufferedImage image = null;
        int tempAxeAbcisse = AxeAbcisse;
        int tempAxeOrdonnee = AxeOrdonnee;

        switch (direction) {
            case "up" -> {

                if(EtatAttaque){
                    tempAxeOrdonnee = AxeOrdonnee - gamePanel.getTileSize();
                    if (numeroDuSprite == 1) {
                        image = attackhaut1;
                    }
                    if (numeroDuSprite == 2) {
                        image = attackhaut2;
                    }
                }
                if(!EtatAttaque){
                    if (numeroDuSprite == 1) {
                        image = haut1;
                    }
                    if (numeroDuSprite == 2) {
                        image = haut2;
                    }
                }
            }
            case "down" -> {
                if(!EtatAttaque){
                    if (numeroDuSprite == 1) {
                        image = bas1;
                    }
                    if (numeroDuSprite == 2) {
                        image = bas2;
                    }
                }
                if(EtatAttaque){
                    if (numeroDuSprite == 1) {
                        image = attackbas1;
                    }
                    if (numeroDuSprite == 2) {
                        image = attackbas2;
                    }
                }
            }
            case "left" -> {

                if(EtatAttaque){
                    tempAxeAbcisse = AxeAbcisse - gamePanel.getTileSize();
                    if (numeroDuSprite == 1) {
                        image = attackgauche1;
                    }
                    if (numeroDuSprite == 2) {
                        image = attackgauche2;
                    }
                }
                if(!EtatAttaque){
                    if (numeroDuSprite == 1) {
                        image = gauche1;
                    }
                    if (numeroDuSprite == 2) {
                        image = gauche2;
                    }
                }
            }
            case "right" -> {
                if(!EtatAttaque){
                    if (numeroDuSprite == 1) {
                        image = droit1;
                    }
                    if (numeroDuSprite == 2) {
                        image = droit2;
                    }
                }
                if(EtatAttaque){
                    if (numeroDuSprite == 1) {
                        image = attackdroit1;
                    }
                    if (numeroDuSprite == 2) {
                        image = attackdroit2;
                    }
                }
            }
        }

        //L'opacite du joueur diminue
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        g2.drawImage(image, tempAxeAbcisse, tempAxeOrdonnee, null);

        //reset de l'alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
