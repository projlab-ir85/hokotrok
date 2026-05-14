package View.Swing;

import Control.Controller;

import javax.swing.*;

public class ControlPanelView extends JPanel implements View.Interfaces.ControlPanelView {
    private Controller controller;

    public ControlPanelView(Controller controller){
        this.controller = controller;
    }
}
