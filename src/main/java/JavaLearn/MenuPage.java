package JavaLearn;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.InputStream;


public class MenuPage {
    // Menu button state
    private int menuSelected = -1;
    private final String[] menuItems = {"Start New Game", "Setting", "Exit"};
    private final Rectangle[] menuButtons = new Rectangle[3];
    private Font font;
    public int bgVolume = 100;
    public int effectVolume = 100;
    private int hoveredButton = -1;
    private int hoveredSetting = -1;
    private final Rectangle[] settingButtons = new Rectangle[5]; // +/-, +/-, Back
    private Font charmFont;
    GamePanel gp;

    public MenuPage(GamePanel gp){
        this.gp = gp;
        // Try to load Charm font
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Charm-Regular.ttf");
            if (is != null) {
                charmFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, 20f);
                font = charmFont;
            } else {
                font = new Font("Serif", Font.BOLD, 20);
            }
        } catch (Exception e) {
            font = new Font("Serif", Font.BOLD, 20);
        }
        // Mouse listeners for menu and settings
        gp.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (gp.gameState == Entity.CommonConstant.STATE_MENU) {
                    hoveredButton = -1;
                    for (int i = 0; i < menuButtons.length; i++) {
                        if (menuButtons[i] != null && menuButtons[i].contains(e.getPoint())) {
                            hoveredButton = i;
                        }
                    }
                    gp.repaint();
                } else if (gp.gameState == Entity.CommonConstant.STATE_SETTING) {
                    hoveredSetting = -1;
                    for (int i = 0; i < settingButtons.length; i++) {
                        if (settingButtons[i] != null && settingButtons[i].contains(e.getPoint())) {
                            hoveredSetting = i;
                        }
                    }
                    gp.repaint();
                }
            }
        });
        gp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gp.gameState == Entity.CommonConstant.STATE_MENU) {
                    for (int i = 0; i < menuButtons.length; i++) {
                        if (menuButtons[i] != null && menuButtons[i].contains(e.getPoint())) {
                            menuSelected = i;
                            gp.repaint();
                        }
                    }
                } else if (gp.gameState == Entity.CommonConstant.STATE_SETTING) {
                    for (int i = 0; i < settingButtons.length; i++) {
                        if (settingButtons[i] != null && settingButtons[i].contains(e.getPoint())) {
                            hoveredSetting = i;
                            gp.repaint();
                        }
                    }
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (gp.gameState == Entity.CommonConstant.STATE_MENU && menuSelected != -1) {
                    if (menuButtons[menuSelected].contains(e.getPoint())) {
                        if (menuSelected == 0) {
                            gp.gameState = Entity.CommonConstant.STATE_PLAY;
                        } else if (menuSelected == 1) {
                            gp.gameState = Entity.CommonConstant.STATE_SETTING;
                        } else if (menuSelected == 2) {
                            System.exit(0);
                        }
                    }
                    menuSelected = -1;
                    gp.repaint();
                } else if (gp.gameState == Entity.CommonConstant.STATE_SETTING && hoveredSetting != -1) {
                    // 0: bg -, 1: bg +, 2: effect -, 3: effect +, 4: Back
                    switch (hoveredSetting) {
                        case 0 -> bgVolume = Math.max(0, bgVolume - 10);
                        case 1 -> bgVolume = Math.min(100, bgVolume + 10);
                        case 2 -> effectVolume = Math.max(0, effectVolume - 10);
                        case 3 -> effectVolume = Math.min(100, effectVolume + 10);
                        case 4 -> gp.gameState = Entity.CommonConstant.STATE_MENU;
                    }
                    // Set actual sound volumes if methods exist
                    try { gp.music.setVolume(bgVolume); } catch (Exception ignored) {}
                    try { gp.se.setVolume(effectVolume); } catch (Exception ignored) {}
                    hoveredSetting = -1;
                    gp.repaint();
                }
            }
        });
    }

    void drawMenu(Graphics2D g2) {
        // Title
        g2.setColor(Color.BLACK);
        g2.setFont(font.deriveFont(Font.BOLD, 40f));
        String title = "Through the Hollow Glass";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (gp.screenWidth-titleWidth)/2, 120);
        // Buttons
        g2.setFont(font.deriveFont(Font.PLAIN, 24f));
        int btnWidth = 300;
        int btnHeight = 50;
        int btnX = (gp.screenWidth-btnWidth)/2;
        int btnY = 200;
        int gap = 20;
        for (int i = 0; i < menuItems.length; i++) {
            int y = btnY + i*(btnHeight+gap);
            menuButtons[i] = new Rectangle(btnX, y, btnWidth, btnHeight);
            Color base = Color.LIGHT_GRAY;
            if (hoveredButton == i) base = Color.ORANGE;
            if (menuSelected == i) base = new Color(255,140,0);
            g2.setColor(base);
            g2.fillRect(btnX, y, btnWidth, btnHeight);
            g2.setColor(Color.BLACK);
            g2.drawRect(btnX, y, btnWidth, btnHeight);
            int textWidth = g2.getFontMetrics().stringWidth(menuItems[i]);
            int textHeight = g2.getFontMetrics().getAscent();
            g2.drawString(menuItems[i], btnX + (btnWidth-textWidth)/2, y + (btnHeight+textHeight)/2 - 6);
        }
    }
    
    void drawSettings(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setFont(font.deriveFont(Font.BOLD, 32f));
        String title = "Settings";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (gp.screenWidth-titleWidth)/2, 120);
        g2.setFont(font.deriveFont(Font.PLAIN, 22f));
        int y1 = 200, y2 = 250;
        int xLabel = 120, xVal = 500, xMinus = 420, xPlus = 570, btnW = 40, btnH = 40;
        // BG Volume
        g2.drawString("Background music volume:", xLabel, y1+28);
        g2.drawString(String.valueOf(bgVolume), xVal, y1+28);
        settingButtons[0] = new Rectangle(xMinus, y1, btnW, btnH);
        settingButtons[1] = new Rectangle(xPlus, y1, btnW, btnH);
        g2.setColor(hoveredSetting == 0 ? Color.ORANGE : Color.LIGHT_GRAY);
        g2.fillRect(xMinus, y1, btnW, btnH);
        g2.setColor(Color.BLACK);
        g2.drawRect(xMinus, y1, btnW, btnH);
        g2.drawString("-", xMinus+btnW/2-6, y1+btnH/2+8);
        g2.setColor(hoveredSetting == 1 ? Color.ORANGE : Color.LIGHT_GRAY);
        g2.fillRect(xPlus, y1, btnW, btnH);
        g2.setColor(Color.BLACK);
        g2.drawRect(xPlus, y1, btnW, btnH);
        g2.drawString("+", xPlus+btnW/2-6, y1+btnH/2+8);
        // Effect Volume
        g2.setColor(Color.BLACK);
        g2.drawString("Effect volume:", xLabel, y2+28);
        g2.drawString(String.valueOf(effectVolume), xVal, y2+28);
        settingButtons[2] = new Rectangle(xMinus, y2, btnW, btnH);
        settingButtons[3] = new Rectangle(xPlus, y2, btnW, btnH);
        g2.setColor(hoveredSetting == 2 ? Color.ORANGE : Color.LIGHT_GRAY);
        g2.fillRect(xMinus, y2, btnW, btnH);
        g2.setColor(Color.BLACK);
        g2.drawRect(xMinus, y2, btnW, btnH);
        g2.drawString("-", xMinus+btnW/2-6, y2+btnH/2+8);
        g2.setColor(hoveredSetting == 3 ? Color.ORANGE : Color.LIGHT_GRAY);
        g2.fillRect(xPlus, y2, btnW, btnH);
        g2.setColor(Color.BLACK);
        g2.drawRect(xPlus, y2, btnW, btnH);
        g2.drawString("+", xPlus+btnW/2-6, y2+btnH/2+8);
        // Back button
        int backY = 350;
        settingButtons[4] = new Rectangle(xLabel, backY, 120, 40);
        g2.setColor(hoveredSetting == 4 ? Color.ORANGE : Color.LIGHT_GRAY);
        g2.fillRect(xLabel, backY, 120, 40);
        g2.setColor(Color.BLACK);
        g2.drawRect(xLabel, backY, 120, 40);
        g2.drawString("Back", xLabel+30, backY+28);
    }
}
