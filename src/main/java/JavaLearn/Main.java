package JavaLearn;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // ปุ่มกากบาทปิดหน้าต่าง
		window.setResizable(false);  // ปรับขนาดหน้าต่างไม่ได้
		window.setTitle("2D Adventure");  // ชื่อหัวข้อ
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);  // ไม่ระบุตำแหน่งหน้าต่าง ---> หน้าต่างอยู่ตรงกลาง
		window.setVisible(true); // ทำให้เห็นหน้าต่างได้
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
}
