package JavaLearn;

public class Camera {
    public int worldX;
    public int worldY;
    public int screenWidth;
    public int screenHeight;

    public Camera(int worldX, int worldY, int screenWidth, int screenHeight) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public int getScreenX(int objWorldX) {
        return objWorldX - worldX + (screenWidth / 2);
    }

    public int getScreenY(int objWorldY) {
        return objWorldY - worldY + (screenHeight / 2);
    }
} 