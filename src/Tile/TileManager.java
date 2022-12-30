package Tile;


import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        tile = new Tile[50];
        mapTileNum = new int[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];

        getTileImage();
        loadMap("/Maps/world");
    }

    public void getTileImage(){
        //Placeholders
        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);

        //Grass
        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", true);

        //Water
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);

        //Road
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);

        //Earth
        setup(39, "earth", false);

        //Wall
        setup(40, "wall", true);

        //Tree
        setup(41, "tree", true);
    }

    public void setup(int index, String imageName, boolean collision){
        UtilityTool utilityTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Tiles/" + imageName + ".png")));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gamePanel.getTileSize(), gamePanel.getTileSize());
            tile[index].collision = collision;

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void loadMap(String filepath){
        try {
            InputStream is = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()){
                String line = br.readLine();

                while(col < gamePanel.getMaxWorldCol()) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gamePanel.getMaxWorldCol()) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gamePanel.getMaxWorldCol() && worldRow < gamePanel.getMaxWorldRow()) {

            int tileNum = mapTileNum[worldCol][worldRow];

            //fitting tiles around Joueur movement
            int mondeAxeAbscisse = worldCol * gamePanel.getTileSize();
            int mondeAxeOrdonnee = worldRow * gamePanel.getTileSize();
            int AxeAbcisse = mondeAxeAbscisse - gamePanel.Joueur.mondeAxeAbscisse + gamePanel.Joueur.AxeAbcisse;
            int AxeOrdonnee = mondeAxeOrdonnee - gamePanel.Joueur.mondeAxeOrdonnee + gamePanel.Joueur.AxeOrdonnee;

            //procedurally generating tiles around Joueur movement
            if(mondeAxeAbscisse + gamePanel.getTileSize()> gamePanel.Joueur.mondeAxeAbscisse - gamePanel.Joueur.AxeAbcisse &&
                    mondeAxeAbscisse - gamePanel.getTileSize()< gamePanel.Joueur.mondeAxeAbscisse + gamePanel.Joueur.AxeAbcisse &&
                    mondeAxeOrdonnee + gamePanel.getTileSize()> gamePanel.Joueur.mondeAxeOrdonnee - gamePanel.Joueur.AxeOrdonnee &&
                    mondeAxeOrdonnee - gamePanel.getTileSize()< gamePanel.Joueur.mondeAxeOrdonnee + gamePanel.Joueur.AxeOrdonnee){

                g2.drawImage(tile[tileNum].image, AxeAbcisse, AxeOrdonnee,null);
            }
            worldCol++;

            if(worldCol == gamePanel.getMaxWorldCol()){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}

