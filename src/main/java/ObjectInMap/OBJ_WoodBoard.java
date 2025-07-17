package ObjectInMap;

import Entity.Player;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_WoodBoard extends SuperObject {
    public OBJ_WoodBoard(){

        name = "WoodBoard";
        isInteractive = true;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/wooden_board.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onInteract(Player player) {
        player.gp.playSE(1);
        player.hasKey++;
        for (int i = 0; i < player.gp.map.objects.length; i++) {
            if (player.gp.map.objects[i] == this) {
                player.gp.map.objects[i] = null;
                break;
            }
        }
        player.gp.ui.showMessage("Read me");
    }
}
