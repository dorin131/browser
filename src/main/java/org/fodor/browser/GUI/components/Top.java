package org.fodor.browser.GUI.components;

import org.fodor.browser.GUI.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Top extends JPanel {
    JTextField address;
    JButton button;

    public Top() {
        address = new JTextField();
        button = new JButton("Go");

        button.addActionListener(new Top.ButtonClickListener());
        address.setPreferredSize(new Dimension(400, 26));

        this.add(address);
        this.add(button);
        this.setMaximumSize(new Dimension(10000, 45));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            button.setText(":^)");
        }
    }
}
