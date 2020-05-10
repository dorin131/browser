package org.fodor.browser.GUI.actions;

import org.fodor.browser.Context;
import org.fodor.browser.networking.Request;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class GoAction implements ActionListener {
    private Context browserContext;
    private JTextField addressField;

    public GoAction(Context browserContext, JTextField addressField) {
        this.addressField = addressField;
        this.browserContext = browserContext;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String address = addressField.getText();
        Request request = new Request();
        CompletableFuture<HttpResponse<String>> response = new CompletableFuture<>();
        try {
            response = request.http(Request.Method.GET, address);
        } catch (URISyntaxException ex) {
            JOptionPane.showMessageDialog(null, "Invalid address: " + address);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getLocalizedMessage());
        }
        response.thenAccept(res -> {
            this.browserContext.getCanvas().draw(res.body());
        });
    }
}
