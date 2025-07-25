package common;


import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.util.List;

public class TextBoxUtil {
    public static void drawEventBox(Graphics2D g2, int screenWidth, List<String> messages, String hint) {
        int boxWidth = 400;
        int boxHeight = 120;
        int x = (screenWidth - boxWidth) / 2;
        int y = 40;
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(x, y, boxWidth, boxHeight, 20, 20);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x, y, boxWidth, boxHeight, 20, 20);
        g2.setColor(Color.WHITE);
        if (messages.size() == 1) {
            drawMessageInBox(g2, x, y, boxWidth, messages.get(0));
        } else if (messages.size() == 2) {
            drawMessageInBox(g2, x, y-10, boxWidth, messages.get(0));
            drawMessageInBox(g2, x, y+10, boxWidth, messages.get(1));
        } else if (messages.size() == 3) {
            drawMessageInBox(g2, x, y-20, boxWidth, messages.get(0));
            drawMessageInBox(g2, x, y, boxWidth, messages.get(1));
            drawMessageInBox(g2, x, y+20, boxWidth, messages.get(2));
        } else if (messages.size() == 4) {
            drawMessageInBox(g2, x, y-30, boxWidth, messages.get(0));
            drawMessageInBox(g2, x, y-10, boxWidth, messages.get(1));
            drawMessageInBox(g2, x, y+10, boxWidth, messages.get(2));
            drawMessageInBox(g2, x, y+30, boxWidth, messages.get(3));
        }
        else if (messages.size() == 5) {
            drawMessageInBox(g2, x, y - 40, boxWidth, messages.get(0)); // บรรทัดบนสุด
            drawMessageInBox(g2, x, y - 20, boxWidth, messages.get(1)); // บรรทัดถัดมา
            drawMessageInBox(g2, x, y, boxWidth, messages.get(2));     // บรรทัดกลาง
            drawMessageInBox(g2, x, y + 20, boxWidth, messages.get(3)); // บรรทัดถัดลงมา
            drawMessageInBox(g2, x, y + 40, boxWidth, messages.get(4)); // บรรทัดล่างสุด
        }
        else if (messages.size() == 6) {
            // สำหรับ 6 บรรทัด เราจะเริ่มจาก Y ที่ต่ำกว่ากลางเล็กน้อย เพื่อให้ครอบคลุมพื้นที่
            // แต่ละบรรทัดห่างกัน 20 พิกเซล
            drawMessageInBox(g2, x, y - 50, boxWidth, messages.get(0));
            drawMessageInBox(g2, x, y - 30, boxWidth, messages.get(1));
            drawMessageInBox(g2, x, y - 10, boxWidth, messages.get(2));
            drawMessageInBox(g2, x, y + 10, boxWidth, messages.get(3));
            drawMessageInBox(g2, x, y + 30, boxWidth, messages.get(4));
            drawMessageInBox(g2, x, y + 50, boxWidth, messages.get(5));
        }
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        int hintWidth = g2.getFontMetrics().stringWidth(hint);
        g2.drawString(hint, x + boxWidth - hintWidth - 10, y + boxHeight - 10);
    }

    private static void drawMessageInBox(Graphics2D g2, int x, int y, int boxWidth, String message){
        g2.setFont(new Font("Tahoma", Font.BOLD, 12));
        int textWidth = g2.getFontMetrics().stringWidth(message);
        g2.drawString(message, x + (boxWidth - textWidth) / 2, y + 60);
    }

    public static void drawBlackScreen(Graphics2D g2, int screenWidth, int screenHeight, int marginY, String message, int transparentFont) {
        int x = 0;
        int y = 0;
        g2.setColor(new Color(0, 0, 0, 255));
        g2.fillRoundRect(x, y, screenWidth, screenHeight, 0, 0);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(new Color(255, 255, 255, transparentFont));
        g2.setFont(new Font("Tahoma", Font.BOLD, 22));
        int textWidth = g2.getFontMetrics().stringWidth(message);
        g2.drawString(message, x + (screenWidth - textWidth) / 2, marginY + 60);
    }
}