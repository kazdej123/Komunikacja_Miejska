package view;

import controller.Controller;
import model.Model;

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
import java.awt.Font;
import java.awt.event.ActionListener;

final class MainPanel extends JPanel {
    static final String ALL = "All";

    private static final Dimension buttonDimension = new Dimension(270, 50);

    private final JPanel cardsPanel = new JPanel(new CardLayout());
    private final JPanel buttonsPanel = new JPanel();

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
        setName(name);
        add(cardsPanel, BorderLayout.CENTER);

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(DefaultView.createLabel("Wyświetl " + name.toLowerCase(), buttonDimension));
        setEmptyBorder(buttonsPanel, 30, 20, 30, 50);

        add(buttonsPanel, BorderLayout.EAST);
    }

    final void addTableView(final String name, final RowCountGetter rowCountGetter, final ValueGetter valueGetter, final String text, final TableViewShower tableViewShower, final String... columnNames) {
        final JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setName(name);

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

        final JScrollPane scrollPane = new JScrollPane(table);
        setEmptyBorder(scrollPane, 20, 10, 10, 10);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        final JPanel internalButtonsPanel = new JPanel();
        internalButtonsPanel.add(createButton("Dodaj rekord", null));
        internalButtonsPanel.add(createButton("Usuń rekordy", null));
        setEmptyBorder(internalButtonsPanel, 10, 10, 80, 10);

        tablePanel.add(internalButtonsPanel, BorderLayout.SOUTH);

        cardsPanel.add(tablePanel, name);

        final JButton button = createButton(text, e1 -> {
            final Controller controller = null;

            if (controller != null) {
                tableViewShower.showTableView();
            } else {
                if (name != ALL) {
                    final JDialog dialog = new JDialog();
                    // TODO
                }
                ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, name);
            }
        });
        DefaultView.setJComponentProperties(button, buttonDimension);

        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonsPanel.add(button);
    }

    private static void setEmptyBorder(final JComponent component, final int top, final int left, final int bottom, final int right) {
        component.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    private static JButton createButton(final String text, final ActionListener actionListener) {
        final JButton button = DefaultView.createButton(text, actionListener);
        button.setFont(new Font(DefaultView.FONT_NAME, Font.PLAIN, 20));
        return button;
    }
}
