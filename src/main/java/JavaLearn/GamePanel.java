package JavaLearn;

import Entity.CommonConstant;
import Entity.Player;
import Map.ForestMap;
import Map.Map;
import Tile.TileManager;
import common.Camera;
import common.CollisionChecker;
import common.Sound;
import common.EventManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
	
	final int originalTileSize = 16;  // 16*16 tile
	final int scale = 3; // 16*3(scale)
	public final int tileSize = originalTileSize * scale;  // 48*48 tile
	public final int maxScreenCol = 16;  // ขนาดแนวนอน
	public final int maxScreenRow = 12;  // ขนาดแนวตั้ง
	public final int screenWidth = tileSize * maxScreenCol;  // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow;  // 576 pixels
	
	//WORLD SETTINGS
	public final int maxWorldCol = 66;
	public final int maxWorldRow = 42;

	//FPS
	int FPS = 60;	
	
	// SYSTEM
	public EventManager eventManager;
	public Camera camera;
	public TileManager tileM;
	KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public PlayerUI ui = new PlayerUI(this);
	Thread gameThread;  // ทำให้โปรแกรมรันได้จนกว่าเราจะหยุด
	MenuPage menuPage = new MenuPage(this);
	
	// ENTITY AND OBJECT
	public Player player;
	public Map map;
    public int gameState = CommonConstant.STATE_MENU;

	public GamePanel () {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));  // กำหนดขนาดของคลาสนี้(JPanel)
		this.setBackground(Color.white);   // พื้นหลังดำ
		this.setDoubleBuffered(true);  // ถ้าทุกอย่างจริง ให้วาดทุกอย่างลงที่หน่วยความจำก่อนค่อยแสดงไปบนหน้าจอจริง ทำการกระพริบทำให้ภาพลื่นไหลต่อเนื่อง
		this.addKeyListener(keyH);  // จดจำอินพุตคีย์
		this.setFocusable(true); // โฟกัสเพื่อรับอินพุตสสำคัญ

		// Initialize camera at player center
		camera = new Camera(23 * tileSize + tileSize / 2, 21 * tileSize + tileSize / 2, screenWidth, screenHeight);
		map = new ForestMap(maxWorldCol, maxWorldRow, 30);
		map.loadMap(this);
		map.setObjects(this);
		tileM = new TileManager(this, camera, map);
		player = new Player(this, keyH, camera);
		eventManager = new EventManager(player);
	}
	
	public void setupGame() {
		
		playMusic(0);
	}
	
	public void startGameThread(){
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {	
		double drawInterval = 1000000000/FPS;  //กำหนดความถี่ต่อเฟรม (เป็นหน่วย นาโนวินาที) เช่น FPS = 60 → 16,666,666 ns ต่อเฟรม
		double delta = 0;   //ใช้สะสมเวลา (ดูว่าถึงเวลาวาดหรือยัง)
		long lastTime = System.nanoTime();  //จำเวลาล่าสุด
		long currentTime;
		long timer = 0;   //เอาไว้จับครบ 1 วินาทีเพื่อโชว์ FPS
		int drawCount = 0;   // นับว่าวาดไปกี่เฟรมแล้ว
		
		while(gameThread != null){    //ถ้าเกมยังไม่หยุด → วนลูปเกมเรื่อย ๆ
			currentTime = System.nanoTime();   
			delta += (currentTime - lastTime) / drawInterval;  //เอาเวลาที่ผ่านไปมาสะสมใน delta
			timer = (currentTime - lastTime);    //เอาเวลาที่ผ่านไปเก็บใน timer เพื่อดูว่าครบ 1 วินาทีหรือยัง
			lastTime = currentTime;
			if(delta >= 1){
				update();      // อัปเดตเกม
				repaint();     // วาดหน้าจอ
				delta--;       // ลบ delta ลง 1 (คือเราวาดครบ 1 เฟรมแล้ว)
				drawCount++;   // นับเฟรมที่วาดไป
			}

			if(timer >= 1000000000){  //ถ้าผ่านไปครบ 1 วินาที → แสดงผล FPS จริง ๆ ที่วาดไปในวินาทีนั้น
				System.out.println("FPS" + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}

	public void update() {
		player.update();
		// EventManager handles event progression if needed
		// Camera follows player center
		updateCamera();
	}

	public void updateCamera() {
		camera.worldX = player.worldX + tileSize / 2;
		camera.worldY = player.worldY + tileSize / 2;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		if (gameState == CommonConstant.STATE_MENU) {
			menuPage.drawMenu(g2);
		} else if (gameState == CommonConstant.STATE_PLAY) {
			// TILE
			tileM.draw(g2);
			// OBJECT
			for(int i = 0; i < map.objects.length; i++) {
				if(map.objects[i] != null) {
					map.objects[i].draw(g2, this, camera);
				}
			}
			// PLAYER
			player.draw(g2);
			// UI
			ui.draw(g2);
		} else if (gameState == CommonConstant.STATE_SETTING) {
			menuPage.drawSettings(g2);
		}
		g2.dispose();
	}
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		
		music.stop();
	}
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
	}
}	

