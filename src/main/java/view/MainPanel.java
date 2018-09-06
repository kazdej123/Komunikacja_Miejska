package view;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Window;

final class MainPanel extends JPanel {
    static final int DEFAULT_FONT_SIZE = 18;

    static final Dimension defaultJComponentDimension = new Dimension(14 * DEFAULT_FONT_SIZE, 45);

//    private final List<TableView> tableViews = new ArrayList<>();

    private final JPanel cardsPanel = createJPanel(new CardLayout(), new Insets(0, 10, 0, 10));
    private final JPanel buttonsPanel = createJPanel(null, new Insets(10, 10, 10, 10));

    private final Window ownerWindow;

    MainPanel(final Window ownerWindow, final String name) {
        super(new BorderLayout());
        this.ownerWindow = ownerWindow;

        setName(name);
        setFocusCycleRoot(true);
        setJComponentEmptyBorder(this, new Insets(20, 10, 30, 10));

        cardsPanel.add(new JPanel(), null);
        add(cardsPanel, BorderLayout.CENTER);

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(DefaultView.createBoldJLabel("Wyświetl " + name.toLowerCase(), (int) (1.1 * DEFAULT_FONT_SIZE), defaultJComponentDimension));
        add(buttonsPanel, BorderLayout.EAST);
    }

    final void addTableView(@NotNull final TableView tableView) {
//        tableViews.add(tableView);
        final JPanel tablePanel = tableView.getTablePanel();
        cardsPanel.add(tablePanel, tablePanel.getName());

        addGapToContainer(buttonsPanel, 0, 15);
        buttonsPanel.add(tableView.getMainButton());
    }

    static void addGapToContainer(@NotNull final Container container, final int width, final int height) {
        container.add(Box.createRigidArea(new Dimension(width, height)));
    }

    static JPanel createJPanel(final LayoutManager layout, final Insets borderInsets) {
        final JPanel jPanel = new JPanel(layout, true);
        jPanel.setFocusCycleRoot(true);
        setJComponentEmptyBorder(jPanel, borderInsets);
        return jPanel;
    }

    static void setJComponentEmptyBorder(@NotNull final JComponent jComponent, final Insets borderInsets) {
        jComponent.setBorder(new EmptyBorder(borderInsets));
    }

    final void showTablePanel(final String tablePanelName) {
        ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, tablePanelName);
    }

    @Contract(pure = true)
    final Window getOwnerWindow() {
        return ownerWindow;
    }
}
