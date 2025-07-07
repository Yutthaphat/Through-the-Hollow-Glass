package Map;

import JavaLearn.GamePanel;
import Object.OBJ_Boots;
import Object.OBJ_Chest;
import Object.OBJ_Door;
import Object.OBJ_Key;

public class ForestMap extends Map {
    public ForestMap(int maxWorldCol, int maxWorldRow, int maxObjects) {
        super(maxWorldCol, maxWorldRow, maxObjects);
    }

    @Override
    public void loadMap(GamePanel gp) {
        try {
            java.io.InputStream is = getClass().getResourceAsStream("/maps/world01.txt");
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col < maxWorldCol && row < maxWorldRow) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                while (col < maxWorldCol) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setObjects(GamePanel gp) {
        objects[0] = new OBJ_Key();
        objects[0].worldX = 23 * gp.tileSize;
        objects[0].worldY = 7 * gp.tileSize;

        objects[1] = new OBJ_Key();
        objects[1].worldX = 23 * gp.tileSize;
        objects[1].worldY = 40 * gp.tileSize;

        objects[2] = new OBJ_Key();
        objects[2].worldX = 37 * gp.tileSize;
        objects[2].worldY = 7 * gp.tileSize;

        objects[3] = new OBJ_Door();
        objects[3].worldX = 10 * gp.tileSize;
        objects[3].worldY = 11 * gp.tileSize;

        objects[4] = new OBJ_Door();
        objects[4].worldX = 8 * gp.tileSize;
        objects[4].worldY = 28 * gp.tileSize;

        objects[5] = new OBJ_Door();
        objects[5].worldX = 12 * gp.tileSize;
        objects[5].worldY = 22 * gp.tileSize;

        objects[6] = new OBJ_Chest();
        objects[6].worldX = 10 * gp.tileSize;
        objects[6].worldY = 7 * gp.tileSize;

        objects[7] = new OBJ_Boots();
        objects[7].worldX = 37 * gp.tileSize;
        objects[7].worldY = 42 * gp.tileSize;
    }
} 