package Tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import JavaLearn.GamePanel;
import JavaLearn.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[50]; // กำหนดขนาด 10 สำหรับ Tile types
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // สร้างอาเรย์สำหรับเก็บข้อมูลแผนที่

		getTileImage(); // โหลดรูปภาพ Tile
		loadMap("/maps/worldV2.txt"); // โหลดข้อมูลแผนที่จากไฟล์
	}
	
	public void getTileImage() {

		// PLACEHOLDER
		setup(0, "grass00", false);
		setup(1, "grass00", false);
		setup(2, "grass00", false);
		setup(3, "grass00", false);
		setup(4, "grass00", false);
		setup(5, "grass00", false);
		setup(6, "grass00", false);	
		setup(7, "grass00", false);	
		setup(8, "grass00", false);	
		setup(9, "grass00", false);	
		// PLACEHOLDER
		
		setup(10, "grass00", false);
		setup(11, "grass01", false);
		setup(12, "water00", true);
		setup(13, "water01", true);
		setup(14, "water02", true);
		setup(15, "water03", true);
		setup(16, "water04", true);
		setup(17, "water05", true);
		setup(18, "water06", true);
		setup(19, "water07", true);
		setup(20, "water08", true);
		setup(21, "water09", true);
		setup(22, "water10", true);
		setup(23, "water11", true);
		setup(24, "water12", true);
		setup(25, "water13", true);
		setup(26, "road00", false);
		setup(27, "road01", false);
		setup(28, "road02", false);
		setup(29, "road03", false);
		setup(30, "road04", false);
		setup(31, "road05", false);
		setup(32, "road06", false);
		setup(33, "road07", false);
		setup(34, "road08", false);
		setup(35, "road09", false);
		setup(36, "road10", false);
		setup(37, "road11", false);
		setup(38, "road12", false);
		setup(39, "earth", false);
		setup(40, "wall", true);
		setup(41, "tree", true);						
	}
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String filePath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			// ลูปอ่านข้อมูลแผนที่
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) { // วนลูปผิดตรรกะสำหรับการอ่านไฟล์
				
				String line = br.readLine(); // อ่านหนึ่งบรรทัด
				
				while(col < gp.maxWorldCol) { // วนลูปคอลัมน์ อาจเกินขนาดจริงของ numbers[]
					
					String numbers[] = line.split(" "); // แยกตัวเลข
					
					int num = Integer.parseInt(numbers[col]); // อาจเกิด ArrayIndexOutOfBoundsException
					
					mapTileNum[col][row] = num; // เก็บข้อมูลลงในอาเรย์
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			 //e.printStackTrace(); 
		}
	}
	
	public void draw(Graphics2D g2) {
	    int worldCol = 0;
	    int worldRow = 0;
	    
	    // ปรับปรุงลูป: วนลูปทั่วทั้ง World Map (ตาม maxWorldCol, maxWorldRow)
	    while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) { 
	        
	        int tileNum = mapTileNum[worldCol][worldRow];
	        
	        int worldX = worldCol * gp.tileSize; // คำนวณพิกัดของ Tile นี้ใน World Map
	        int worldY = worldRow * gp.tileSize;

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
	            
	            g2.drawImage(tile[tileNum].image, screenX, screenY, null);
	        }

	        worldCol++;
	        
	        if(worldCol == gp.maxWorldCol) {
	            worldCol = 0;
	            worldRow++;
	        }
	    }
	}
}