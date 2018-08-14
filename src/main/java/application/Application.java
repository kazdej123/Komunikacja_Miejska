package application;

import view.DefaultView;
import view.View;

import javax.swing.UIManager;
import java.awt.EventQueue;

final class Application {
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            EventQueue.invokeLater(() -> {
                final View view = new DefaultView();
                view.showLoginDialog();
            });
        } catch (final Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
