package view;

import controller.Controller;
import model.Model;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public final class DefaultView implements View {
    private static final String FONT_NAME = "Tahoma";

    @FunctionalInterface
    private interface RowCountGetter {
        int getRowCount();
    }

    @FunctionalInterface
    private interface ValueGetter {
        Object getValueAt();
    }

    @FunctionalInterface
    private interface TableViewShowingHandler {
        void showTableView();
    }

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

                final List<JPanel> mainPanelsList = new ArrayList<>();
                /*mainPanelsList.add(createMainPanel(controller));
                mainPanelsList.add(createMainPanel(controller));
                mainPanelsList.add(createMainPanel(controller));
                mainPanelsList.add(createMainPanel(controller));
                mainPanelsList.add(createMainPanel(controller));
                mainPanelsList.add(createMainPanel(controller));
                mainPanelsList.add(createMainPanel(controller));
                mainPanelsList.add(createMainPanel(controller));
                mainPanelsList.add(createMainPanel(controller));
                mainPanelsList.add(createMainPanel(controller));*/

                for (int i = 0; i < mainPanelsList.size(); i++) {
                    tabbedPane.add(mainPanelsList.get(i));
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

    private static JPanel createMainPanel(final String name, final String[] columnNames, final RowCountGetter rowCountGetter, final ValueGetter valueGetter, final TableViewShowingHandler tableViewShowingHandler) {
        final JPanel mainPanel = createDoubleBufferedPanel(new BorderLayout());

        mainPanel.setName(name);

        final JPanel cardsPanel = createDoubleBufferedPanel(new CardLayout());

        final JPanel tablePanel = createDoubleBufferedPanel(new BorderLayout());

        final String all = "All";

        tablePanel.setName(all);

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
                return rowCountGetter.getRowCount();
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

        final JPanel internalButtonsPanel = createDoubleBufferedPanel(new FlowLayout());
        internalButtonsPanel.add(createAdjustedButton("Dodaj rekord", null));
        internalButtonsPanel.add(createAdjustedButton("Usuń rekordy", null));
        setEmptyBorder(internalButtonsPanel, 10, 10, 80, 10);

        tablePanel.add(internalButtonsPanel, BorderLayout.SOUTH);

        cardsPanel.add(tablePanel, all);

        mainPanel.add(cardsPanel, BorderLayout.CENTER);

        final JPanel buttonsPanel = createDoubleBufferedPanel(null);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        final Dimension dimension = new Dimension(270, 50);

        buttonsPanel.add(createLabel("Wyświetl " + name.toLowerCase(), dimension));
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        final JButton button = createAdjustedButton("wszystkie", e1 -> tableViewShowingHandler.showTableView());
        setDefaultJComponentProperties(button, dimension);

        buttonsPanel.add(button);
        setEmptyBorder(buttonsPanel, 30, 20, 30, 50);

        mainPanel.add(buttonsPanel, BorderLayout.EAST);
        return mainPanel;
    }

    @NotNull
    @Contract("_ -> new")
    private static JPanel createDoubleBufferedPanel(final LayoutManager layout) {
        return new JPanel(layout, true);
    }

    private static void setEmptyBorder(@NotNull final JComponent component, final int top, final int left, final int bottom, final int right) {
        component.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    private static JButton createAdjustedButton(final String text, final ActionListener actionListener) {
        final JButton button = createButton(text, actionListener);
        button.setFont(new Font(FONT_NAME, Font.PLAIN, 20));
        return button;
    }

    private static JButton createButton(final String text, final ActionListener actionListener) {
        final JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private static JLabel createLabel(final String text, final Dimension dimension) {
        final JLabel label = new JLabel(text, SwingConstants.CENTER);
        setDefaultJComponentProperties(label, dimension);
        label.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        return label;
    }

    private static void setDefaultJComponentProperties(@NotNull final JComponent component, final Dimension dimension) {
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        component.setPreferredSize(dimension);
    }
}
