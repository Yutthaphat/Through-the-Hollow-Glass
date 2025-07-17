package ObjectInMap;

import Entity.Player;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject {
	public OBJ_Chest(){
				
		name = "Chest";
		isInteractive = true;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onInteract(Player player) {
		player.gp.ui.gameFinished = true;
		player.gp.stopMusic();
		player.gp.playSE(4);
	}
}


