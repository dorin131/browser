package org.fodor.browser.gui.actions;

import org.fodor.browser.gui.MainWindow;
import org.fodor.browser.networking.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoAction implements ActionListener {
    private MainWindow GUI;

    public GoAction(MainWindow GUI) {
        this.GUI = GUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var request = new Request();
        var address = GUI.getAddressField().getText();

        request.makeGetRequest(address).thenAccept(res -> {
            GUI.getSourceTextArea().setText(res.body());
        });
    }
}
