package ObjectInMap;

import JavaLearn.GamePanel;
import common.Camera;
import Entity.Player;

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
		public boolean isInteractive = false;
		public void onInteract(Player player) {}
		public boolean isPlayerColliding(Player player) {
			Rectangle playerRect = new Rectangle(player.worldX, player.worldY, player.solidArea.width, player.solidArea.height);
			Rectangle objRect = new Rectangle(worldX, worldY, solidArea.width, solidArea.height);
			return playerRect.intersects(objRect);
		}
		
		public void draw(Graphics2D g2, GamePanel gp, Camera camera) {
			int screenX = camera.getScreenX(worldX);
			int screenY = camera.getScreenY(worldY);
			if(worldX + gp.tileSize > camera.worldX - camera.screenWidth/2 &&
			   worldX - gp.tileSize < camera.worldX + camera.screenWidth/2 &&
			   worldY + gp.tileSize > camera.worldY - camera.screenHeight/2 &&
			   worldY - gp.tileSize < camera.worldY + camera.screenHeight/2) {
				g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
				drawHoverAction(g2, gp, screenX, screenY);
			}
		}

		private void drawHoverAction(Graphics2D g2, GamePanel gp, int screenX, int screenY) {
			if(isInteractive && isPlayerColliding(gp.player)) {
				// Draw red 'P' with white border at center
				String text = "P";
				Font font = new Font("Arial", Font.BOLD, gp.tileSize/2);
				g2.setFont(font);
				int textWidth = g2.getFontMetrics().stringWidth(text);
				int textHeight = g2.getFontMetrics().getAscent();
				int px = screenX + gp.tileSize/2 - textWidth/2;
				int py = screenY + gp.tileSize/2 + textHeight/2 - 4;
				g2.setColor(Color.WHITE);
				g2.drawString(text, px-2, py-2);
				g2.drawString(text, px+2, py-2);
				g2.drawString(text, px-2, py+2);
				g2.drawString(text, px+2, py+2);
				g2.setColor(Color.RED);
				g2.drawString(text, px, py);
			}
		}
}
