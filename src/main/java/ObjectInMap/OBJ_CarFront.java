package ObjectInMap;

import javax.imageio.ImageIO;
import java.io.IOException;
import Entity.Player;

public class OBJ_CarFront extends SuperObject{
    public OBJ_CarFront(){

        name = "CarFront";
        isInteractive = true;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/car_front.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onInteract(Player player) {
        player.gp.playSE(2);
        player.speed += 1;
        for (int i = 0; i < player.gp.map.objects.length; i++) {
            if (player.gp.map.objects[i] == this) {
                player.gp.map.objects[i] = null;
                break;
            }
        }
        player.gp.ui.showMessage("Go Home");
    }
}
