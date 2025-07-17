package Map;

import JavaLearn.GamePanel;
import ObjectInMap.SuperObject;
import CheckpointSystem.CheckpointManager;

public abstract class Map {
    public int[][] mapTileNum;
    public SuperObject[] objects;
    public int maxWorldCol;
    public int maxWorldRow;
    public int maxObjects;
    public CheckpointManager checkpointManager;

    public Map(int maxWorldCol, int maxWorldRow, int maxObjects) {
        this.maxWorldCol = maxWorldCol;
        this.maxWorldRow = maxWorldRow;
        this.maxObjects = maxObjects;
        mapTileNum = new int[maxWorldCol][maxWorldRow];
        objects = new SuperObject[maxObjects];
    }

    public abstract void loadMap(GamePanel gp);
    public abstract void setObjects(GamePanel gp);
    public abstract void setCheckpoints(GamePanel gp);
} 