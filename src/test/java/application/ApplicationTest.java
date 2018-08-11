package application;

import org.junit.Test;
import view.MainView;
import view.View;

import javax.swing.UIManager;
import java.awt.EventQueue;

public final class ApplicationTest {
    @Test
    public final void mainTest1() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            EventQueue.invokeLater(() -> {
                final View view = new MainView();
                view.showLoginWindow();
            });
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}