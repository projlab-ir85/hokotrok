package View;

import Control.Controller;
import RoadComponents.RoadSection;
import View.Scenes.ControlPanel;
import View.MapElements.IntersectionView;
import View.MapElements.RoadSectionView;
import View.MapElements.VehicleView;

import java.util.List;

import javax.swing.*;

public class GameView extends JFrame implements View.Interfaces.GameView {
    private Controller controller;
    private List<VehicleView> vehicleViews;
    private  List<RoadSectionView> roadSectionViews;
    private List<IntersectionView> intersectionViews;
    private ControlPanel controlPanel;
    private int tickCounter;
    private int VIEW_TICKS_PER_GAME_TICK;

    public GameView(Controller controller){
        this.controller = controller;
    }

    public void start(){

    }

    public RoadSectionView getRoadSectionView(RoadSection roadSection){
        return null;
    }

    public void loadMap(String graphicsConfigPath){

    }
}
