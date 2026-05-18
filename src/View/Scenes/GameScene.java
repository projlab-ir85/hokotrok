package View.Scenes;

import RoadComponents.Road;
import View.MapElements.BusView;
import View.MapElements.IntersectionView;
import View.MapElements.RoadSectionView;
import View.Util.Point;
import View.Util.RoadView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameScene extends JPanel{
    private JPanel graphicsPanel;
    private double scale;
    private List<RoadSectionView> rsvs;

    public GameScene(double scale, int viewTicksPerGameTick, List<Road> roads){
        this.scale = scale;
        this.setLayout(new BorderLayout());

        BusView busView = new BusView(null, new Point(0,0));
        rsvs = new ArrayList<>();
        for(Road r : roads){
            RoadView rw = new RoadView(r, new IntersectionView(null, new Point(0,0)), new IntersectionView(null, new Point(256,256)), viewTicksPerGameTick);
            rsvs.addAll(rw.getRoadSectionViews());
        }
        RoadView rw = new RoadView(
                new Road("a",
                        null,
                        null,
                        2,
                        3,
                        Road.Way.ONEWAY,
                        0,
                        0,
                        0,
                        Road.Type.FOUT),
                new IntersectionView(null, new Point(0,0)),
                new IntersectionView(null, new Point(0,400)),
                viewTicksPerGameTick);
        rsvs.addAll(rw.getRoadSectionViews());

        graphicsPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D)g;
                g2d.scale(scale,scale);

                for(RoadSectionView rsv : rsvs){
                    rsv.draw(g2d);
                }

                busView.draw(g2d);
            }
        };

        this.add(graphicsPanel, BorderLayout.CENTER);
    }
}
