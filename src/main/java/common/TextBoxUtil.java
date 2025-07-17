package common;

import java.awt.*;

public class TextBoxUtil {
    public static void drawEventBox(Graphics2D g2, int screenWidth, String message, String hint) {
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
        g2.setFont(new Font("Arial", Font.BOLD, 22));
        int textWidth = g2.getFontMetrics().stringWidth(message);
        g2.drawString(message, x + (boxWidth - textWidth) / 2, y + 60);
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        int hintWidth = g2.getFontMetrics().stringWidth(hint);
        g2.drawString(hint, x + boxWidth - hintWidth - 10, y + boxHeight - 10);
    }
} 