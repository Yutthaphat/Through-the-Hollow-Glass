package JavaLearn;

import Entity.Player;
import Object.SuperObject;
import Tile.TileManager;

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
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;

	//FPS
	int FPS = 60;	
	
	// SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;  // ทำให้โปรแกรมรันได้จนกว่าเราจะหยุด 
	
	// ENTITY AND OBJECT
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10]; 

	//set player's default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;  // กำหนดความเร็วผู้เล่น

	public GamePanel () {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));  // กำหนดขนาดของคลาสนี้(JPanel)
		this.setBackground(Color.black);   // พื้นหลังดำ
		this.setDoubleBuffered(true);  // ถ้าทุกอย่างจริง ให้วาดทุกอย่างลงที่หน่วยความจำก่อนค่อยแสดงไปบนหน้าจอจริง ทำการกระพริบทำให้ภาพลื่นไหลต่อเนื่อง
		this.addKeyListener(keyH);  // จดจำอินพุตคีย์
		this.setFocusable(true); // โฟกัสเพื่อรับอินพุตสสำคัญ
	}
	
	public void setupGame() {
		
		aSetter.setObject();
		
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
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// TILE
		tileM.draw(g2);
		
		// OBJECT
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		// PLAYER
		player.draw(g2);
		
		// UI
		ui.draw(g2);
		
		g2.dispose();  // วาดเสร็จแล้วให้ปิดไม่งั้นเครื่องจะช้า
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

