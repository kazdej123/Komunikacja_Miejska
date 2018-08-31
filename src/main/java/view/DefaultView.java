package view;

import controller.Controller;
import model.Model;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
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
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import static view.MainPanel.TableViewNames;

public final class DefaultView implements View {
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;

    private final Controller controller = null;

    private final JFrame frame = new JFrame("Komunikacja miejska");

    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

    public DefaultView() {
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

        final TableViewNames gminyNames = new TableViewNames("Gminy", "gminę");
        final TableViewNames miejscowosciNames = new TableViewNames("Miejscowosci", "miejscowość");
        final TableViewNames uliceNames = new TableViewNames("Ulice", "ulicę");
        final TableViewNames przystankiNames = new TableViewNames("Przystanki", "przystanek");
        final TableViewNames linieNames = new TableViewNames("Linie", "linię");
        final TableViewNames podlinieNames = new TableViewNames("Podlinie", "podlinię");
        final TableViewNames rozkladyJazdyNames = new TableViewNames("RozkladyJazdy", "rozkład jazdy");
        final TableViewNames dniKursowaniaNames = new TableViewNames("DniKursowania", "dzień kursowania");

        final MainPanel gminyMainPanel = createThisOwnedMainPanel("Gminy");
        addMainPanel(gminyMainPanel, () -> model.getGminyRowCount(), () -> model.getGminyValueAt(), () -> controller.showGminy(), () -> controller.insertIntoGminy(), idGminy, nazwaGminy);

        final MainPanel miejscowosciMainPanel = createThisOwnedMainPanel("Miejscowości");
        miejscowosciMainPanel.addTableView(gminyNames, () -> model.getMiejscowosciByGminyRowCount(), () -> model.getMiejscowosciByGminyValueAt(), () -> controller.insertIntoMiejscowosciByGminy(), "w danej gminie", () -> controller.showMiejscowosciByGminy(), idMiejscowosci, nazwaMiejscowosci);
        addMainPanel(miejscowosciMainPanel, () -> model.getMiejscowosciRowCount(), () -> model.getMiejscowosciValueAt(), () -> controller.showMiejscowosci(), () -> controller.insertIntoMiejscowosci(), idMiejscowosci, nazwaMiejscowosci, gmina);

        final MainPanel uliceMainPanel = createThisOwnedMainPanel("Ulice");
        uliceMainPanel.addTableView(miejscowosciNames, () -> model.getUliceByMiejscowosciRowCount(), () -> model.getUliceByMiejscowosciValueAt(), () -> controller.insertIntoUliceByMiejscowosci(), "w danej miejscowości", () -> controller.showUliceByMiejscowosci(), idUlicy, nazwaUlicy);
        uliceMainPanel.addTableView(gminyNames, () -> model.getUliceByGminyRowCount(), () -> model.getUliceByGminyValueAt(), () -> controller.insertIntoUliceByGminy(), "w danej gminie", () -> controller.showUliceByGminy(), idUlicy, nazwaUlicy, miejscowosc);
        addMainPanel(uliceMainPanel, () -> model.getUliceRowCount(), () -> model.getUliceValueAt(), () -> controller.showUlice(), () -> controller.insertIntoUlice(), idUlicy, nazwaUlicy, miejscowosc, gmina);

        final MainPanel przystankiMainPanel = createThisOwnedMainPanel("Przystanki");
        przystankiMainPanel.addTableView(uliceNames, () -> model.getPrzystankiByUliceRowCount(), () -> model.getPrzystankiByUliceValueAt(), () -> controller.insertIntoPrzystankiByUlice(), "przy danej ulicy", () -> controller.showPrzystankiByUlice(), idPrzystanku, nazwaPrzystanku, typPrzystanku);
        przystankiMainPanel.addTableView(miejscowosciNames, () -> model.getPrzystankiByMiejscowosciRowCount(), () -> model.getPrzystankiByMiejscowosciValueAt(), () -> controller.insertIntoPrzystankiByMiejscowosci(), "w danej miejscowości", () -> controller.showPrzystankiByMiejscowosci(), idPrzystanku, nazwaPrzystanku, ulica, typPrzystanku);
        przystankiMainPanel.addTableView(gminyNames, () -> model.getPrzystankiByGminyRowCount(), () -> model.getPrzystankiByGminyValueAt(), () -> controller.insertIntoPrzystankiByGminy(), "w danej gminie", () -> controller.showPrzystankiByGminy(), idPrzystanku, nazwaPrzystanku, ulica, miejscowosc, typPrzystanku);
        addMainPanel(przystankiMainPanel, () -> model.getPrzystankiRowCount(), () -> model.getPrzystankiValueAt(), () -> controller.showPrzystanki(), () -> controller.insertIntoPrzystanki(), idPrzystanku, nazwaPrzystanku, ulica, miejscowosc, gmina, typPrzystanku);

        final MainPanel linieMainPanel = createThisOwnedMainPanel("Linie");
        addMainPanel(linieMainPanel, () -> model.getLinieRowCount(), () -> model.getLinieValueAt(), () -> controller.showLinie(), () -> controller.insertIntoLinie(), nrLinii, autobusCzyTramwaj, dziennaCzyNocna, liniaWaznaOd, uwagi);

        final MainPanel podlinieMainPanel = createThisOwnedMainPanel("Podlinie");
        podlinieMainPanel.addTableView(linieNames, () -> model.getPodlinieByLinieRowCount(), () -> model.getPodlinieByLinieValueAt(), () -> controller.insertIntoPodlinieByLinie(), "danej linii", () -> controller.showPodlinieByLinie(), idPodlinii, uwagi);
        addMainPanel(podlinieMainPanel, () -> model.getPodlinieRowCount(), () -> model.getPodlinieValueAt(), () -> controller.showPodlinie(), () -> controller.insertIntoPodlinie(), idPodlinii, linia, uwagi);

        final MainPanel rozkladyJazdyMainPanel = createThisOwnedMainPanel("Rozkłady jazdy");
        rozkladyJazdyMainPanel.addTableView(podlinieNames, () -> model.getRozkladyJazdyByPodlinieRowCount(), () -> model.getRozkladyJazdyByPodlinieValueAt(), () -> controller.insertIntoRozkladyJazdyByPodlinie(), "danej podlinii", () -> controller.showRozkladyJazdyByPodlinie(), nrPorzadkowy, idRozkladu, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku);
        rozkladyJazdyMainPanel.addTableView(linieNames, () -> model.getRozkladyJazdyByLinieRowCount(), () -> model.getRozkladyJazdyByLinieValueAt(), () -> controller.insertIntoRozkladyJazdyByLinie(), "danej linii", () -> controller.showRozkladyJazdyByLinie(), nrPorzadkowy, idRozkladu, podlinia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku);
        rozkladyJazdyMainPanel.addTableView(przystankiNames, () -> model.getRozkladyJazdyByPrzystankiRowCount(), () -> model.getRozkladyJazdyByPrzystankiValueAt(), () -> controller.insertIntoRozkladyJazdyByPrzystanki(), "na danym przystanku", () -> controller.showRozkladyJazdyByPrzystanki(), nrPorzadkowy, idRozkladu, podlinia, linia);
        rozkladyJazdyMainPanel.addTableView(uliceNames, () -> model.getRozkladyJazdyByUliceRowCount(), () -> model.getRozkladyJazdyByUliceValueAt(), () -> controller.insertIntoRozkladyJazdyByUlice(), "przy danej ulicy", () -> controller.showRozkladyJazdyByUlice(), nrPorzadkowy, idRozkladu, podlinia, linia, idPrzystanku, przystanek, typPrzystanku);
        rozkladyJazdyMainPanel.addTableView(miejscowosciNames, () -> model.getRozkladyJazdyByMiejscowosciRowCount(), () -> model.getRozkladyJazdyByMiejscowosciValueAt(), () -> controller.insertIntoRozkladyJazdyByMiejscowosci(), "w danej miejscowości", () -> controller.showRozkladyJazdyByMiejscowosci(), nrPorzadkowy, idRozkladu, podlinia, linia, idPrzystanku, przystanek, ulica, typPrzystanku);
        rozkladyJazdyMainPanel.addTableView(gminyNames, () -> model.getRozkladyJazdyByGminyRowCount(), () -> model.getRozkladyJazdyByGminyValueAt(), () -> controller.insertIntoRozkladyJazdyByGminy(), "w danej gminie", () -> controller.showRozkladyJazdyByGminy(), nrPorzadkowy, idRozkladu, podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, typPrzystanku);
        addMainPanel(rozkladyJazdyMainPanel, () -> model.getRozkladyJazdyRowCount(), () -> model.getRozkladyJazdyValueAt(), () -> controller.showRozkladyJazdy(), () -> controller.insertIntoRozkladyJazdy(), nrPorzadkowy, idRozkladu, podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku);

        final MainPanel dniKursowaniaMainPanel = createThisOwnedMainPanel("Dni kursowania");
        addMainPanel(dniKursowaniaMainPanel, () -> model.getDniKursowaniaRowCount(), () -> model.getDniKursowaniaValueAt(), () -> controller.showDniKursowania(), () -> controller.insertIntoDniKursowania(), idDnia, nazwaDnia);

        final MainPanel kursyMainPanel = createThisOwnedMainPanel("Kursy");
        kursyMainPanel.addTableView(dniKursowaniaNames, () -> model.getKursyByDniKursowaniaRowCount(), () -> model.getKursyByDniKursowaniaValueAt(), () -> controller.insertIntoKursyByDniKursowania(), "w danym dniu kursowania", () -> controller.showKursyByDniKursowania(), podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, godzina, uwagi);
        kursyMainPanel.addTableView(rozkladyJazdyNames, () -> model.getKursyByRozkladyJazdyRowCount(), () -> model.getKursyByRozkladyJazdyValueAt(), () -> controller.insertIntoKursyByRozkladyJazdy(), "na danym rozkładzie jazdy", () -> controller.showKursyByRozkladyJazdy(), dzienKursowania, godzina, uwagi);
        kursyMainPanel.addTableView(podlinieNames, () -> model.getKursyByPodlinieRowCount(), () -> model.getKursyByPodlinieValueAt(), () -> controller.insertIntoKursyByPodlinie(), "danej podlinii", () -> controller.showKursyByPodlinie(), idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, dzienKursowania, godzina, uwagi);
        kursyMainPanel.addTableView(linieNames, () -> model.getKursyByLinieRowCount(), () -> model.getKursyByLinieValueAt(), () -> controller.insertIntoKursyByLinie(), "danej linii", () -> controller.showKursyByLinie(), podlinia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, dzienKursowania, godzina, uwagi);
        kursyMainPanel.addTableView(przystankiNames, () -> model.getKursyByPrzystankiRowCount(), () -> model.getKursyByPrzystankiValueAt(), () -> controller.insertIntoKursyByPrzystanki(), "na danym przystanku", () -> controller.showKursyByPrzystanki(), podlinia, linia, dzienKursowania, godzina, uwagi);
        kursyMainPanel.addTableView(uliceNames, () -> model.getKursyByUliceRowCount(), () -> model.getKursyByUliceValueAt(), () -> controller.insertIntoKursyByUlice(), "przy danej ulicy", () -> controller.showKursyByUlice(), podlinia, linia, idPrzystanku, przystanek, typPrzystanku, dzienKursowania, godzina, uwagi);
        kursyMainPanel.addTableView(miejscowosciNames, () -> model.getKursyByMiejscowosciRowCount(), () -> model.getKursyByMiejscowosciValueAt(), () -> controller.insertIntoKursyByMiejscowosci(), "w danej miejscowości", () -> controller.showKursyByMiejscowosci(), podlinia, linia, idPrzystanku, przystanek, ulica, typPrzystanku, dzienKursowania, godzina, uwagi);
        kursyMainPanel.addTableView(gminyNames, () -> model.getKursyByGminyRowCount(), () -> model.getKursyByGminyValueAt(), () -> controller.insertIntoKursyByGminy(), "w danej gminie", () -> controller.showKursyByGminy(), podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, typPrzystanku, dzienKursowania, godzina, uwagi);
        addMainPanel(kursyMainPanel, () -> model.getKursyRowCount(), () -> model.getKursyValueAt(), () -> controller.showKursy(), () -> controller.insertIntoKursy(), podlinia, linia, idPrzystanku, przystanek, ulica, miejscowosc, gmina, typPrzystanku, dzienKursowania, godzina, uwagi);

        final MainPanel logiMainPanel = createThisOwnedMainPanel("Logi");
        addMainPanel(logiMainPanel, () -> model.getLogiRowCount(), () -> model.getLogiValueAt(), () -> controller.showLogi(), () -> controller.insertIntoLogi(), dataModyfikacji, tresc);

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setTabComponentAt(i, createLabel(tabbedPane.getTitleAt(i), new Dimension(150, 56), 18));
        }
        frame.add(tabbedPane);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowClosingListener(frame);
        frame.setVisible(true);
    }

    @NotNull
    @Contract("_ -> new")
    private MainPanel createThisOwnedMainPanel(final String name) {
        return new MainPanel(name, frame);
    }

    private void addMainPanel(final MainPanel mainPanel, final IntSupplier intSupplier, final Supplier<Object> objectSupplier, final Runnable insertRowRunnable, final Runnable showTableViewRunnable, final String... columnNames) {
        mainPanel.addTableView("All", intSupplier, objectSupplier, insertRowRunnable, "wszystkie", showTableViewRunnable, () -> {}, columnNames);
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

    static JButton createButton(final String text, final ActionListener actionListener) {
        final JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    /*@Override
    public final void showLoginDialog() {
        final JDialog dialog = new JDialog((Frame) null, "Okno logowania");

        final JButton loginButton = createButton("Zaloguj", e -> controller.login());

        final GroupLayout groupLayout = createGroupLayout(dialog);

        final JLabel usernameLabel = new JLabel("Nazwa użytkownika: ");
        final JLabel passwordLabel = new JLabel("Hasło: ");
        final JTextField usernameField = new JTextField("", 20);
        final JPasswordField passwordField = new JPasswordField("", 20);

        final JButton cancelButton = createButton("Anuluj", e -> exit());

        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(usernameLabel).addComponent(passwordLabel)).addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(usernameField).addComponent(passwordField))).addGroup(groupLayout.createSequentialGroup().addComponent(loginButton).addComponent(cancelButton)));
        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup().addGroup(createBaselineGroup(groupLayout).addComponent(usernameLabel).addComponent(usernameField)).addGroup(createBaselineGroup(groupLayout).addComponent(passwordLabel).addComponent(passwordField)).addGroup(createBaselineGroup(groupLayout).addComponent(loginButton).addComponent(cancelButton)));

        addWindowClosingListener(dialog);
        initDialog(dialog, groupLayout, loginButton, WindowConstants.DO_NOTHING_ON_CLOSE);
    }*/

    /*private static final GroupLayout createGroupLayout() {
        final GroupLayout groupLayout = new GroupLayout(dialog.getContentPane());
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        return groupLayout;
    }*/

    /*private static final GroupLayout.ParallelGroup createBaselineGroup() {
        return groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
    }*/

    /*private static final void initDialog() {
        dialog.setModal(true);
        dialog.setLayout(layout);
        dialog.pack();
        dialog.setLocation((SCREEN_WIDTH - dialog.getWidth()) / 2, (SCREEN_HEIGHT - dialog.getHeight()) / 2);
        dialog.setResizable(false);
        dialog.getRootPane().setDefaultButton(defaultButton);
        dialog.setDefaultCloseOperation(closeOperation);
        dialog.setVisible(true);
    }*/
}
