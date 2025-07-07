package Object;

import JavaLearn.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

		public BufferedImage image;
		public String name;
		public boolean collision = false;
		public int worldX, worldY;
		public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
		public int solidAreaDefaultX = 0;
		public int solidAreaDefaultY = 0;
		
		public void draw(Graphics2D g2, GamePanel gp, JavaLearn.Camera camera) {
        int screenX = camera.getScreenX(worldX);
        int screenY = camera.getScreenY(worldY);
        if(worldX + gp.tileSize > camera.worldX - camera.screenWidth/2 &&
           worldX - gp.tileSize < camera.worldX + camera.screenWidth/2 &&
           worldY + gp.tileSize > camera.worldY - camera.screenHeight/2 &&
           worldY - gp.tileSize < camera.worldY + camera.screenHeight/2) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
