package org.fodor.browser.gui.actions;

import org.fodor.browser.gui.MainWindow;
import org.fodor.browser.networking.Request;
import org.fodor.browser.shared.Renderer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoAction implements ActionListener {
    private MainWindow GUI;
    private Renderer renderer;

    public GoAction(Renderer renderer, MainWindow GUI) {
        this.GUI = GUI;
        this.renderer = renderer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var request = new Request();
        var address = GUI.getAddressField().getText();

        request.makeGetRequest(address).thenAccept(res -> {
            String source = res.body();
            GUI.getSourceTextArea().setText(source);
            renderer.render(GUI.getCanvasPanel(), source);
        });
    }
}
