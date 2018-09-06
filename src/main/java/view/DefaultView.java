package view;

import controller.Controller;
import model.Model;
import org.jetbrains.annotations.Contract;
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
    static final int SCREEN_WIDTH = getScreenSize().width;
    static final int SCREEN_HEIGHT = getScreenSize().height;

    private static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    private static final String ID_GMINY = "Id gminy";
    private static final String NAZWA_GMINY = "Nazwa gminy";
    private static final String ID_MIEJSCOWOSCI = "Id miejscowości";
    private static final String NAZWA_MIEJSCOWOSCI = "Nazwa miejscowości";
    private static final String GMINA = "Gmina";
    private static final String ID_ULICY = "Id ulicy";
    private static final String NAZWA_ULICY = "Nazwa ulicy";
    private static final String MIEJSCOWOSC = "Miejscowość";
    private static final String ID_PRZYSTANKU = "Id przystanku";
    private static final String NAZWA_PRZYSTANKU = "Nazwa przystanku";
    private static final String ULICA = "Ulica";
    private static final String TYP_PRZYSTANKU = "Typ przystanku";
    private static final String NR_LINII = "Nr linii";
    private static final String AUTOBUS_CZY_TRAMWAJ = "Autobus czy tramwaj";
    private static final String DZIENNA_CZY_NOCNA = "Dzienna czy nocna";
    private static final String LINIA_WAZNA_OD = "Linia ważna od";
    private static final String UWAGI = "Uwagi";
    private static final String ID_PODLINII = "Id podlinii";
    private static final String LINIA = "Linia";
    private static final String ID_ROZKLADU = "Id rozkładu";
    private static final String PODLINIA = "Podlinia";
    private static final String PRZYSTANEK = "Przystanek";
    private static final String NR_PORZADKOWY = "Nr porządkowy";
    private static final String ID_DNIA = "Id dnia";
    private static final String NAZWA_DNIA = "Nazwa dnia";
    private static final String DZIEN_KURSOWANIA = "Dzień";
    private static final String GODZINA = "Godzina";
    private static final String DATA_MODYFIKACJI = "Data modyfikacji";
    private static final String TRESC = "Treść";

    private static final TableViewNames gminyNames = new TableViewNames("Gminy", "gminę");
    private static final TableViewNames miejscowosciNames = new TableViewNames("Miejscowosci", "miejscowość");
    private static final TableViewNames uliceNames = new TableViewNames("Ulice", "ulicę");
    private static final TableViewNames przystankiNames = new TableViewNames("Przystanki", "przystanek");
    private static final TableViewNames linieNames = new TableViewNames("Linie", "linię");
    private static final TableViewNames podlinieNames = new TableViewNames("Podlinie", "podlinię");
    private static final TableViewNames rozkladyJazdyNames = new TableViewNames("RozkladyJazdy", "rozkład jazdy");
    private static final TableViewNames dniKursowaniaNames = new TableViewNames("DniKursowania", "dzień kursowania");

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
        final Model model = null;

        final MainPanel gminyMainPanel = createMainPanel("Gminy");

        final TableView gminyTableView = createTableView(gminyMainPanel, () -> model.getGminyRowCount(), () -> model.getGminyValueAt(), () -> controller.showGminy(), () -> controller.insertIntoGminy(), ID_GMINY, NAZWA_GMINY);
        gminyMainPanel.addTableView(gminyTableView);

        addMainPanel(gminyMainPanel);

        final MainPanel miejscowosciMainPanel = createMainPanel("Miejscowości");

        final TableView miejscowosciByGminyTableView = createTableView(miejscowosciMainPanel, gminyNames, () -> model.getMiejscowosciByGminyRowCount(), () -> model.getMiejscowosciByGminyValueAt(), () -> controller.insertIntoMiejscowosciByGminy(), "w danej gminie", () -> controller.showMiejscowosciByGminy(), ID_MIEJSCOWOSCI, NAZWA_MIEJSCOWOSCI);
        miejscowosciMainPanel.addTableView(miejscowosciByGminyTableView);

        final TableView miejscowosciTableView = createTableView(miejscowosciMainPanel, () -> model.getMiejscowosciRowCount(), () -> model.getMiejscowosciValueAt(), () -> controller.showMiejscowosci(), () -> controller.insertIntoMiejscowosci(), ID_MIEJSCOWOSCI, NAZWA_MIEJSCOWOSCI, GMINA);
        miejscowosciMainPanel.addTableView(miejscowosciTableView);

        addMainPanel(miejscowosciMainPanel);

        final MainPanel uliceMainPanel = createMainPanel("Ulice");

        final TableView uliceByMiejscowosciTableView = createTableView(uliceMainPanel, miejscowosciNames, () -> model.getUliceByMiejscowosciRowCount(), () -> model.getUliceByMiejscowosciValueAt(), () -> controller.insertIntoUliceByMiejscowosci(), "w danej miejscowości", () -> controller.showUliceByMiejscowosci(), ID_ULICY, NAZWA_ULICY);
        uliceMainPanel.addTableView(uliceByMiejscowosciTableView);

        final TableView uliceByGminyTableView = createTableView(uliceMainPanel, gminyNames, () -> model.getUliceByGminyRowCount(), () -> model.getUliceByGminyValueAt(), () -> controller.insertIntoUliceByGminy(), "w danej gminie", () -> controller.showUliceByGminy(), ID_ULICY, NAZWA_ULICY, MIEJSCOWOSC);
        uliceMainPanel.addTableView(uliceByGminyTableView);

        final TableView uliceTableView = createTableView(uliceMainPanel, () -> model.getUliceRowCount(), () -> model.getUliceValueAt(), () -> controller.showUlice(), () -> controller.insertIntoUlice(), ID_ULICY, NAZWA_ULICY, MIEJSCOWOSC, GMINA);
        uliceMainPanel.addTableView(uliceTableView);

        addMainPanel(uliceMainPanel);

        final MainPanel przystankiMainPanel = createMainPanel("Przystanki");

        final TableView przystankiByUliceTableView = createTableView(przystankiMainPanel, uliceNames, () -> model.getPrzystankiByUliceRowCount(), () -> model.getPrzystankiByUliceValueAt(), () -> controller.insertIntoPrzystankiByUlice(), "przy danej ulicy", () -> controller.showPrzystankiByUlice(), ID_PRZYSTANKU, NAZWA_PRZYSTANKU, TYP_PRZYSTANKU);
        przystankiMainPanel.addTableView(przystankiByUliceTableView);

        final TableView przystankiByMiejscowosciTableView = createTableView(przystankiMainPanel, miejscowosciNames, () -> model.getPrzystankiByMiejscowosciRowCount(), () -> model.getPrzystankiByMiejscowosciValueAt(), () -> controller.insertIntoPrzystankiByMiejscowosci(), "w danej miejscowości", () -> controller.showPrzystankiByMiejscowosci(), ID_PRZYSTANKU, NAZWA_PRZYSTANKU, ULICA, TYP_PRZYSTANKU);
        przystankiMainPanel.addTableView(przystankiByMiejscowosciTableView);

        final TableView przystankiByGminyTableView = createTableView(przystankiMainPanel, gminyNames, () -> model.getPrzystankiByGminyRowCount(), () -> model.getPrzystankiByGminyValueAt(), () -> controller.insertIntoPrzystankiByGminy(), "w danej gminie", () -> controller.showPrzystankiByGminy(), ID_PRZYSTANKU, NAZWA_PRZYSTANKU, ULICA, MIEJSCOWOSC, TYP_PRZYSTANKU);
        przystankiMainPanel.addTableView(przystankiByGminyTableView);

        final TableView przystankiTableView = createTableView(przystankiMainPanel, () -> model.getPrzystankiRowCount(), () -> model.getPrzystankiValueAt(), () -> controller.showPrzystanki(), () -> controller.insertIntoPrzystanki(), ID_PRZYSTANKU, NAZWA_PRZYSTANKU, ULICA, MIEJSCOWOSC, GMINA, TYP_PRZYSTANKU);
        przystankiMainPanel.addTableView(przystankiTableView);

        addMainPanel(przystankiMainPanel);

        final MainPanel linieMainPanel = createMainPanel("Linie");

        final TableView linieTableView = createTableView(linieMainPanel, () -> model.getLinieRowCount(), () -> model.getLinieValueAt(), () -> controller.showLinie(), () -> controller.insertIntoLinie(), NR_LINII, AUTOBUS_CZY_TRAMWAJ, DZIENNA_CZY_NOCNA, LINIA_WAZNA_OD, UWAGI);
        linieMainPanel.addTableView(linieTableView);

        addMainPanel(linieMainPanel);

        final MainPanel podlinieMainPanel = createMainPanel("Podlinie");

        final TableView podlinieByLinieTableView = createTableView(podlinieMainPanel, linieNames, () -> model.getPodlinieByLinieRowCount(), () -> model.getPodlinieByLinieValueAt(), () -> controller.insertIntoPodlinieByLinie(), "danej linii", () -> controller.showPodlinieByLinie(), ID_PODLINII, UWAGI);
        podlinieMainPanel.addTableView(podlinieByLinieTableView);

        final TableView podlinieTableView = createTableView(podlinieMainPanel, () -> model.getPodlinieRowCount(), () -> model.getPodlinieValueAt(), () -> controller.showPodlinie(), () -> controller.insertIntoPodlinie(), ID_PODLINII, LINIA, UWAGI);
        podlinieMainPanel.addTableView(podlinieTableView);

        addMainPanel(podlinieMainPanel);

        final MainPanel rozkladyJazdyMainPanel = createMainPanel("Rozkłady jazdy");

        final TableView rozkladyJazdyByPodlinieTableView = createTableView(rozkladyJazdyMainPanel, podlinieNames, () -> model.getRozkladyJazdyByPodlinieRowCount(), () -> model.getRozkladyJazdyByPodlinieValueAt(), () -> controller.insertIntoRozkladyJazdyByPodlinie(), "danej podlinii", () -> controller.showRozkladyJazdyByPodlinie(), NR_PORZADKOWY, ID_ROZKLADU, ID_PRZYSTANKU, PRZYSTANEK, ULICA, MIEJSCOWOSC, GMINA, TYP_PRZYSTANKU);
        rozkladyJazdyMainPanel.addTableView(rozkladyJazdyByPodlinieTableView);

        final TableView rozkladyJazdyByLinieTableView = createTableView(rozkladyJazdyMainPanel, linieNames, () -> model.getRozkladyJazdyByLinieRowCount(), () -> model.getRozkladyJazdyByLinieValueAt(), () -> controller.insertIntoRozkladyJazdyByLinie(), "danej linii", () -> controller.showRozkladyJazdyByLinie(), NR_PORZADKOWY, ID_ROZKLADU, PODLINIA, ID_PRZYSTANKU, PRZYSTANEK, ULICA, MIEJSCOWOSC, GMINA, TYP_PRZYSTANKU);
        rozkladyJazdyMainPanel.addTableView(rozkladyJazdyByLinieTableView);

        final TableView rozkladyJazdyByPrzystankiTableView = createTableView(rozkladyJazdyMainPanel, przystankiNames, () -> model.getRozkladyJazdyByPrzystankiRowCount(), () -> model.getRozkladyJazdyByPrzystankiValueAt(), () -> controller.insertIntoRozkladyJazdyByPrzystanki(), "na danym przystanku", () -> controller.showRozkladyJazdyByPrzystanki(), NR_PORZADKOWY, ID_ROZKLADU, PODLINIA, LINIA);
        rozkladyJazdyMainPanel.addTableView(rozkladyJazdyByPrzystankiTableView);

        final TableView rozkladyJazdyByUliceTableView = createTableView(rozkladyJazdyMainPanel, uliceNames, () -> model.getRozkladyJazdyByUliceRowCount(), () -> model.getRozkladyJazdyByUliceValueAt(), () -> controller.insertIntoRozkladyJazdyByUlice(), "przy danej ulicy", () -> controller.showRozkladyJazdyByUlice(), NR_PORZADKOWY, ID_ROZKLADU, PODLINIA, LINIA, ID_PRZYSTANKU, PRZYSTANEK, TYP_PRZYSTANKU);
        rozkladyJazdyMainPanel.addTableView(rozkladyJazdyByUliceTableView);

        final TableView rozkladyJazdyByMiejscowosciTableView = createTableView(rozkladyJazdyMainPanel, miejscowosciNames, () -> model.getRozkladyJazdyByMiejscowosciRowCount(), () -> model.getRozkladyJazdyByMiejscowosciValueAt(), () -> controller.insertIntoRozkladyJazdyByMiejscowosci(), "w danej miejscowości", () -> controller.showRozkladyJazdyByMiejscowosci(), NR_PORZADKOWY, ID_ROZKLADU, PODLINIA, LINIA, ID_PRZYSTANKU, PRZYSTANEK, ULICA, TYP_PRZYSTANKU);
        rozkladyJazdyMainPanel.addTableView(rozkladyJazdyByMiejscowosciTableView);

        final TableView rozkladyJazdyByGminyTableView = createTableView(rozkladyJazdyMainPanel, gminyNames, () -> model.getRozkladyJazdyByGminyRowCount(), () -> model.getRozkladyJazdyByGminyValueAt(), () -> controller.insertIntoRozkladyJazdyByGminy(), "w danej gminie", () -> controller.showRozkladyJazdyByGminy(), NR_PORZADKOWY, ID_ROZKLADU, PODLINIA, LINIA, ID_PRZYSTANKU, PRZYSTANEK, ULICA, MIEJSCOWOSC, TYP_PRZYSTANKU);
        rozkladyJazdyMainPanel.addTableView(rozkladyJazdyByGminyTableView);

        final TableView rozkladyJazdyTableView = createTableView(rozkladyJazdyMainPanel, () -> model.getRozkladyJazdyRowCount(), () -> model.getRozkladyJazdyValueAt(), () -> controller.showRozkladyJazdy(), () -> controller.insertIntoRozkladyJazdy(), NR_PORZADKOWY, ID_ROZKLADU, PODLINIA, LINIA, ID_PRZYSTANKU, PRZYSTANEK, ULICA, MIEJSCOWOSC, GMINA, TYP_PRZYSTANKU);
        rozkladyJazdyMainPanel.addTableView(rozkladyJazdyTableView);

        addMainPanel(rozkladyJazdyMainPanel);

        final MainPanel dniKursowaniaMainPanel = createMainPanel("Dni kursowania");

        final TableView dniKursowaniaTableView = createTableView(dniKursowaniaMainPanel, () -> model.getDniKursowaniaRowCount(), () -> model.getDniKursowaniaValueAt(), () -> controller.showDniKursowania(), () -> controller.insertIntoDniKursowania(), ID_DNIA, NAZWA_DNIA);
        dniKursowaniaMainPanel.addTableView(dniKursowaniaTableView);

        addMainPanel(dniKursowaniaMainPanel);

        final MainPanel kursyMainPanel = createMainPanel("Kursy");

        final TableView kursyByDniKursowaniaTableView = createTableView(kursyMainPanel, dniKursowaniaNames, () -> model.getKursyByDniKursowaniaRowCount(), () -> model.getKursyByDniKursowaniaValueAt(), () -> controller.insertIntoKursyByDniKursowania(), "w danym dniu kursowania", () -> controller.showKursyByDniKursowania(), PODLINIA, LINIA, ID_PRZYSTANKU, PRZYSTANEK, ULICA, MIEJSCOWOSC, GMINA, TYP_PRZYSTANKU, GODZINA, UWAGI);
        kursyMainPanel.addTableView(kursyByDniKursowaniaTableView);

        final TableView kursyByRozkladyJazdyTableView = createTableView(kursyMainPanel, rozkladyJazdyNames, () -> model.getKursyByRozkladyJazdyRowCount(), () -> model.getKursyByRozkladyJazdyValueAt(), () -> controller.insertIntoKursyByRozkladyJazdy(), "na danym rozkładzie jazdy", () -> controller.showKursyByRozkladyJazdy(), DZIEN_KURSOWANIA, GODZINA, UWAGI);
        kursyMainPanel.addTableView(kursyByRozkladyJazdyTableView);

        final TableView kursyByPodlinieTableView = createTableView(kursyMainPanel, podlinieNames, () -> model.getKursyByPodlinieRowCount(), () -> model.getKursyByPodlinieValueAt(), () -> controller.insertIntoKursyByPodlinie(), "danej podlinii", () -> controller.showKursyByPodlinie(), ID_PRZYSTANKU, PRZYSTANEK, ULICA, MIEJSCOWOSC, GMINA, TYP_PRZYSTANKU, DZIEN_KURSOWANIA, GODZINA, UWAGI);
        kursyMainPanel.addTableView(kursyByPodlinieTableView);

        final TableView kursyByLinieTableView = createTableView(kursyMainPanel, linieNames, () -> model.getKursyByLinieRowCount(), () -> model.getKursyByLinieValueAt(), () -> controller.insertIntoKursyByLinie(), "danej linii", () -> controller.showKursyByLinie(), PODLINIA, ID_PRZYSTANKU, PRZYSTANEK, ULICA, MIEJSCOWOSC, GMINA, TYP_PRZYSTANKU, DZIEN_KURSOWANIA, GODZINA, UWAGI);
        kursyMainPanel.addTableView(kursyByLinieTableView);

        final TableView kursyByPrzystankiTableView = createTableView(kursyMainPanel, przystankiNames, () -> model.getKursyByPrzystankiRowCount(), () -> model.getKursyByPrzystankiValueAt(), () -> controller.insertIntoKursyByPrzystanki(), "na danym przystanku", () -> controller.showKursyByPrzystanki(), PODLINIA, LINIA, DZIEN_KURSOWANIA, GODZINA, UWAGI);
        kursyMainPanel.addTableView(kursyByPrzystankiTableView);

        final TableView kursyByUliceTableView = createTableView(kursyMainPanel, uliceNames, () -> model.getKursyByUliceRowCount(), () -> model.getKursyByUliceValueAt(), () -> controller.insertIntoKursyByUlice(), "przy danej ulicy", () -> controller.showKursyByUlice(), PODLINIA, LINIA, ID_PRZYSTANKU, PRZYSTANEK, TYP_PRZYSTANKU, DZIEN_KURSOWANIA, GODZINA, UWAGI);
        kursyMainPanel.addTableView(kursyByUliceTableView);

        final TableView kursyByMiejscowosciTableView = createTableView(kursyMainPanel, miejscowosciNames, () -> model.getKursyByMiejscowosciRowCount(), () -> model.getKursyByMiejscowosciValueAt(), () -> controller.insertIntoKursyByMiejscowosci(), "w danej miejscowości", () -> controller.showKursyByMiejscowosci(), PODLINIA, LINIA, ID_PRZYSTANKU, PRZYSTANEK, ULICA, TYP_PRZYSTANKU, DZIEN_KURSOWANIA, GODZINA, UWAGI);
        kursyMainPanel.addTableView(kursyByMiejscowosciTableView);

        final TableView kursyByGminyTableView = createTableView(kursyMainPanel, gminyNames, () -> model.getKursyByGminyRowCount(), () -> model.getKursyByGminyValueAt(), () -> controller.insertIntoKursyByGminy(), "w danej gminie", () -> controller.showKursyByGminy(), PODLINIA, LINIA, ID_PRZYSTANKU, PRZYSTANEK, ULICA, MIEJSCOWOSC, TYP_PRZYSTANKU, DZIEN_KURSOWANIA, GODZINA, UWAGI);
        kursyMainPanel.addTableView(kursyByGminyTableView);

        final TableView kursyTableView = createTableView(kursyMainPanel, () -> model.getKursyRowCount(), () -> model.getKursyValueAt(), () -> controller.showKursy(), () -> controller.insertIntoKursy(), PODLINIA, LINIA, ID_PRZYSTANKU, PRZYSTANEK, ULICA, MIEJSCOWOSC, GMINA, TYP_PRZYSTANKU, DZIEN_KURSOWANIA, GODZINA, UWAGI);
        kursyMainPanel.addTableView(kursyTableView);

        addMainPanel(kursyMainPanel);

        final MainPanel logiMainPanel = createMainPanel("Logi");

        final TableView logiTableView = createTableView(logiMainPanel, () -> model.getLogiRowCount(), () -> model.getLogiValueAt(), () -> controller.showLogi(), () -> controller.insertIntoLogi(), DATA_MODYFIKACJI, TRESC);
        logiMainPanel.addTableView(logiTableView);

        addMainPanel(logiMainPanel);

        jTabbedPane.setFocusCycleRoot(true);

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

    @NotNull
    @Contract("_ -> new")
    private MainPanel createMainPanel(final String name) {
        return new MainPanel(jFrame, name);
    }

    private void addMainPanel(final MainPanel mainPanel) {
        jTabbedPane.add(mainPanel);
    }

    @NotNull
    @Contract("_, _, _, _, _, _ -> new")
    private static TableView createTableView(@NotNull final MainPanel ownerMainPanel, final IntSupplier intSupplier, final Supplier objectSupplier, final Runnable insertRowRunnable, final Runnable showTableViewRunnable, final String... columnNames) {
        return new TableView(ownerMainPanel, "All", intSupplier, objectSupplier, insertRowRunnable, "wszystkie", showTableViewRunnable, null, columnNames);
    }

    @NotNull
    @Contract("_, _, _, _, _, _, _, _ -> new")
    private static TableView createTableView(@NotNull final MainPanel ownerMainPanel, @NotNull final TableViewNames tableViewNames, final IntSupplier intSupplier, final Supplier objectSupplier, final Runnable insertRowRunnable, final String buttonText, final Runnable showTableViewRunnable, final String... columnNames) {
        return new TableView(ownerMainPanel, tableViewNames.tablePanelName, intSupplier, objectSupplier, insertRowRunnable, buttonText, showTableViewRunnable, tableViewNames.choosingDialogTitle, columnNames);
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
        return jLabel;
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
