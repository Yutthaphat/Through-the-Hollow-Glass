package ObjectInMap;

import Entity.Player;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends SuperObject{
	public OBJ_Boots(){
		
		name = "Boots";
		isInteractive = true;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
			
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
		player.gp.ui.showMessage("Speed up!");
	}
}
