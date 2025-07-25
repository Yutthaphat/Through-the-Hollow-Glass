package Map;

import CheckpointSystem.CheckpointManager;
import CheckpointSystem.Checkpoint_Start_Forest_Scene;
import JavaLearn.GamePanel;
import ObjectInMap.*;

public class ForestMap extends Map {
    public ForestMap(int maxWorldCol, int maxWorldRow, int maxObjects) {
        super(maxWorldCol, maxWorldRow, maxObjects);
    }

    @Override
    public void loadMap(GamePanel gp) {
        try {
            java.io.InputStream is = getClass().getResourceAsStream("/maps/Map_Forest.txt");
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
       /* objects[0] = new OBJ_Key();
        objects[0].worldX = 23 * gp.tileSize;
        objects[0].worldY = 7 * gp.tileSize;

        objects[1] = new OBJ_Key();
        objects[1].worldX = 23 * gp.tileSize;
        objects[1].worldY = 40 * gp.tileSize;

        objects[2] = new OBJ_Key();
        objects[2].worldX = 37 * gp.tileSize;
        objects[2].worldY = 7 * gp.tileSize;   */

        objects[3] = new OBJ_MetalfenceRight();
        objects[3].worldX = 38 * gp.tileSize;
        objects[3].worldY = 39 * gp.tileSize;

        objects[4] = new OBJ_MetalfenceLeft();
        objects[4].worldX = 37 * gp.tileSize;
        objects[4].worldY = 39 * gp.tileSize;

        objects[6] = new OBJ_Chest();
        objects[6].worldX = 40 * gp.tileSize;
        objects[6].worldY = 16 * gp.tileSize;

      /*  objects[7] = new OBJ_Boots();
        objects[7].worldX = 37 * gp.tileSize;
        objects[7].worldY = 42 * gp.tileSize;  */

        objects[8] = new OBJ_CarFront();
        objects[8].worldX = 9 * gp.tileSize;
        objects[8].worldY = 14 * gp.tileSize;

        objects[9] = new OBJ_CarBack();
        objects[9].worldX = 8 * gp.tileSize;
        objects[9].worldY = 14 * gp.tileSize;

        objects[10] = new OBJ_StopCarWoodBoard();
        objects[10].worldX = 14 * gp.tileSize;
        objects[10].worldY = 14 * gp.tileSize;

        objects[11] = new OBJ_DirectionWoodBoard();
        objects[11].worldX = 27 * gp.tileSize;
        objects[11].worldY = 14 * gp.tileSize;

        objects[12] = new OBJ_WoodBody();
        objects[12].worldX = 25 * gp.tileSize;
        objects[12].worldY = 19 * gp.tileSize;

        objects[13] = new OBJ_WoodBody();
        objects[13].worldX = 34 * gp.tileSize;
        objects[13].worldY = 26 * gp.tileSize;

        objects[14] = new OBJ_WoodHead();
        objects[14].worldX = 24 * gp.tileSize;
        objects[14].worldY = 19 * gp.tileSize;

        objects[15] = new OBJ_WoodHead();
        objects[15].worldX = 33 * gp.tileSize;
        objects[15].worldY = 26 * gp.tileSize;

        objects[16] = new OBJ_WoodTail();
        objects[16].worldX = 26 * gp.tileSize;
        objects[16].worldY = 19 * gp.tileSize;

        objects[17] = new OBJ_WoodTail();
        objects[17].worldX = 35 * gp.tileSize;
        objects[17].worldY = 26 * gp.tileSize;

        //for testing
        objects[18] = new OBJ_Boots();
        objects[18].worldX = 14 * gp.tileSize;
        objects[18].worldY = 15 * gp.tileSize;
    }

    @Override
    public void setCheckpoints(GamePanel gp) {
        checkpointManager = new CheckpointManager();
        /*
        checkpointManager.addCheckpoint(
                new Checkpoint_Story(
                        gp.tileSize * 14,
                        gp.tileSize * 13,
                        gp.tileSize,
                        gp.tileSize));

         */

        checkpointManager.addCheckpoint(
                new Checkpoint_Start_Forest_Scene(
                        gp.tileSize * 12,
                        gp.tileSize * 13,
                        gp.tileSize,
                        gp.tileSize));
    }
}