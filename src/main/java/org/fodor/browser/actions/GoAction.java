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
        String address = GUI.getAddressField().getText();
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
            ((Canvas) GUI.getCanvasPanel()).draw(res.body());
        });
    }
}
