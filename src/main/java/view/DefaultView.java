package view;

import controller.Controller;
import model.Model;
import org.jetbrains.annotations.NotNull;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class DefaultView implements View {
    static final String FONT_NAME = "Tahoma";

    @Override
    public final void showLoginDialog() {
        final JDialog dialog = new JDialog((Frame) null, "Okno logowania", true);

        final Controller controller = null;

        addWindowClosingListener(dialog, controller);

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        final int screenWidth = screenSize.width;
        final int screenHeight = screenSize.height;

        final JButton loginButton = createButton("Zaloguj", e -> {
            if (controller != null) {
                controller.login();
            } else {
                dialog.dispose();

                final JFrame frame = new JFrame("Komunikacja miejska");

                final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
                tabbedPane.setDoubleBuffered(true);
                tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

                final Model model = null;

                final String idGminy = "Id gminy";
                final String nazwaGminy = "Nazwa gminy";
                final String idMiejscowosci = "Id miejscowości";
                final String nazwaMiejscowosci = "Nazwa miejscowości";
                final String gmina = "Gmina";
                final String idUlicy = "Id ulicy";
                final String nazwaUlicy = "Nazwa ulicy";
                final String miejscowosc = "Miejscowość";
                final String idPrzystanku = "Id przystanku";
                final String nazwaPrzystanku = "Nazwa przystanku";
                final String ulica = "Ulica";
                final String typPrzystanku = "Typ przystanku";
                final String nrLinii = "Nr linii";
                final String autobusCzyTramwaj = "Autobus czy tramwaj";
                final String dziennaCzyNocna = "Dzienna czy nocna";
                final String liniaWaznaOd = "Linia ważna od";
                final String dodatkoweInformacje = "Informacje";
                final String idPodlinii = "Id podlinii";
                final String linia = "Linia";
                final String idRozkladu = "Id rozkładu";
                final String podlinia = "Podlinia";
                final String przystanek = "Przystanek";
                final String nrPorzadkowy = "Nr porządkowy";
                final String idDnia = "Id dnia";
                final String nazwaDnia = "Nazwa dnia";
                final String dzienKursowania = "Dzień";
                final String godzina = "Godzina";
                final String dataModyfikacji = "Data modyfikacji";
                final String tresc = "Treść";

                final String gminy = "Gminy";
                final String miejscowosci = "Miejscowosci";
                final String ulice = "Ulice";
                final String przystanki = "Przystanki";
                final String linie = "Linie";
                final String podlinie = "Podlinie";
                final String rozkladyJazdy = "RozkladyJazdy";
                final String dniKursowania = "DniKursowania";

                /*final MainPanel MainPanel = new MainPanel("Gminy", fontName, () -> model.getMiejscowosciByGminyRowCount(), () -> model.getMiejscowosciByGminyValueAt(), () -> controller.showMiejscowosciByGminy(), idMiejscowosci, nazwaMiejscowosci);
                final JPanel cardsPanel = miejscowosciMainPanel.getCardsPanel();
                final Dimension dimension = new Dimension(270, 50);
                final JPanel buttonsPanel = miejscowosciMainPanel.getButtonsPanel();
                miejscowosciMainPanel.addTableView("MiejscowosciByGminy", fontName, cardsPanel, "w danej gminie", dimension, buttonsPanel, () -> model.getMiejscowosciByGminyRowCount(), () -> model.getMiejscowosciByGminyValueAt(), () -> controller.showMiejscowosciByGminy());*/

                /*final MainPanel gminyMainPanel = new MainPanel("Gminy", () -> model.getGminyRowCount(), () -> model.getGminyValueAt(), () -> controller.showGminy(), idGminy, nazwaGminy);

                final MainPanel miejscowosciMainPanel = new MainPanel("Miejscowości", () -> model.getMiejscowosciRowCount(), () -> model.getMiejscowosciValueAt(), () -> controller.showMiejscowosci(), idMiejscowosci, nazwaMiejscowosci, gmina);
                final MainPanel uliceMainPanel = new MainPanel("Ulice", () -> model.getUliceRowCount(), () -> model.getUliceValueAt(), () -> controller.showUlice(), idUlicy, nazwaUlicy, miejscowosc, gmina);
                final MainPanel przystankiMainPanel = new MainPanel("Przystanki", () -> model.getPrzystankiRowCount(), () -> model.getPrzystankiValueAt(), () -> controller.showPrzystanki(), idPrzystanku, nazwaPrzystanku, ulica, miejscowosc, gmina, typPrzystanku);
                final MainPanel linieMainPanel = new MainPanel("Linie", () -> model.getLinieRowCount(), () -> model.getLinieValueAt(), () -> controller.showLinie(), nrLinii, autobusCzyTramwaj, dziennaCzyNocna, liniaWaznaOd, dodatkoweInformacje);
                final MainPanel podlinieMainPanel = new MainPanel("Podlinie", () -> model.getPodlinieRowCount(), () -> model.getPodlinieValueAt(), () -> controller.showPodlinie(), idPodlinii, linia, dodatkoweInformacje);
                final MainPanel rozkladyJazdyMainPanel = new MainPanel("Rozkłady jazdy", () -> model.getRozkladyJazdyRowCount(), () -> model.getRozkladyJazdyValueAt(), () -> controller.showRozkladyJazdy(), nrPorzadkowy, idRozkladu, podlinia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku);
                final MainPanel dniKursowaniaMainPanel = new MainPanel("Dni kursowania", () -> model.getDniKursowaniaRowCount(), () -> model.getDniKursowaniaValueAt(), () -> controller.showDniKursowania(), idDnia, nazwaDnia);
                final MainPanel kursyMainPanel = new MainPanel("Kursy", () -> model.getKursyRowCount(), () -> model.getKursyValueAt(), () -> controller.showKursy(), podlinia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, dzienKursowania, godzina, dodatkoweInformacje);
                final MainPanel logiMainPanel = new MainPanel("Logi", () -> model.getLogiRowCount(), () -> model.getLogiValueAt(), () -> controller.showLogi(), dataModyfikacji, tresc);*/

                for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                    tabbedPane.setTabComponentAt(i, createLabel(tabbedPane.getTitleAt(i), new Dimension(180, 50)));
                }
                frame.add(tabbedPane);
                addWindowClosingListener(frame, controller);
                frame.setSize(screenWidth, screenHeight);
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
                frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                frame.setVisible(true);
            }
        });

        final GroupLayout groupLayout = new GroupLayout(dialog.getContentPane());
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        final JLabel usernameLabel = new JLabel("Nazwa użytkownika: ");
        final JLabel passwordLabel = new JLabel("Hasło: ");
        final JTextField usernameField = new JTextField("", 20);
        final JPasswordField passwordField = new JPasswordField("", 20);

        final JButton cancelButton = createButton("Anuluj", e -> closeWindow(dialog, controller));

        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(usernameLabel).addComponent(passwordLabel)).addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(usernameField).addComponent(passwordField))).addGroup(groupLayout.createSequentialGroup().addComponent(loginButton).addComponent(cancelButton)));
        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup().addGroup(createBaselineGroup(groupLayout).addComponent(usernameLabel).addComponent(usernameField)).addGroup(createBaselineGroup(groupLayout).addComponent(passwordLabel).addComponent(passwordField)).addGroup(createBaselineGroup(groupLayout).addComponent(loginButton).addComponent(cancelButton)));

        dialog.setLayout(groupLayout);
        dialog.pack();
        dialog.setLocation((screenWidth - dialog.getWidth()) / 2, (screenHeight - dialog.getHeight()) / 2);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        final JRootPane rootPane = dialog.getRootPane();
        rootPane.setDefaultButton(loginButton);
        rootPane.setDoubleBuffered(true);

        dialog.setVisible(true);
    }

    private static void addWindowClosingListener(@NotNull final Window window, final Controller controller) {
        window.addWindowListener(new WindowAdapter() {
            @Override
            public final void windowClosing(final WindowEvent e) {
                closeWindow(window, controller);
            }
        });
    }

    private static void closeWindow(final Window window, final Controller controller) {
        if (controller != null) {
            controller.exit();
        } else {
            window.dispose();
        }
    }

    private static GroupLayout.ParallelGroup createBaselineGroup(@NotNull final GroupLayout groupLayout) {
        return groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
    }

    static JButton createButton(final String text, final ActionListener actionListener) {
        final JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    static JLabel createLabel(final String text, final Dimension dimension) {
        final JLabel label = new JLabel(text, SwingConstants.CENTER);
        setDefaultJComponentProperties(label, dimension);
        label.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        return label;
    }

    static void setDefaultJComponentProperties(@NotNull final JComponent component, final Dimension dimension) {
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        component.setPreferredSize(dimension);
    }
}
