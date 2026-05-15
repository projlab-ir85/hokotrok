package View.UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class BasePanel extends JPanel{
    private Component component;
    private ActionListener actionListener;

    public BasePanel(Component component, ActionListener actionListener){
        super(new BorderLayout());
        this.component = component;
        this.actionListener = actionListener;

        this.add(component, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(5,10,5,10));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionListener);
        topPanel.add(backButton, BorderLayout.WEST);

        this.add(topPanel, BorderLayout.NORTH);
    }
}
