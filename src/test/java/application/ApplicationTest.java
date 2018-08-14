package application;

import org.junit.Test;
import view.DefaultView;
import view.View;

import javax.swing.UIManager;
import java.awt.EventQueue;

public final class ApplicationTest {
    @Test
    public final void mainTest1() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            EventQueue.invokeLater(() -> {
                final View view = new DefaultView();
                view.showLoginDialog();
            });
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}