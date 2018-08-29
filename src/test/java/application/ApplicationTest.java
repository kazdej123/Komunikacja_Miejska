package application;

import org.junit.Test;
import view.DefaultView;

import javax.swing.UIManager;
import java.awt.EventQueue;

public final class ApplicationTest {
    @Test
    public final void mainTest1() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            EventQueue.invokeLater(DefaultView::new);
        } catch (final Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}