package ObjectInMap;

import Entity.Player;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_MetalfenceRight extends SuperObject {
	public OBJ_MetalfenceRight(){
			
		name = "MetalfenceRight";
		isInteractive = true;
		
		try {

			image = ImageIO.read(getClass().getResourceAsStream("/objects/metalfence_right.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}

	@Override
	public void onInteract(Player player) {
		if (player.hasKey > 0) {
			player.gp.playSE(3);
			for (int i = 0; i < player.gp.map.objects.length; i++) {
				if (player.gp.map.objects[i] == this) {
					player.gp.map.objects[i] = null;
					break;
				}
			}
			player.hasKey--;
			player.gp.ui.showMessage("You opened the door!");
		} else {
			player.gp.ui.showMessage("You need a key!");
		}
	}
}
