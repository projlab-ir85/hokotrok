package View.Scenes;

import View.MapElements.RoadSectionView;
import View.Util.Point;

import javax.swing.*;
import java.awt.*;

public class GameScene extends JPanel{
    private JPanel graphicsPanel;
    private double scale;

    public GameScene(double scale){
        this.scale = scale;
        this.setLayout(new BorderLayout());

        RoadSectionView roadSectionView = new RoadSectionView(null, new Point(0,0), new Point(256,256));

        graphicsPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D)g;
                g2d.scale(scale,scale);

                roadSectionView.draw(g);
            }
        };

        this.add(graphicsPanel, BorderLayout.CENTER);
    }
}
