package application;

import view.DefaultView;
import view.View;

import javax.swing.UIManager;
import java.awt.EventQueue;

final class Application {
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            EventQueue.invokeLater(Application::new);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private Application() {
        final View view = new DefaultView();
        view.showLoginWindow();
    }
}
