package View.MapElements;

import RoadComponents.RoadSection;
import Util.Observer;
import View.Interfaces.Animatable;
import View.Util.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RoadSectionView implements Observer, Animatable {
    private BufferedImage roadTile;
    private BufferedImage iceTile;
    private BufferedImage snowTile;
    private Point startCoord;
    private Point endCoord;
    private RoadSection roadSection;
    private double t;
    private int VIEW_TICKS_PER_GAME_TICK;
    private double startSnow;
    private double targetSnow;
    private double startIce;
    private double targetIce;
    private double startGravel;
    private double targetGravel;

    public RoadSectionView(RoadSection roadSection, Point startCoord, Point endCoord){
        this.roadSection = roadSection;
        this.startCoord = startCoord;
        this.endCoord = endCoord;

        //for testing
        startIce = 1;
        startSnow = 100;

        try{
            roadTile = ImageIO.read(getClass().getResourceAsStream("/images/roadComponents/roads.png")).getSubimage(2*256, 0*256, 256,256);
            iceTile = ImageIO.read(getClass().getResourceAsStream("/images/roadComponents/ice.png")).getSubimage(2*256, 0*256, 256,256);
            snowTile = ImageIO.read(getClass().getResourceAsStream("/images/roadComponents/snow.png")).getSubimage(2*256, 0*256, 256,256);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Point getStartCoord(){return startCoord;}

    public Point getEndCoord(){return endCoord;}

    public void update(){

    }

    public void tick(){

    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(roadTile, (int)startCoord.x, (int)startCoord.y, null);

        float snowAlpha = (float)startSnow / 100f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, snowAlpha));
        g2d.drawImage(snowTile, (int)startCoord.x, (int)startCoord.y, null);

        float iceAlpha = (float)startIce / 100f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, iceAlpha));
        g2d.drawImage(iceTile, (int)startCoord.x, (int)startCoord.y, null);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
