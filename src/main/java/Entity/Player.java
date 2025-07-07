package Entity;


import JavaLearn.GamePanel;
import JavaLearn.KeyHandler;
import common.Camera;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	Camera camera;
	
	public int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler keyH, Camera camera) {
		
		this.gp = gp;
		this.keyH = keyH;
		this.camera = camera;
		
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
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if(keyH.upPressed == true){  // ขึ้นลดค่า Y
				direction = "up";   
			}
			else if(keyH.downPressed == true){   // ลงเพิ่มค่า Y
				direction = "down";  
			}
			else if(keyH.leftPressed == true){   // ซ้ายลดค่า X
				direction = "left";  
			}
			else if(keyH.rightPressed == true){   // ขวาเพิ่มค่า X
				direction = "right";    
			}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true, gp.map);
			pickUpObject(objIndex);
			
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
	public void pickUpObject(int i) {
		
		if(i != 999) {
			
			String objectName = gp.map.objects[i].name;
			
			switch(objectName) {
			case "Key":
				gp.playSE(1);
				hasKey++;
				gp.map.objects[i] = null;
				gp.ui.showMessage("You got a key!");
				break;
			case "Door":
				if(hasKey > 0) {
					gp.playSE(3);
					gp.map.objects[i] = null;
					hasKey--;
					gp.ui.showMessage("You opened the door!");
				}
				else {
					gp.ui.showMessage("You need a key!");
				}
				break;
			case "Boots":
				gp.playSE(2);
				speed += 1;
				gp.map.objects[i] = null;
				gp.ui.showMessage("Speed up!");
				break;
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				break;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		//g2.setColor(Color.WHITE);  // กำหนดสีขาว
		// g2.fillRect(x, y, width, height);  // วาดสี่เหลี่ยมแล้วเติมสีที่กำหนดลงไป
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
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
		int screenX = camera.getScreenX(worldX);
		int screenY = camera.getScreenY(worldY);
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
