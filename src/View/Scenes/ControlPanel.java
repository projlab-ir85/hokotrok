package View.Scenes;

import Control.Controller;

import javax.swing.*;

public class ControlPanel extends JPanel implements View.Interfaces.ControlPanelView {
    private Controller controller;

    public ControlPanel(Controller controller){
        this.controller = controller;
    }
}
