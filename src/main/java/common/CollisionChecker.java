package common;

import Entity.Entity;
import JavaLearn.GamePanel;
import Map.Map;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;

		switch (entity.direction) {
			case "up" -> {
				entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
				tileNum1 = gp.map.mapTileNum[entityLeftCol][entityTopRow];
				tileNum2 = gp.map.mapTileNum[entityRightCol][entityTopRow];
				if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
			}
			case "down" -> {
				entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
				tileNum1 = gp.map.mapTileNum[entityLeftCol][entityBottomRow];
				tileNum2 = gp.map.mapTileNum[entityRightCol][entityBottomRow];
				if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
			}
			case "left" -> {
				entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
				tileNum1 = gp.map.mapTileNum[entityLeftCol][entityTopRow];
				tileNum2 = gp.map.mapTileNum[entityLeftCol][entityBottomRow];
				if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
			}
			case "right" -> {
				entityRightCol = (entityRightWorldX - entity.speed) / gp.tileSize;
				tileNum1 = gp.map.mapTileNum[entityRightCol][entityTopRow];
				tileNum2 = gp.map.mapTileNum[entityRightCol][entityBottomRow];
				if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
			}
		}
	}

	public int checkObject(Entity entity, boolean isPlayer, Map map) {
		
		int index = 999;
		
		for(int i = 0; i < map.objects.length; i++) {
			
			if(map.objects[i] != null) {
				
				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				// Get the object's solid area position
				map.objects[i].solidArea.x = map.objects[i].worldX + map.objects[i].solidArea.x;
				map.objects[i].solidArea.y = map.objects[i].worldY + map.objects[i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(map.objects[i].solidArea)) {
						if(map.objects[i].collision) {
							entity.collisionOn = true;
						}
						if(isPlayer) {
							index = i;
						}
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(map.objects[i].solidArea)) {
						if(map.objects[i].collision) {
							entity.collisionOn = true;
						}
						if(isPlayer) {
							index = i;
						}
					}
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(map.objects[i].solidArea)) {
						if(map.objects[i].collision) {
							entity.collisionOn = true;
						}
						if(isPlayer) {
							index = i;
						}
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(map.objects[i].solidArea)) {
						if(map.objects[i].collision) {
							entity.collisionOn = true;
						}
						if(isPlayer) {
							index = i;
						}
					break;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				map.objects[i].solidArea.x = map.objects[i].solidAreaDefaultX;
				map.objects[i].solidArea.y = map.objects[i].solidAreaDefaultY;
			}
		}		
		return index;
	}
}
