package Entity;


import JavaLearn.GamePanel;
import JavaLearn.KeyHandler;
import common.Camera;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

	public GamePanel gp;
	KeyHandler keyH;

	public int hasKey = 0;
	
	private boolean locked = false;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;

		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 12;
		worldY = gp.tileSize * 13;
		speed = 4;
		direction = "down";
	}
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/evelyn_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/evelyn_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/evelyn_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/evelyn_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/evelyn_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/evelyn_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/evelyn_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/evelyn_right_2.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void lock() { locked = true; }
	public void unlock() { locked = false; }
	
	public void update() {
		
		if (locked) {
			// Skip movement and actions during event
			return;
		}
		
		if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.interactPressed) {
			
			if(keyH.upPressed){
				direction = "up";
			}
			else if(keyH.downPressed){
				direction = "down";
			}
			else if(keyH.leftPressed){
				direction = "left";
			}
			else if(keyH.rightPressed){
				direction = "right";
			}

			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);

			//check object interact
			int objIndex = gp.cChecker.checkObject(this, true, gp.map);
			if (objIndex != 999) {
				if (gp.map.objects[objIndex].isInteractive && keyH.interactPressed) {
					gp.map.objects[objIndex].onInteract(this);
				}
			}

			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false) {

				switch(direction) {
				case"up":
					worldY -= speed;
					break;
				case"down":
					worldY += speed;
					break;
				case"left":
					worldX -= speed;
					break;
				case"right":
					worldX += speed;
					break;
				}
			}

			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		/*
		else {
			counter2++;
			if(counter2 == 20) {
				spriteNum = 1;
				counter2 = 0;
			}
		}*/
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;

		switch (direction) {
			case "up" -> {
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
			}
			case "down" -> {
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
			}
			case "left" -> {
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
			}
			case "right" -> {
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
			}
		}
		Camera gpCamera = gp.camera;
		int screenX = gpCamera.getScreenX(worldX);
		int screenY = gpCamera.getScreenY(worldY);
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
