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
		
		public void draw(Graphics2D g2, GamePanel gp) {
		
	        // คำนวณพิกัดของ Tile นี้บนหน้าจอ (สัมพันธ์กับตำแหน่งผู้เล่น)
	        // นี่คือสูตรมาตรฐานสำหรับการทำกล้องติดตามผู้เล่นในเกม 2D
	        int screenX = worldX - gp.player.worldX + gp.player.screenX;
	        int screenY = worldY - gp.player.worldY + gp.player.screenY;
	        
	        // **เพิ่ม Optimization (ไม่บังคับ แต่ดีต่อประสิทธิภาพ):**
	        // วาด Tile เฉพาะเมื่อ Tile นั้นอยู่ในขอบเขตการมองเห็นของหน้าจอเท่านั้น
	        // (เผื่อขอบนอกออกไปอีก 1 Tile เพื่อป้องกันขอบดำเวลาผู้เล่นขยับ)
	        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
	           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && // + gp.tileSize เพื่อขยายขอบขวา
	           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
	           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) { // + gp.tileSize เพื่อขยายขอบล่าง
	            
	            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	        }
		}
}
