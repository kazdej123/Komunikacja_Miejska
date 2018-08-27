package view;

import controller.Controller;
import model.Model;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
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
    private final Controller controller = null;

    private final JFrame frame = new JFrame("Komunikacja miejska");

    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

    @Override
    public final void showLoginDialog() {
        final JDialog dialog = new JDialog((Frame) null, "Okno logowania");

        addWindowClosingListener(dialog);

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        final int screenWidth = screenSize.width;
        final int screenHeight = screenSize.height;

        final JButton loginButton = createButton("Zaloguj", e -> {
            if (controller != null) {
                controller.login();
            } else {
                dialog.dispose();

                tabbedPane.setFocusCycleRoot(true);
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
                final String uwagi = "Uwagi";
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

                final MainPanel gminyMainPanel = createThisOwnedMainPanel("Gminy");

                addMainPanel(gminyMainPanel, () -> model.getGminyRowCount(), () -> model.getGminyValueAt(), () -> controller.showGminy(), idGminy, nazwaGminy);

                final MainPanel miejscowosciMainPanel = createThisOwnedMainPanel("Miejscowości");
                miejscowosciMainPanel.addTableView(gminy, () -> model.getMiejscowosciByGminyRowCount(), () -> model.getMiejscowosciByGminyValueAt(), "w danej gminie", () -> controller.showMiejscowosciByGminy(), idMiejscowosci, nazwaMiejscowosci);

                addMainPanel(miejscowosciMainPanel, () -> model.getMiejscowosciRowCount(), () -> model.getMiejscowosciValueAt(), () -> controller.showMiejscowosci(), idMiejscowosci, nazwaMiejscowosci, gmina);

                final MainPanel uliceMainPanel = createThisOwnedMainPanel("Ulice");
                uliceMainPanel.addTableView(miejscowosci, () -> model.getUliceByMiejscowosciRowCount(), () -> model.getUliceByMiejscowosciValueAt(), "w danej miejscowości", () -> controller.showUliceByMiejscowosci(), idUlicy, nazwaUlicy);
                uliceMainPanel.addTableView(gminy, () -> model.getUliceByGminyRowCount(), () -> model.getUliceByGminyValueAt(), "w danej gminie", () -> controller.showUliceByGminy(), idUlicy, nazwaUlicy, miejscowosc);

                addMainPanel(uliceMainPanel, () -> model.getUliceRowCount(), () -> model.getUliceValueAt(), () -> controller.showUlice(), idUlicy, nazwaUlicy, miejscowosc, gmina);

                final MainPanel przystankiMainPanel = createThisOwnedMainPanel("Przystanki");
                przystankiMainPanel.addTableView(ulice, () -> model.getPrzystankiByUliceRowCount(), () -> model.getPrzystankiByUliceValueAt(), "przy danej ulicy", () -> controller.showPrzystankiByUlice(), idPrzystanku, nazwaPrzystanku, typPrzystanku);
                przystankiMainPanel.addTableView(miejscowosci, () -> model.getPrzystankiByMiejscowosciRowCount(), () -> model.getPrzystankiByMiejscowosciValueAt(), "w danej miejscowości", () -> controller.showPrzystankiByMiejscowosci(), idPrzystanku, nazwaPrzystanku, ulica, typPrzystanku);
                przystankiMainPanel.addTableView(gminy, () -> model.getPrzystankiByGminyRowCount(), () -> model.getPrzystankiByGminyValueAt(), "w danej gminie", () -> controller.showPrzystankiByGminy(), idPrzystanku, nazwaPrzystanku, ulica, miejscowosc, typPrzystanku);

                addMainPanel(przystankiMainPanel, () -> model.getPrzystankiRowCount(), () -> model.getPrzystankiValueAt(), () -> controller.showPrzystanki(), idPrzystanku, nazwaPrzystanku, ulica, miejscowosc, gmina, typPrzystanku);

                final MainPanel linieMainPanel = createThisOwnedMainPanel("Linie");

                addMainPanel(linieMainPanel, () -> model.getLinieRowCount(), () -> model.getLinieValueAt(), () -> controller.showLinie(), nrLinii, autobusCzyTramwaj, dziennaCzyNocna, liniaWaznaOd, uwagi);

                final MainPanel podlinieMainPanel = createThisOwnedMainPanel("Podlinie");
                podlinieMainPanel.addTableView(linie, () -> model.getPodlinieByLinieRowCount(), () -> model.getPodlinieByLinieValueAt(), "danej linii", () -> controller.showPodlinieByLinie(), idPodlinii, uwagi);

                addMainPanel(podlinieMainPanel, () -> model.getPodlinieRowCount(), () -> model.getPodlinieValueAt(), () -> controller.showPodlinie(), idPodlinii, linia, uwagi);

                final MainPanel rozkladyJazdyMainPanel = createThisOwnedMainPanel("Rozkłady jazdy");
                rozkladyJazdyMainPanel.addTableView(podlinie, () -> model.getRozkladyJazdyByPodlinieRowCount(), () -> model.getRozkladyJazdyByPodlinieValueAt(), "danej podlinii", () -> controller.showRozkladyJazdyByPodlinie(), nrPorzadkowy, idRozkladu, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku);
                rozkladyJazdyMainPanel.addTableView(linie, () -> model.getRozkladyJazdyByLinieRowCount(), () -> model.getRozkladyJazdyByLinieValueAt(), "danej linii", () -> controller.showRozkladyJazdyByLinie(), nrPorzadkowy, idRozkladu, podlinia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku);
                rozkladyJazdyMainPanel.addTableView(przystanki, () -> model.getRozkladyJazdyByPrzystankiRowCount(), () -> model.getRozkladyJazdyByPrzystankiValueAt(), "na danym przystanku", () -> controller.showRozkladyJazdyByPrzystanki(), nrPorzadkowy, idRozkladu, podlinia, linia);
                rozkladyJazdyMainPanel.addTableView(ulice, () -> model.getRozkladyJazdyByUliceRowCount(), () -> model.getRozkladyJazdyByUliceValueAt(), "przy danej ulicy", () -> controller.showRozkladyJazdyByUlice(), nrPorzadkowy, idRozkladu, podlinia, linia, idPrzystanku, przystanek, typPrzystanku);
                rozkladyJazdyMainPanel.addTableView(miejscowosci, () -> model.getRozkladyJazdyByMiejscowosciRowCount(), () -> model.getRozkladyJazdyByMiejscowosciValueAt(), "w danej miejscowości", () -> controller.showRozkladyJazdyByMiejscowosci(), nrPorzadkowy, idRozkladu, podlinia, linia, idPrzystanku, przystanek, ulica, typPrzystanku);
                rozkladyJazdyMainPanel.addTableView(gminy, () -> model.getRozkladyJazdyByGminyRowCount(), () -> model.getRozkladyJazdyByGminyValueAt(), "w danej gminie", () -> controller.showRozkladyJazdyByGminy(), nrPorzadkowy, idRozkladu, podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, typPrzystanku);

                addMainPanel(rozkladyJazdyMainPanel, () -> model.getRozkladyJazdyRowCount(), () -> model.getRozkladyJazdyValueAt(), () -> controller.showRozkladyJazdy(), nrPorzadkowy, idRozkladu, podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku);

                final MainPanel dniKursowaniaMainPanel = createThisOwnedMainPanel("Dni kursowania");

                addMainPanel(dniKursowaniaMainPanel, () -> model.getDniKursowaniaRowCount(), () -> model.getDniKursowaniaValueAt(), () -> controller.showDniKursowania(), idDnia, nazwaDnia);

                final MainPanel kursyMainPanel = createThisOwnedMainPanel("Kursy");
                kursyMainPanel.addTableView(dniKursowania, () -> model.getKursyByDniKursowaniaRowCount(), () -> model.getKursyByDniKursowaniaValueAt(), "w danym dniu kursowania", () -> controller.showKursyByDniKursowania(), podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, godzina, uwagi);
                kursyMainPanel.addTableView(rozkladyJazdy, () -> model.getKursyByRozkladyJazdyRowCount(), () -> model.getKursyByRozkladyJazdyValueAt(), "na danym rozkładzie jazdy", () -> controller.showKursyByRozkladyJazdy(), dzienKursowania, godzina, uwagi);
                kursyMainPanel.addTableView(podlinie, () -> model.getKursyByPodlinieRowCount(), () -> model.getKursyByPodlinieValueAt(), "danej podlinii", () -> controller.showKursyByPodlinie(), idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, dzienKursowania, godzina, uwagi);
                kursyMainPanel.addTableView(linie, () -> model.getKursyByLinieRowCount(), () -> model.getKursyByLinieValueAt(), "danej linii", () -> controller.showKursyByLinie(), podlinia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, dzienKursowania, godzina, uwagi);
                kursyMainPanel.addTableView(przystanki, () -> model.getKursyByPrzystankiRowCount(), () -> model.getKursyByPrzystankiValueAt(), "na danym przystanku", () -> controller.showKursyByPrzystanki(), podlinia, linia, dzienKursowania, godzina, uwagi);
                kursyMainPanel.addTableView(ulice, () -> model.getKursyByUliceRowCount(), () -> model.getKursyByUliceValueAt(), "przy danej ulicy", () -> controller.showKursyByUlice(), podlinia, linia, idPrzystanku, przystanek, typPrzystanku, dzienKursowania, godzina, uwagi);
                kursyMainPanel.addTableView(miejscowosci, () -> model.getKursyByMiejscowosciRowCount(), () -> model.getKursyByMiejscowosciValueAt(), "w danej miejscowości", () -> controller.showKursyByMiejscowosci(), podlinia, linia, idPrzystanku, przystanek, ulica, typPrzystanku, dzienKursowania, godzina, uwagi);
                kursyMainPanel.addTableView(gminy, () -> model.getKursyByGminyRowCount(), () -> model.getKursyByGminyValueAt(), "w danej gminie", () -> controller.showKursyByGminy(), podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, typPrzystanku, dzienKursowania, godzina, uwagi);

                addMainPanel(kursyMainPanel, () -> model.getKursyRowCount(), () -> model.getKursyValueAt(), () -> controller.showKursy(), podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, dzienKursowania, godzina, uwagi);

                final MainPanel logiMainPanel = createThisOwnedMainPanel("Logi");

                addMainPanel(logiMainPanel, () -> model.getLogiRowCount(), () -> model.getLogiValueAt(), () -> controller.showLogi(), dataModyfikacji, tresc);

                for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                    tabbedPane.setTabComponentAt(i, createLabel(tabbedPane.getTitleAt(i), new Dimension(150, 56), 18));
                }
                frame.add(tabbedPane);
                addWindowClosingListener(frame);
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

        final JButton cancelButton = createButton("Anuluj", e -> closeWindow(dialog));

        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(usernameLabel).addComponent(passwordLabel)).addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(usernameField).addComponent(passwordField))).addGroup(groupLayout.createSequentialGroup().addComponent(loginButton).addComponent(cancelButton)));
        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup().addGroup(createBaselineGroup(groupLayout).addComponent(usernameLabel).addComponent(usernameField)).addGroup(createBaselineGroup(groupLayout).addComponent(passwordLabel).addComponent(passwordField)).addGroup(createBaselineGroup(groupLayout).addComponent(loginButton).addComponent(cancelButton)));

        dialog.setLayout(groupLayout);
        dialog.pack();
        dialog.setLocation((screenWidth - dialog.getWidth()) / 2, (screenHeight - dialog.getHeight()) / 2);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.getRootPane().setDefaultButton(loginButton);
        dialog.setVisible(true);
    }

    private void addWindowClosingListener(@NotNull final Window window) {
        window.addWindowListener(new WindowAdapter() {
            @Override
            public final void windowClosing(final WindowEvent e) {
                closeWindow(window);
            }
        });
    }

    private void closeWindow(final Window window) {
        if (controller != null) {
            controller.exit();
        } else {
            window.dispose();
        }
    }

    static JButton createButton(final String text, final ActionListener actionListener) {
        final JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    @NotNull
    @Contract("_ -> new")
    private MainPanel createThisOwnedMainPanel(final String name) {
        return new MainPanel(name, frame);
    }

    private void addMainPanel(@NotNull final MainPanel mainPanel, final MainPanel.RowCountGetter rowCountGetter, final MainPanel.ValueGetter valueGetter, final MainPanel.TableViewShower tableViewShower, final String... columnNames) {
        mainPanel.addTableView(MainPanel.ALL, rowCountGetter, valueGetter, "wszystkie", tableViewShower, null, columnNames);
        tabbedPane.add(mainPanel);
    }

    static JLabel createLabel(final String text, final Dimension dimension, final int fontSize) {
        final JLabel label = new JLabel(text, SwingConstants.CENTER);
        centerJComponent(label);
        setJComponentBoldFont(label, fontSize);
        label.setPreferredSize(dimension);
        return label;
    }

    static void centerJComponent(@NotNull final JComponent jComponent) {
        jComponent.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    static void setJComponentBoldFont(final JComponent jComponent, final int size) {
        setJComponentFont(jComponent, Font.BOLD, size);
    }

    static void setJComponentFont(@NotNull final JComponent jComponent, final int style, final int size) {
        jComponent.setFont(new Font(jComponent.getFont().getName(), style, size));
    }

    private static GroupLayout.ParallelGroup createBaselineGroup(@NotNull final GroupLayout groupLayout) {
        return groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
    }
}
