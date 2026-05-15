package View;

import Control.Controller;
import RoadComponents.RoadSection;
import View.Scenes.*;
import View.UI.*;
import View.MapElements.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.*;

public class GameView extends JFrame{
    private Controller controller;
    private List<VehicleView> vehicleViews;
    private  List<RoadSectionView> roadSectionViews;
    private List<IntersectionView> intersectionViews;
    private ControlPanel controlPanel;
    private int tickCounter;
    private int VIEW_TICKS_PER_GAME_TICK;

    public GameView(Controller controller){
        this.controller = controller;

        setTitle("Hokotrok");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //custom action on close
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
                exitOperation();
            }
        });

        getContentPane().setPreferredSize(new Dimension(1024, 600));
        setResizable(false);
        pack();

        showMainMenu();

        setVisible(true);
    }

    public void start(){

    }

    public RoadSectionView getRoadSectionView(RoadSection roadSection){
        return null;
    }

    public void loadMap(String graphicsConfigPath){

    }

    public void showMainMenu(){
        setContentPane(new BasePanel(new MainMenu(this), ae -> exitOperation()));
        revalidate();
    }

    public void showNewGameMenu(){
        setContentPane(new BasePanel(new NewGameMenu(this), ae -> showMainMenu()));
        revalidate();
    }

    public void showLoadGameMenu(){
        setContentPane(new BasePanel(new LoadGameMenu(this), ae -> showMainMenu()));
        revalidate();
    }

    public void exitOperation(){
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Confirm exit",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.ERROR_MESSAGE
        );

        if(result == JOptionPane.OK_OPTION){
            controller.exit();
            System.exit(0);
        }
    }
}
