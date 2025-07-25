package ObjectInMap;

import Entity.Player;
import JavaLearn.GamePanel;
import common.EventManager;
import common.TextBoxUtil;
// import common.EventObject; // <<< ลบบรรทัดนี้ออกไป
import common.Camera;        // ยังคงต้องมี import นี้สำหรับ Camera

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;

// <<< ลบส่วน "implements EventObject" ออกจากบรรทัดนี้
public class OBJ_CarBack extends SuperObject {

    // ตัวแปรสำหรับ Cutscene (คัดลอกมาจาก Checkpoint_Start_Forest_Scene)
    private EventManager eventManagerLocal;
    private GamePanel gpLocal;
    private int countFramePerSec = 0;
    private int currentSecond = 0;
    private int FPS;
    private Camera mainCamera;
    private Camera copyCameraVar;
    private int countFPSMovingCamera = 0;

    public OBJ_CarBack() {
        name = "CarBack";
        isInteractive = true;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/car_back.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEventStep(int step, EventManager mgr) {
        // --- ส่วนเริ่มต้นและจัดการ Cutscene ---
        if (eventManagerLocal == null) {
            this.eventManagerLocal = mgr;
            this.gpLocal = mgr.getPlayer().gp;
            this.FPS = this.gpLocal.FPS;
            this.mainCamera = this.gpLocal.camera;
            this.gpLocal.ui.isPlayerUIOn = false;
            resetCutsceneTimers();
        }

        if (step == 0 || step == 1) {
            // สอง step แรกนี้เป็น Dialogue Box ปกติ ผู้เล่นต้องกด Space/Enter เอง
        }
        else if (step == 2) { // <<< เริ่ม Cutscene ส่วนที่ 1 (Fade พร้อมข้อความแรก)
            calculateSecond();
            if (isAfterSeconds(6)) {
                eventManagerLocal.advanceEventStep();
            }
        }
        else if (step == 3) { // <<< Cutscene ส่วนที่ 2 (Fade พร้อมข้อความที่สอง)
            calculateSecond();
            if (isAfterSeconds(12)) {
                eventManagerLocal.advanceEventStep();
            }
        }
        else if (step == 4) { // <<< Cutscene ส่วนที่ 3 (กล้องเลื่อน)
            calculateSecond();
            gpLocal.isCameraFollowPlayer = false;
            copyCamera(mainCamera);

            mainCamera.worldX += 4;
            countFPSMovingCamera += 1;

            if (countFPSMovingCamera > FPS*3) {
                gpLocal.isCameraFollowPlayer = true;
                gpLocal.camera = copyCameraVar;
                eventManagerLocal.advanceEventStep();
            }
        }
        else if (step == 5) { // <<< สิ้นสุด Event ทั้งหมด
            Player player = mgr.getPlayer();
            player.gp.playSE(2);
            mgr.endEvent();
            this.removeThisObjectFromMap(player);
            this.gpLocal.ui.isPlayerUIOn = true;
            this.gpLocal.isCameraFollowPlayer = true;
            this.gpLocal.camera = this.mainCamera;
        }
    }

    @Override
    public void onActionEvent(Graphics2D g2, GamePanel gp, int eventStep) {
        String hint = "Press space or enter to proceed";

        // ตรวจสอบและเริ่มต้นตัวแปร Cutscene (กรณีเรียก onActionEvent ก่อน onEventStep)
        if (gpLocal == null) {
            this.gpLocal = gp;
            this.FPS = gp.FPS;
            this.mainCamera = gp.camera;
            this.eventManagerLocal = gp.eventManager;
        }

        if (eventStep == 0) {
            TextBoxUtil.drawEventBox(
                    g2,
                    gp.screenWidth,
                    List.of(
                            "Evelyn",
                            "รู้สึกว่าการเอาตัวเองไปเสี่ยงอันตรายเป็นสิ่งที่ไม่ควรทำ"
                    ),
                    hint
            );
        } else if (eventStep == 1) {
            TextBoxUtil.drawEventBox(
                    g2,
                    gp.screenWidth,
                    List.of(
                            "Evelyn",
                            "จึงตัดใจฉีกจดหมาย",
                            "และกลับบ้าน"
                    ),
                    hint
            );
        }
        else if (eventStep == 2) {
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
                    "END",
                    transparentNum);
        } else if (eventStep == 3) {
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
                    "Thank For Playing",
                    transparentNum);
        } else if (eventStep == 4) {
            // กล้องเลื่อน, ไม่ต้องแสดงข้อความ
        }
    }

    @Override
    public void onInteract(Entity.Player player) {
        // ตรวจสอบและเริ่มต้นตัวแปร Cutscene เมื่อมีการโต้ตอบ
        if (eventManagerLocal == null) {
            this.eventManagerLocal = player.gp.eventManager;
            this.gpLocal = player.gp;
            this.FPS = this.gpLocal.FPS;
            this.mainCamera = this.gpLocal.camera;
        }

        if (player.gp.eventManager != null && !player.gp.eventManager.isEventActive()) {
            player.gp.eventManager.startEvent(this);
            this.gpLocal.ui.isPlayerUIOn = false;
        }
    }

    // --- Helper Methods (คัดลอกมาจาก Checkpoint_Start_Forest_Scene) ---
    public void resetCutsceneTimers() {
        countFramePerSec = 0;
        currentSecond = 0;
        countFPSMovingCamera = 0;
    }

    public void calculateSecond(){
        countFramePerSec++;
        this.currentSecond = countFramePerSec / FPS;
    }

    public boolean isAfterSeconds(int second){
        return currentSecond >= second;
    }

    public boolean isAfterFps(int framePerSec){
        return countFramePerSec >= framePerSec;
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
        copyCameraVar = temp;
    }

    // --- End of Helper Methods ---

    // เมธอดที่ EventManager อาจจะเรียกหา ถ้ามันทำงานกับ SuperObject โดยตรง
    // ถ้า EventManager ของคุณเรียกเมธอดเหล่านี้โดยตรงจาก SuperObject
    // หรือคุณอาจจะไม่จำเป็นต้องมีเมธอดเหล่านี้เลย ถ้า EventManager ไม่ได้เรียกหา
    public Player getPlayer() {
        return gpLocal != null ? gpLocal.player : null;
    }

    public void removeThisObjectFromMap(Player player) {
        for (int i = 0; i < player.gp.map.objects.length; i++) {
            if (player.gp.map.objects[i] == this) {
                player.gp.map.objects[i] = null;
                break;
            }
        }
    }
}