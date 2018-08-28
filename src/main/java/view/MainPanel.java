package view;

import controller.Controller;
import model.Model;
import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class MainPanel extends JPanel {
    static final String ALL = "All";

    private static final int FONT_SIZE = 18;

    private static final Dimension buttonDimension = new Dimension(14 * FONT_SIZE, 45);

    private final JPanel cardsPanel = createCycleRootFocusedPanel(new CardLayout());
    private final JPanel buttonsPanel = createCycleRootFocusedPanel(null);

    private final Frame ownerFrame;

    @FunctionalInterface
    interface RowCountGetter {
        int getRowCount();
    }

    @FunctionalInterface
    interface ValueGetter {
        Object getValueAt();
    }

    @FunctionalInterface
    interface TableViewShower {
        void showTableView();
    }

    static final class TableViewNames {
        private final String tablePanelName;
        private final String choosingDialogTitle;

        TableViewNames(final String tablePanelName, final String choosingDialogTitle) {
            this.tablePanelName = tablePanelName;
            this.choosingDialogTitle = choosingDialogTitle;
        }
    }

    MainPanel(final String name, final Frame ownerFrame) {
        super(new BorderLayout());
        this.ownerFrame = ownerFrame;

        setName(name);
        setFocusCycleRoot(true);
        add(cardsPanel, BorderLayout.CENTER);

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(DefaultView.createLabel("Wyświetl " + name.toLowerCase(), buttonDimension, (int) (1.1 * FONT_SIZE)));
        setEmptyBorder(buttonsPanel, 30, 10, 30, 20);

        add(buttonsPanel, BorderLayout.EAST);
    }

    final void addTableView(final TableViewNames tableViewNames, final RowCountGetter rowCountGetter, final ValueGetter valueGetter, final String buttonText, final TableViewShower tableViewShower, final String... columnNames) {
        final JPanel tablePanel = createCycleRootFocusedPanel(new BorderLayout());

        final String tablePanelName = tableViewNames.tablePanelName;

        tablePanel.setName(tablePanelName);

        final JTable table = new JTable(new AbstractTableModel() {
            @Override
            public final int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public final String getColumnName(final int columnIndex) {
                return columnNames[columnIndex];
            }

            @Override
            public final int getRowCount() {
                final Model model = null;

                if (model != null) {
                    return rowCountGetter.getRowCount();
                } else {
                    return 0;
                }
            }

            @Override
            public final Object getValueAt(final int rowIndex, final int columnIndex) {
                return valueGetter.getValueAt();
            }
        });
        table.setAutoCreateRowSorter(true);
        DefaultView.setJComponentBoldFont(table.getTableHeader(), 12);

        final JScrollPane scrollPane = new JScrollPane(table);
        setEmptyBorder(scrollPane, 20, 10, 10, 10);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        final JPanel internalButtonsPanel = createCycleRootFocusedPanel(new FlowLayout());
        internalButtonsPanel.add(createInternalButton("Dodaj rekord", null));
        internalButtonsPanel.add(createInternalButton("Usuń rekordy", null));
        setEmptyBorder(internalButtonsPanel, 10, 10, 30, 10);

        tablePanel.add(internalButtonsPanel, BorderLayout.SOUTH);

        cardsPanel.add(tablePanel, tablePanelName);

        final JButton button = DefaultView.createButton(buttonText, e1 -> {
            final Controller controller = null;

            if (controller != null) {
                tableViewShower.showTableView();
            } else {
                if (!tablePanelName.equals(ALL)) {
                    final JDialog dialog = new JDialog(ownerFrame, "Wybierz " + tableViewNames.choosingDialogTitle);
                    DefaultView.initDialog(dialog, null, null, WindowConstants.DISPOSE_ON_CLOSE); // TODO
                    // TODO
//                    dialog.setVisible(true);
                }
                ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, tablePanelName);
            }
        });
        DefaultView.centerJComponent(button);
        setJComponentPlainFont(button, FONT_SIZE);
        button.setMaximumSize(buttonDimension);

        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonsPanel.add(button);
    }

    private static void setEmptyBorder(@NotNull final JComponent component, final int top, final int left, final int bottom, final int right) {
        component.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    private static JPanel createCycleRootFocusedPanel(final LayoutManager layout) {
        final JPanel panel = new JPanel(layout);
        panel.setFocusCycleRoot(true);
        return panel;
    }

    private static JButton createInternalButton(final String text, final ActionListener actionListener) {
        final JButton button = DefaultView.createButton(text, actionListener);
        setJComponentPlainFont(button, 18);
        button.setPreferredSize(new Dimension(180, 40));
        return button;
    }

    private static void setJComponentPlainFont(final JComponent jComponent, final int size) {
        DefaultView.setJComponentFont(jComponent, Font.PLAIN, size);
    }
}
