package org.fodor.browser.actions;

import org.fodor.browser.GUI.MainWindow;
import org.fodor.browser.GUI.components.Canvas;
import org.fodor.browser.networking.Request;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

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
