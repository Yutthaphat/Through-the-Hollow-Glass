package Tile;

import JavaLearn.Camera;
import JavaLearn.GamePanel;
import Map.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
	
	GamePanel gp;
	Camera camera;
	public Tile[] tile;
    Map map;
	
	public TileManager(GamePanel gp, Camera camera, Map map) {
		
		this.gp = gp;
		this.camera = camera;
		this.map = map;
		
		tile = new Tile[10]; // กำหนดขนาด 10 สำหรับ Tile types
		getTileImage(); // โหลดรูปภาพ Tile
	}
	
	public void getTileImage() {
		
		try {
			// โหลดรูปภาพ Tile ประเภทต่างๆ
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true;

			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
			
		}catch(IOException e) {
			e.printStackTrace(); // พิมพ์ stack trace หากมีข้อผิดพลาดในการโหลดรูป
		}
	}

	public void loadMap(String filePath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath.replace("res/", "/"));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			// ลูปอ่านข้อมูลแผนที่
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) { // วนลูปผิดตรรกะสำหรับการอ่านไฟล์
				
				String line = br.readLine(); // อ่านหนึ่งบรรทัด
				
				while(col < gp.maxWorldCol) { // วนลูปคอลัมน์ อาจเกินขนาดจริงของ numbers[]
					
					String numbers[] = line.split(" "); // แยกตัวเลข
					
					int num = Integer.parseInt(numbers[col]); // อาจเกิด ArrayIndexOutOfBoundsException
					
					map.mapTileNum[col][row] = num; // เก็บข้อมูลลงในอาเรย์
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
	    int worldCol = 0;
	    int worldRow = 0;
	    
	    // ปรับปรุงลูป: วนลูปทั่วทั้ง World Map (ตาม maxWorldCol, maxWorldRow)
	    while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) { 
	        
	        int tileNum = map.mapTileNum[worldCol][worldRow];
	        
	        int worldX = worldCol * gp.tileSize; // คำนวณพิกัดของ Tile นี้ใน World Map
	        int worldY = worldRow * gp.tileSize;

	        // คำนวณพิกัดของ Tile นี้บนหน้าจอ (สัมพันธ์กับตำแหน่งผู้เล่น)
	        // นี่คือสูตรมาตรฐานสำหรับการทำกล้องติดตามผู้เล่นในเกม 2D
	        int screenX = camera.getScreenX(worldX);
	        int screenY = camera.getScreenY(worldY);
	        
	        // **เพิ่ม Optimization (ไม่บังคับ แต่ดีต่อประสิทธิภาพ):**
	        // วาด Tile เฉพาะเมื่อ Tile นั้นอยู่ในขอบเขตการมองเห็นของหน้าจอเท่านั้น
	        // (เผื่อขอบนอกออกไปอีก 1 Tile เพื่อป้องกันขอบดำเวลาผู้เล่นขยับ)
	        if(worldX + gp.tileSize > camera.worldX - camera.screenWidth/2 &&
	           worldX - gp.tileSize < camera.worldX + camera.screenWidth/2 && // + gp.tileSize เพื่อขยายขอบขวา
	           worldY + gp.tileSize > camera.worldY - camera.screenHeight/2 &&
	           worldY - gp.tileSize < camera.worldY + camera.screenHeight/2) { // + gp.tileSize เพื่อขยายขอบล่าง
	            
	            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	        }

	        worldCol++;
	        
	        if(worldCol == gp.maxWorldCol) {
	            worldCol = 0;
	            worldRow++;
	        }
	    }
	}
}