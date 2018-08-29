package application;

import view.DefaultView;

import javax.swing.UIManager;
import java.awt.EventQueue;

final class Application {
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            EventQueue.invokeLater(DefaultView::new);
        } catch (final Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
