package Object;

import Entity.Player;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_WoodBody extends SuperObject {
    public OBJ_WoodBody(){

        name = "WoodBody";
        isInteractive = true;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/wood_body.png"));

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
        player.gp.ui.showMessage("Block");
    }
}

