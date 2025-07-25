package Tile;

import JavaLearn.GamePanel;
import Map.Map;
import common.Camera;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
    Map map;

	public TileManager(GamePanel gp, Map map) {

		this.gp = gp;
		this.map = map;

		tile = new Tile[30]; // กำหนดขนาด 10 สำหรับ Tile types
		getTileImage(); // โหลดรูปภาพ Tile
	}

	public void getTileImage() {

		try {
			// โหลดรูปภาพ Tile ประเภทต่างๆ
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[0].collision = true;

			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/fence.png"));
			tile[1].collision = true;

			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/roadwalk.png"));

			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bush.png"));

			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/roadcan'twalk.png"));
			tile[4].collision = true;

			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[5].collision = true;

			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rock.png"));
			tile[6].collision = true;

			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tombstone_sign.png"));
			tile[7].collision = true;

			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/objects/car_back.png"));
			tile[8].collision = true;

			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/objects/car_front.png"));
			tile[9].collision = true;

			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/objects/metalfence_left.png"));
			tile[10].collision = true;

			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/objects/metalfence_right.png"));
			tile[11].collision = true;


		}catch(IOException e) {
			e.printStackTrace(); // พิมพ์ stack trace หากมีข้อผิดพลาดในการโหลดรูป
		}
	}

	public void draw(Graphics2D g2) {
	    int worldCol = 0;
	    int worldRow = 0;
	    Camera gpCamera = gp.camera;

	    // ปรับปรุงลูป: วนลูปทั่วทั้ง World Map (ตาม maxWorldCol, maxWorldRow)
	    while(worldCol < gp.map.maxWorldCol && worldRow < gp.map.maxWorldRow) {

	        int tileNum = map.mapTileNum[worldCol][worldRow];

	        int worldX = worldCol * gp.tileSize; // คำนวณพิกัดของ Tile นี้ใน World Map
	        int worldY = worldRow * gp.tileSize;

	        // คำนวณพิกัดของ Tile นี้บนหน้าจอ (สัมพันธ์กับตำแหน่งผู้เล่น)
	        // นี่คือสูตรมาตรฐานสำหรับการทำกล้องติดตามผู้เล่นในเกม 2D
	        int screenX = gpCamera.getScreenX(worldX);
	        int screenY = gpCamera.getScreenY(worldY);

	        // **เพิ่ม Optimization (ไม่บังคับ แต่ดีต่อประสิทธิภาพ):**
	        // วาด Tile เฉพาะเมื่อ Tile นั้นอยู่ในขอบเขตการมองเห็นของหน้าจอเท่านั้น
	        // (เผื่อขอบนอกออกไปอีก 1 Tile เพื่อป้องกันขอบดำเวลาผู้เล่นขยับ)
	        if(worldX + gp.tileSize > gpCamera.worldX - gpCamera.screenWidth/2 &&
	           worldX - gp.tileSize < gpCamera.worldX + gpCamera.screenWidth/2 && // + gp.tileSize เพื่อขยายขอบขวา
	           worldY + gp.tileSize > gpCamera.worldY - gpCamera.screenHeight/2 &&
	           worldY - gp.tileSize < gpCamera.worldY + gpCamera.screenHeight/2) { // + gp.tileSize เพื่อขยายขอบล่าง

	            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	        }

	        worldCol++;

	        if(worldCol == gp.map.maxWorldCol) {
	            worldCol = 0;
	            worldRow++;
	        }
	    }
	}
}