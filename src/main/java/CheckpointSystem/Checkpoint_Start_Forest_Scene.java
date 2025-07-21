package CheckpointSystem;

import Entity.Player;
import JavaLearn.GamePanel;
import common.Camera;
import common.EventManager;
import common.TextBoxUtil;

import java.awt.*;
import java.util.List;

public class Checkpoint_Start_Forest_Scene extends Checkpoint {
    EventManager eventManager;
    GamePanel gp;
    int countFramePerSec = 0;
    int currentSecond = 0;
    int FPS;

    Camera mainCamera;
    Camera copyCamera;
    int countFPSMovingCamera = 0;

    public Checkpoint_Start_Forest_Scene(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void onPlayerEnter(Player player, EventManager eventManager) {
        eventManager.startEvent(this);
        this.eventManager = eventManager;
        this.gp = this.eventManager.getPlayer().gp;
        this.FPS = this.gp.FPS;
        mainCamera = gp.camera;
        copyCamera(mainCamera);
        this.gp.ui.isPlayerUIOn = false;
    }

    @Override
    public void onEventStep(int step, EventManager mgr) {
        if (step == 10) {
            this.gp.ui.isPlayerUIOn = true;
            mgr.endEvent();
            gp.map.checkpointManager.removeCheckpoint(this);
        }
    }

    @Override
    public void onActionEvent(Graphics2D g2, GamePanel gp, int eventStep) {
        calculateSecond();
        if (eventStep == 10) return; // use for skip cut scene

        if (!isAfterSeconds(6)) {
            int transparentNum = 0;
            if (isAfterSeconds(5)) {
                transparentNum = getFadeOutTransparentNumber(FPS * 5);
            } else {
                transparentNum = getFadeInTransparentNumber(0);
            }

            TextBoxUtil.drawBlackScreen(
                    g2,
                    gp.screenWidth,
                    gp.screenHeight,
                    200,
                    "จิตแพทย์หญิงวัย 38 ปีได้รับจดหมายปริศนา",
                    transparentNum);
        } else if (isAfterSeconds(6) && !isAfterSeconds(12)) {
            int transparentNum = 0;
            if (isAfterSeconds(11)) {
                transparentNum = getFadeOutTransparentNumber(FPS * 11);
            } else {
                transparentNum = getFadeInTransparentNumber(FPS * 6);
            }
            TextBoxUtil.drawBlackScreen(
                    g2,
                    gp.screenWidth,
                    gp.screenHeight,
                    200,
                    "ทำให้เธอต้องกลับมายังสถานที่แห่งความทรงจำอีกครั้ง",
                    transparentNum);
        } else if (eventStep == 0){
            gp.isCameraFollowPlayer = false;
            eventManager.advanceEventStep();
        } else if (eventStep == 1){
            String message = "Evelyn: สิบปี... ฉันสาบานไว้ว่าจะไม่มีวันกลับมาเหยียบที่นี่อีก";
            String hint = "Press space or enter to continue";
            TextBoxUtil.drawEventBox(g2, gp.screenWidth, List.of(message), hint);
        } else if (eventStep == 2){
            calculateSecond();

            mainCamera.worldX += 4;
            countFPSMovingCamera += 1;

            if (countFPSMovingCamera > FPS*3) {
                gp.isCameraFollowPlayer = true;
                gp.camera = copyCamera;
                eventManager.advanceEventStep();
            }
        } else if (eventStep == 3){
            String message = "Evelyn: แต่เธอ... Sophie Taylor... ";
            String message2 = "(หยิบจดหมายเก่าออกจากกระเป๋าเสื้อ)";
            String hint = "Press space or enter to continue";
            TextBoxUtil.drawEventBox(g2, gp.screenWidth, List.of(message, message2), hint);
        } else if (eventStep == 4){
            String message = "Evelyn: แค่ชื่อของเธอก็ทำให้ทุกอย่างไหลย้อนกลับมา...";
            String hint = "Press space or enter to continue";
            TextBoxUtil.drawEventBox(g2, gp.screenWidth, List.of(message), hint);
        } else if (eventStep == 5){
            String message = "Evelyn: ...ฉันแทบไม่ได้ติดต่อกับเธออีกเลยตั้งแต่วันนั้น";
            String message2 = "แล้วทำไมจดหมายที่ฉันได้รับถึงเป็นชื่อของเธอนะ...?";
            String message3 = "(Evelyn หันมองรอบตัว เงียบ ไม่มีใคร)";
            String hint = "Press space or enter to continue";
            TextBoxUtil.drawEventBox(g2, gp.screenWidth, List.of(message, message2, message3), hint);
        } else if (eventStep == 6){
            String message = "XXX: เธอกลับมา...ในที่สุด";
            String hint = "Press space or enter to continue";
            TextBoxUtil.drawEventBox(g2, gp.screenWidth, List.of(message), hint);
        } else if (eventStep == 7){
            String message = "Evelyn: หืม...? เสียงใครกัน?";
            String hint = "Press space or enter to continue";
            TextBoxUtil.drawEventBox(g2, gp.screenWidth, List.of(message), hint);
        } else if (eventStep == 8){
            String message = "Evelyn: ฉันคงคิดไปเอง...";
            String hint = "Press space or enter to continue";
            TextBoxUtil.drawEventBox(g2, gp.screenWidth, List.of(message), hint);
        } else {
            eventManager.advanceEventStep();
        }
    }

    public boolean isAfterSeconds(int second){
        return currentSecond >= second;
    }

    public boolean isAfterFps(int framePerSec){
        return countFramePerSec >= framePerSec;
    }


    public void calculateSecond(){
        countFramePerSec++;
        this.currentSecond = countFramePerSec / FPS;
    }

    public int getFadeInTransparentNumber(int initialFrameAmount){
        int transNum = 0;
        if (isAfterFps(initialFrameAmount+FPS/12*1)) transNum = 50;
        if (isAfterFps(initialFrameAmount+FPS/12*2)) transNum = 75;
        if (isAfterFps(initialFrameAmount+FPS/12*3)) transNum = 100;
        if (isAfterFps(initialFrameAmount+FPS/12*4)) transNum = 120;
        if (isAfterFps(initialFrameAmount+FPS/12*5)) transNum = 140;
        if (isAfterFps(initialFrameAmount+FPS/12*6)) transNum = 165;
        if (isAfterFps(initialFrameAmount+FPS/12*7)) transNum = 190;
        if (isAfterFps(initialFrameAmount+FPS/12*8)) transNum = 215;
        if (isAfterFps(initialFrameAmount+FPS/12*9)) transNum = 230;
        if (isAfterFps(initialFrameAmount+FPS/12*10)) transNum = 245;
        if (isAfterFps(initialFrameAmount+FPS/12*11)) transNum = 250;
        if (isAfterFps(initialFrameAmount+FPS/12*12)) transNum = 255;

        return transNum;
    }

    public int getFadeOutTransparentNumber(int initialFrameAmount){
        int transNum = 255;
        if (isAfterFps(initialFrameAmount+FPS/12*1)) transNum = 255;
        if (isAfterFps(initialFrameAmount+FPS/12*2)) transNum = 250;
        if (isAfterFps(initialFrameAmount+FPS/12*3)) transNum = 245;
        if (isAfterFps(initialFrameAmount+FPS/12*4)) transNum = 230;
        if (isAfterFps(initialFrameAmount+FPS/12*5)) transNum = 215;
        if (isAfterFps(initialFrameAmount+FPS/12*6)) transNum = 190;
        if (isAfterFps(initialFrameAmount+FPS/12*7)) transNum = 165;
        if (isAfterFps(initialFrameAmount+FPS/12*8)) transNum = 140;
        if (isAfterFps(initialFrameAmount+FPS/12*9)) transNum = 120;
        if (isAfterFps(initialFrameAmount+FPS/12*10)) transNum = 100;
        if (isAfterFps(initialFrameAmount+FPS/12*11)) transNum = 50;
        if (isAfterFps(initialFrameAmount+FPS/12*12)) transNum = 0;


        return transNum;
    }

    public void copyCamera(Camera c) {
        Camera temp = new Camera(c.worldX, c.worldY, c.screenWidth, c.screenHeight);
        copyCamera = temp;
    }
} 