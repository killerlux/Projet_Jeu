package main;

import Entity.Entity;
import Entity.Joueur;
import Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class GamePanel extends JPanel implements Runnable{

    //Screen settings
    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    //Getters and Setters
    public int getTileSize() {return tileSize;}
    public int getScreenWidth() {return screenWidth;}
    public int getScreenHeight() {return screenHeight;}

    //World settings
    final int maxWorldCol = 50;
    final int maxWorldRow = 50;

    public int getMaxWorldCol() {return maxWorldCol;}
    public int getMaxWorldRow() {return maxWorldRow;}

   //System settings
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound sfx = new Sound();
    public CollisionChecker checker = new CollisionChecker(this);
    public SetterRessources SetterRessources = new SetterRessources(this);
    public UI ui = new UI(this);
    Thread gameThread;
    public EventHandler eventHandler = new EventHandler(this);

    //Joueur, NPC & Items
    public Entity[] items = new Entity[10];
    public Joueur Joueur = new Joueur(this, keyHandler);
    public Entity[] npcs = new Entity[10];
    public Entity[] monsters = new Entity[20];
    ArrayList<Entity> entities = new ArrayList<>();

    //Game state
    public int gameState;
    public final int EtatJeu = 1;
    public final int EtatPause = 2;
    public  final int EtatDialogue = 3;
    public final int EtatJoueur = 4;
    public final int titleState = 0;

    //Constructor
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void gameSetup(){
        SetterRessources.SetterObjet();
        SetterRessources.SetterPNJ();
        SetterRessources.SetterMob();
        //playMusic(0);
        gameState = titleState;
    }

    public void update(){
        if(gameState == EtatJeu){
            //Joueur
            Joueur.update();
            //npc
            for (Entity npc : npcs) {
                if (npc != null) {
                    npc.update();
                }
            }


            for (int i = 0; i < monsters.length; i++) {
                if(monsters[i] != null){
                    if (monsters[i].isAlive && !monsters[i].isDying) {
                        monsters[i].update();
                    }
                    if(!monsters[i].isAlive){
                        monsters[i] = null;
                    }
                }
            }
        }
        if(gameState == EtatPause){
            //nothing
        }

    }

    //Drawing objects to the screen
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //Debug
        long drawStart = 0;
        if(keyHandler.checkDrawTime){
            drawStart = System.nanoTime();
        }

        //Tile screen
        if(gameState == titleState){
        ui.draw(g2);
        }
        //others
        else{
            //Tiles
            tileManager.draw(g2);

            //Joueur
            entities.add(Joueur);

            //NPC
            for (Entity npc : npcs) {
                if (npc != null) {
                    entities.add(npc);
                }
            }

            //Monsters
            for (Entity monster : monsters) {
                if (monster != null) {
                    entities.add(monster);
                }
            }

            //Items
            for (Entity item : items) {
                if (item != null) {
                    entities.add(item);
                }
            }

            //Sort
            Collections.sort(entities, (o1, o2) -> {
                int result = Integer.compare(o1.mondeAxeOrdonnee, o2.mondeAxeOrdonnee);
                return result;});
            
            //Draw entities
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).draw(g2);
            }

            //Reset entities
            entities.clear();

            //UI
            ui.draw(g2);
        }

        //Debug
        if(keyHandler.checkDrawTime){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }
        g2.dispose();
    }

    //Sounds & Music
    public void playMusic(int index){
        music.setFile(index);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSFX(int index){
        sfx.setFile(index);
        sfx.play();
    }

    //Game loop
    @Override
    public void run() {
        int FPS = 60;
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Staring game loop thread
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
}
