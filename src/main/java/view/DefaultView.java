package view;

import controller.Controller;
import model.Model;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public final class DefaultView implements View {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    static final int SCREEN_WIDTH = screenSize.width;
    static final int SCREEN_HEIGHT = screenSize.height;

    private static final class TableViewNames {
        private final String tablePanelName;
        private final Object choosingDialogTitle;

        private TableViewNames(final String tablePanelName, final Object choosingDialogTitle) {
            this.tablePanelName = tablePanelName;
            this.choosingDialogTitle = choosingDialogTitle;
        }
    }

    private final Controller controller = null;

    private final JFrame jFrame = new JFrame("Komunikacja miejska");

    private final JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);

    public DefaultView() {
        jTabbedPane.setFocusCycleRoot(true);

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

        final TableViewNames gminyNames = new TableViewNames("Gminy", "gminę");
        final TableViewNames miejscowosciNames = new TableViewNames("Miejscowosci", "miejscowość");
        final TableViewNames uliceNames = new TableViewNames("Ulice", "ulicę");
        final TableViewNames przystankiNames = new TableViewNames("Przystanki", "przystanek");
        final TableViewNames linieNames = new TableViewNames("Linie", "linię");
        final TableViewNames podlinieNames = new TableViewNames("Podlinie", "podlinię");
        final TableViewNames rozkladyJazdyNames = new TableViewNames("RozkladyJazdy", "rozkład jazdy");
        final TableViewNames dniKursowaniaNames = new TableViewNames("DniKursowania", "dzień kursowania");

        final MainPanel gminyMainPanel = addMainPanel("Gminy");
        addTableView(gminyMainPanel, () -> model.getGminyRowCount(), () -> model.getGminyValueAt(), () -> controller.showGminy(), () -> controller.insertIntoGminy(), idGminy, nazwaGminy);

        final MainPanel miejscowosciMainPanel = addMainPanel("Miejscowości");
        addTableView(miejscowosciMainPanel, gminyNames, () -> model.getMiejscowosciByGminyRowCount(), () -> model.getMiejscowosciByGminyValueAt(), () -> controller.insertIntoMiejscowosciByGminy(), "w danej gminie", () -> controller.showMiejscowosciByGminy(), idMiejscowosci, nazwaMiejscowosci);
        addTableView(miejscowosciMainPanel, () -> model.getMiejscowosciRowCount(), () -> model.getMiejscowosciValueAt(), () -> controller.showMiejscowosci(), () -> controller.insertIntoMiejscowosci(), idMiejscowosci, nazwaMiejscowosci, gmina);

        final MainPanel uliceMainPanel = addMainPanel("Ulice");
        addTableView(uliceMainPanel, miejscowosciNames, () -> model.getUliceByMiejscowosciRowCount(), () -> model.getUliceByMiejscowosciValueAt(), () -> controller.insertIntoUliceByMiejscowosci(), "w danej miejscowości", () -> controller.showUliceByMiejscowosci(), idUlicy, nazwaUlicy);
        addTableView(uliceMainPanel, gminyNames, () -> model.getUliceByGminyRowCount(), () -> model.getUliceByGminyValueAt(), () -> controller.insertIntoUliceByGminy(), "w danej gminie", () -> controller.showUliceByGminy(), idUlicy, nazwaUlicy, miejscowosc);
        addTableView(uliceMainPanel, () -> model.getUliceRowCount(), () -> model.getUliceValueAt(), () -> controller.showUlice(), () -> controller.insertIntoUlice(), idUlicy, nazwaUlicy, miejscowosc, gmina);

        final MainPanel przystankiMainPanel = addMainPanel("Przystanki");
        addTableView(przystankiMainPanel, uliceNames, () -> model.getPrzystankiByUliceRowCount(), () -> model.getPrzystankiByUliceValueAt(), () -> controller.insertIntoPrzystankiByUlice(), "przy danej ulicy", () -> controller.showPrzystankiByUlice(), idPrzystanku, nazwaPrzystanku, typPrzystanku);
        addTableView(przystankiMainPanel, miejscowosciNames, () -> model.getPrzystankiByMiejscowosciRowCount(), () -> model.getPrzystankiByMiejscowosciValueAt(), () -> controller.insertIntoPrzystankiByMiejscowosci(), "w danej miejscowości", () -> controller.showPrzystankiByMiejscowosci(), idPrzystanku, nazwaPrzystanku, ulica, typPrzystanku);
        addTableView(przystankiMainPanel, gminyNames, () -> model.getPrzystankiByGminyRowCount(), () -> model.getPrzystankiByGminyValueAt(), () -> controller.insertIntoPrzystankiByGminy(), "w danej gminie", () -> controller.showPrzystankiByGminy(), idPrzystanku, nazwaPrzystanku, ulica, miejscowosc, typPrzystanku);
        addTableView(przystankiMainPanel, () -> model.getPrzystankiRowCount(), () -> model.getPrzystankiValueAt(), () -> controller.showPrzystanki(), () -> controller.insertIntoPrzystanki(), idPrzystanku, nazwaPrzystanku, ulica, miejscowosc, gmina, typPrzystanku);

        final MainPanel linieMainPanel = addMainPanel("Linie");
        addTableView(linieMainPanel, () -> model.getLinieRowCount(), () -> model.getLinieValueAt(), () -> controller.showLinie(), () -> controller.insertIntoLinie(), nrLinii, autobusCzyTramwaj, dziennaCzyNocna, liniaWaznaOd, uwagi);

        final MainPanel podlinieMainPanel = addMainPanel("Podlinie");
        addTableView(podlinieMainPanel, linieNames, () -> model.getPodlinieByLinieRowCount(), () -> model.getPodlinieByLinieValueAt(), () -> controller.insertIntoPodlinieByLinie(), "danej linii", () -> controller.showPodlinieByLinie(), idPodlinii, uwagi);
        addTableView(podlinieMainPanel, () -> model.getPodlinieRowCount(), () -> model.getPodlinieValueAt(), () -> controller.showPodlinie(), () -> controller.insertIntoPodlinie(), idPodlinii, linia, uwagi);

        final MainPanel rozkladyJazdyMainPanel = addMainPanel("Rozkłady jazdy");
        addTableView(rozkladyJazdyMainPanel, podlinieNames, () -> model.getRozkladyJazdyByPodlinieRowCount(), () -> model.getRozkladyJazdyByPodlinieValueAt(), () -> controller.insertIntoRozkladyJazdyByPodlinie(), "danej podlinii", () -> controller.showRozkladyJazdyByPodlinie(), nrPorzadkowy, idRozkladu, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku);
        addTableView(rozkladyJazdyMainPanel, linieNames, () -> model.getRozkladyJazdyByLinieRowCount(), () -> model.getRozkladyJazdyByLinieValueAt(), () -> controller.insertIntoRozkladyJazdyByLinie(), "danej linii", () -> controller.showRozkladyJazdyByLinie(), nrPorzadkowy, idRozkladu, podlinia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku);
        addTableView(rozkladyJazdyMainPanel, przystankiNames, () -> model.getRozkladyJazdyByPrzystankiRowCount(), () -> model.getRozkladyJazdyByPrzystankiValueAt(), () -> controller.insertIntoRozkladyJazdyByPrzystanki(), "na danym przystanku", () -> controller.showRozkladyJazdyByPrzystanki(), nrPorzadkowy, idRozkladu, podlinia, linia);
        addTableView(rozkladyJazdyMainPanel, uliceNames, () -> model.getRozkladyJazdyByUliceRowCount(), () -> model.getRozkladyJazdyByUliceValueAt(), () -> controller.insertIntoRozkladyJazdyByUlice(), "przy danej ulicy", () -> controller.showRozkladyJazdyByUlice(), nrPorzadkowy, idRozkladu, podlinia, linia, idPrzystanku, przystanek, typPrzystanku);
        addTableView(rozkladyJazdyMainPanel, miejscowosciNames, () -> model.getRozkladyJazdyByMiejscowosciRowCount(), () -> model.getRozkladyJazdyByMiejscowosciValueAt(), () -> controller.insertIntoRozkladyJazdyByMiejscowosci(), "w danej miejscowości", () -> controller.showRozkladyJazdyByMiejscowosci(), nrPorzadkowy, idRozkladu, podlinia, linia, idPrzystanku, przystanek, ulica, typPrzystanku);
        addTableView(rozkladyJazdyMainPanel, gminyNames, () -> model.getRozkladyJazdyByGminyRowCount(), () -> model.getRozkladyJazdyByGminyValueAt(), () -> controller.insertIntoRozkladyJazdyByGminy(), "w danej gminie", () -> controller.showRozkladyJazdyByGminy(), nrPorzadkowy, idRozkladu, podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, typPrzystanku);
        addTableView(rozkladyJazdyMainPanel, () -> model.getRozkladyJazdyRowCount(), () -> model.getRozkladyJazdyValueAt(), () -> controller.showRozkladyJazdy(), () -> controller.insertIntoRozkladyJazdy(), nrPorzadkowy, idRozkladu, podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku);

        final MainPanel dniKursowaniaMainPanel = addMainPanel("Dni kursowania");
        addTableView(dniKursowaniaMainPanel, () -> model.getDniKursowaniaRowCount(), () -> model.getDniKursowaniaValueAt(), () -> controller.showDniKursowania(), () -> controller.insertIntoDniKursowania(), idDnia, nazwaDnia);

        final MainPanel kursyMainPanel = addMainPanel("Kursy");
        addTableView(kursyMainPanel, dniKursowaniaNames, () -> model.getKursyByDniKursowaniaRowCount(), () -> model.getKursyByDniKursowaniaValueAt(), () -> controller.insertIntoKursyByDniKursowania(), "w danym dniu kursowania", () -> controller.showKursyByDniKursowania(), podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, godzina, uwagi);
        addTableView(kursyMainPanel, rozkladyJazdyNames, () -> model.getKursyByRozkladyJazdyRowCount(), () -> model.getKursyByRozkladyJazdyValueAt(), () -> controller.insertIntoKursyByRozkladyJazdy(), "na danym rozkładzie jazdy", () -> controller.showKursyByRozkladyJazdy(), dzienKursowania, godzina, uwagi);
        addTableView(kursyMainPanel, podlinieNames, () -> model.getKursyByPodlinieRowCount(), () -> model.getKursyByPodlinieValueAt(), () -> controller.insertIntoKursyByPodlinie(), "danej podlinii", () -> controller.showKursyByPodlinie(), idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, dzienKursowania, godzina, uwagi);
        addTableView(kursyMainPanel, linieNames, () -> model.getKursyByLinieRowCount(), () -> model.getKursyByLinieValueAt(), () -> controller.insertIntoKursyByLinie(), "danej linii", () -> controller.showKursyByLinie(), podlinia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, dzienKursowania, godzina, uwagi);
        addTableView(kursyMainPanel, przystankiNames, () -> model.getKursyByPrzystankiRowCount(), () -> model.getKursyByPrzystankiValueAt(), () -> controller.insertIntoKursyByPrzystanki(), "na danym przystanku", () -> controller.showKursyByPrzystanki(), podlinia, linia, dzienKursowania, godzina, uwagi);
        addTableView(kursyMainPanel, uliceNames, () -> model.getKursyByUliceRowCount(), () -> model.getKursyByUliceValueAt(), () -> controller.insertIntoKursyByUlice(), "przy danej ulicy", () -> controller.showKursyByUlice(), podlinia, linia, idPrzystanku, przystanek, typPrzystanku, dzienKursowania, godzina, uwagi);
        addTableView(kursyMainPanel, miejscowosciNames, () -> model.getKursyByMiejscowosciRowCount(), () -> model.getKursyByMiejscowosciValueAt(), () -> controller.insertIntoKursyByMiejscowosci(), "w danej miejscowości", () -> controller.showKursyByMiejscowosci(), podlinia, linia, idPrzystanku, przystanek, ulica, typPrzystanku, dzienKursowania, godzina, uwagi);
        addTableView(kursyMainPanel, gminyNames, () -> model.getKursyByGminyRowCount(), () -> model.getKursyByGminyValueAt(), () -> controller.insertIntoKursyByGminy(), "w danej gminie", () -> controller.showKursyByGminy(), podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, typPrzystanku, dzienKursowania, godzina, uwagi);
        addTableView(kursyMainPanel, () -> model.getKursyRowCount(), () -> model.getKursyValueAt(), () -> controller.showKursy(), () -> controller.insertIntoKursy(), podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, dzienKursowania, godzina, uwagi);

        final MainPanel logiMainPanel = addMainPanel("Logi");
        addTableView(logiMainPanel, () -> model.getLogiRowCount(), () -> model.getLogiValueAt(), () -> controller.showLogi(), () -> controller.insertIntoLogi(), dataModyfikacji, tresc);

        for (int i = 0; i < jTabbedPane.getTabCount(); i++) {
            jTabbedPane.setTabComponentAt(i, createBoldJLabel(jTabbedPane.getTitleAt(i), 18, new Dimension(150, 56)));
        }
        jFrame.add(jTabbedPane);
        jFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowClosingListener(jFrame);
        jFrame.setVisible(true);
    }

    private MainPanel addMainPanel(final String name) {
        final MainPanel mainPanel = new MainPanel(name);
        jTabbedPane.add(mainPanel);
        return mainPanel;
    }

    private void addTableView(final MainPanel mainPanel, final IntSupplier intSupplier, final Supplier objectSupplier, final Runnable insertRowRunnable, final Runnable showTableViewRunnable, final String... columnNames) {
        addTableView(mainPanel, new TableViewNames("All", null), intSupplier, objectSupplier, insertRowRunnable, "wszystkie", showTableViewRunnable, columnNames);
    }

    private void addTableView(final MainPanel mainPanel, @NotNull final TableViewNames tableViewNames, final IntSupplier intSupplier, final Supplier objectSupplier, final Runnable insertRowRunnable, final String buttonText, final Runnable showTableViewRunnable, final String... columnNames) {
        new TableView(mainPanel, jFrame, tableViewNames.tablePanelName, intSupplier, objectSupplier, insertRowRunnable, buttonText, showTableViewRunnable, tableViewNames.choosingDialogTitle, columnNames);
    }

    static JLabel createBoldJLabel(final String text, final int fontSize, final Dimension dimension) {
        final JLabel jLabel = new JLabel(text, SwingConstants.CENTER);
        centerJComponent(jLabel);
        setComponentBoldFont(jLabel, fontSize);
        jLabel.setPreferredSize(dimension);
        return jLabel;
    }

    static void centerJComponent(@NotNull final JComponent jComponent) {
        jComponent.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    static void setComponentBoldFont(final Component component, final int fontSize) {
        setComponentFont(component, Font.BOLD, fontSize);
    }

    static void setComponentFont(@NotNull final Component component, final int fontStyle, final int fontSize) {
        component.setFont(new Font(component.getFont().getName(), fontStyle, fontSize));
    }

    private void addWindowClosingListener(@NotNull final Window window) {
        window.addWindowListener(new WindowAdapter() {
            @Override
            public final void windowClosing(final WindowEvent e) {
                exit();
            }
        });
    }

    private void exit() {
        if (controller != null) {
            controller.exit();
        } else {
            System.exit(0);
        }
    }

    /*private static final JLabel createJLabel() {
        final JLabel jLabel = new JLabel(text, SwingConstants.CENTER);
        centerJComponent(jLabel);
        setJComponentFont(jLabel, fontStyle, fontSize);
        return label;
    }*/

    /*@Override
    public final void showLoginDialog() {
        final JDialog jDialog = new JDialog((Frame) null, "Okno logowania");

        final JButton loginButton = createJButton("Zaloguj", e -> controller.login());

        final GroupLayout groupLayout = createGroupLayout(dialog);

        final JLabel usernameLabel = new JLabel("Nazwa użytkownika: ");
        final JLabel passwordLabel = new JLabel("Hasło: ");
        final JTextField usernameField = new JTextField("", 20);
        final JPasswordField passwordField = new JPasswordField("", 20);

        final JButton cancelButton = createJButton("Anuluj", e -> exit());

        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(usernameLabel).addComponent(passwordLabel)).addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(usernameField).addComponent(passwordField))).addGroup(groupLayout.createSequentialGroup().addComponent(loginButton).addComponent(cancelButton)));
        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup().addGroup(createBaselineGroup(groupLayout).addComponent(usernameLabel).addComponent(usernameField)).addGroup(createBaselineGroup(groupLayout).addComponent(passwordLabel).addComponent(passwordField)).addGroup(createBaselineGroup(groupLayout).addComponent(loginButton).addComponent(cancelButton)));

        addWindowClosingListener(dialog);
        initJDialog(jDialog, groupLayout, loginButton, WindowConstants.DO_NOTHING_ON_CLOSE);
    }*/

    /*private static final GroupLayout createGroupLayout() {
        final GroupLayout groupLayout = new GroupLayout(jDialog.getContentPane());
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        return groupLayout;
    }*/

    /*private static final GroupLayout.ParallelGroup createBaselineGroup() {
        return groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
    }*/
}
