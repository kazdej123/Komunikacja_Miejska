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
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

final class MainPanel extends JPanel {
    static final String ALL = "All";

    private static final int FONT_SIZE = 18;

    private static final Dimension buttonDimension = new Dimension(14 * FONT_SIZE, 45);

    private final JPanel cardsPanel = createCycleRootFocusedPanel(new CardLayout());
    private final JPanel buttonsPanel = createCycleRootFocusedPanel(null);

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

    MainPanel(final String name) {
        super(new BorderLayout());
        setPanelNameAndCycleRootFocused(this, name);
        add(cardsPanel, BorderLayout.CENTER);

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(DefaultView.createLabel("Wyświetl " + name.toLowerCase(), buttonDimension, (int) (1.1 * FONT_SIZE)));
        setEmptyBorder(buttonsPanel, 30, 10, 30, 20);

        add(buttonsPanel, BorderLayout.EAST);
    }

    final void addTableView(final String name, final RowCountGetter rowCountGetter, final ValueGetter valueGetter, final String text, final TableViewShower tableViewShower, final String... columnNames) {
        final JPanel tablePanel = new JPanel(new BorderLayout());
        setPanelNameAndCycleRootFocused(tablePanel, name);

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
        DefaultView.setJComponentFont(table.getTableHeader(), Font.BOLD, 12);

        final JScrollPane scrollPane = new JScrollPane(table);
        setEmptyBorder(scrollPane, 20, 10, 10, 10);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        final JPanel internalButtonsPanel = createCycleRootFocusedPanel(new FlowLayout());
        internalButtonsPanel.add(createInternalButton("Dodaj rekord", null));
        internalButtonsPanel.add(createInternalButton("Usuń rekordy", null));
        setEmptyBorder(internalButtonsPanel, 10, 10, 80, 10);

        tablePanel.add(internalButtonsPanel, BorderLayout.SOUTH);

        cardsPanel.add(tablePanel, name);

        final JButton button = DefaultView.createButton(text, e1 -> {
            final Controller controller = null;

            if (controller != null) {
                tableViewShower.showTableView();
            } else {
                if (!name.equals(ALL)) {
                    final JDialog dialog = new JDialog();
                    // TODO
                }
                ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, name);
            }
        });
        DefaultView.centerJComponentAndSetFont(button, Font.PLAIN, FONT_SIZE);
        button.setMaximumSize(buttonDimension);

        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonsPanel.add(button);
    }

    private static JPanel createCycleRootFocusedPanel(final LayoutManager layout) {
        final JPanel panel = new JPanel(layout);
        panel.setFocusCycleRoot(true);
        return panel;
    }

    private static void setPanelNameAndCycleRootFocused(@NotNull final JPanel panel, final String name) {
        panel.setName(name);
        panel.setFocusCycleRoot(true);
    }

    private static void setEmptyBorder(@NotNull final JComponent component, final int top, final int left, final int bottom, final int right) {
        component.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    private static JButton createInternalButton(final String text, final ActionListener actionListener) {
        final JButton button = DefaultView.createButton(text, actionListener);
        DefaultView.setJComponentFont(button, Font.PLAIN, 20);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }
}
