package View.MapElements;

import RoadComponents.RoadSection;
import Util.Observer;
import View.Interfaces.Animatable;
import View.Util.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RoadSectionView implements Observer, Animatable {
    private Point startCoord;
    private Point endCoord;
    private RoadSection roadSection;
    private double t;
    private int VIEW_TICKS_PER_GAME_TICK;
    private double currentSnow;
    private double targetSnow;
    private double currentIce;
    private double targetIce;
    private double currentRock;
    private double targetRock;

    public static final int LANE_WIDTH = 48;
    private static final Color ASPHALT = new Color(80, 80, 80);
    private static final Color LANE_MARKING = new Color(200, 200, 200);
    private static final Color SNOW_COLOR = new Color(240, 240, 255, 200);
    private static final Color ICE_COLOR = new Color(150, 200, 255, 150);
    private static final Color ROCK_COLOR = new Color(180, 140, 100, 130);

    public RoadSectionView(RoadSection roadSection, Point startCoord, Point endCoord, int viewTicksPerGameTick){
        this.roadSection = roadSection;
        this.startCoord = startCoord;
        this.endCoord = endCoord;
        this.VIEW_TICKS_PER_GAME_TICK = viewTicksPerGameTick;

        if (roadSection != null) {
            currentSnow = roadSection.getSnow();
            currentIce = roadSection.getIce();
            currentRock = roadSection.getRock();
        }

        //for testing
        currentIce = 1;
        targetIce = 1;
        currentSnow = 1;
        targetSnow = 1;
        currentRock = 1;
        targetRock = 1;
    }

    public Point getStartCoord(){return startCoord;}

    public Point getEndCoord(){return endCoord;}

    private boolean isHorizontal() {
        return Math.abs(endCoord.x - startCoord.x) > Math.abs(endCoord.y - startCoord.y);
    }

    private int getLaneIndex() {
        if (roadSection == null) return 0;
        return roadSection.getSublaneIndex();
    }

    @Override
    public void update(){
        if (roadSection == null) return;
        targetSnow = roadSection.getSnow();
        targetIce = roadSection.getIce();
        targetRock = roadSection.getRock();
        t = 0;
    }

    @Override
    public void tick(){
        if (t >= 1.0) return;
        t += 1.0 / VIEW_TICKS_PER_GAME_TICK;
        if (t > 1.0) t = 1.0;
        currentSnow = currentSnow + t * (targetSnow - currentSnow);
        currentIce = currentIce + t * (targetIce - currentIce);
        currentRock = currentRock + t * (targetRock - currentRock);
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        int x = (int) startCoord.x;
        int y = (int) startCoord.y;

        int rectX, rectY, rectW, rectH;

        if (isHorizontal()) {
            int sectionWidth = (int)(endCoord.x - startCoord.x);
            rectX = x;
            rectY = y;
            rectW = sectionWidth;
            rectH = LANE_WIDTH;
        } else {
            int sectionHeight = (int)(endCoord.y - startCoord.y);
            rectX = x;
            rectY = y;
            rectW = LANE_WIDTH;
            rectH = sectionHeight;
        }

        // asphalt
        g2d.setColor(ASPHALT);
        g2d.fillRect(rectX, rectY, rectW, rectH);

        // lane markings
        g2d.setColor(LANE_MARKING);
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{10, 10}, 0));
        if (isHorizontal()) {
            g2d.drawLine(rectX, rectY, rectX + rectW, rectY);
            //g2d.drawLine(rectX, rectY + LANE_WIDTH - 1, rectX + rectW, rectY + LANE_WIDTH - 1);
        } else {
            g2d.drawLine(rectX, rectY, rectX, rectY + rectH);
            //g2d.drawLine(rectX + LANE_WIDTH - 1, rectY, rectX + LANE_WIDTH - 1, rectY + rectH);
        }
        g2d.setStroke(new BasicStroke(1));

        // rock layer
        if (currentRock > 0) {
            float rockAlpha = (float) currentRock / 100f * 0.5f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, rockAlpha));
            g2d.setColor(ROCK_COLOR);
            g2d.fillRect(rectX, rectY, rectW, rectH);
        }

        // ice layer
        if (currentIce > 0) {
            float iceAlpha = (float) currentIce / 100f * 0.6f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, iceAlpha));
            g2d.setColor(ICE_COLOR);
            g2d.fillRect(rectX, rectY, rectW, rectH);
        }

        // snow layer
        if (currentSnow > 0) {
            float snowAlpha = (float) currentSnow / 100f * 0.85f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, snowAlpha));
            g2d.setColor(SNOW_COLOR);
            g2d.fillRect(rectX, rectY, rectW, rectH);
        }

        // reset composite
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
