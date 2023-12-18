import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Task> taskList = new ArrayList<>();
    private static DefaultListModel<String> listModel = new DefaultListModel<>();
    private static JList<String> taskJList;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("To-Do List Anwendung");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        centerFrameOnScreen(frame);

        taskJList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskJList);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Aufgabe hinzufügen");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        JButton changeStatusButton = new JButton("Status ändern");
        changeStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTaskStatus();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(changeStatusButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

    private static void addTask() {
        String description = JOptionPane.showInputDialog("Geben Sie die Aufgabenbeschreibung ein:");
        if (description != null && !description.trim().isEmpty()) {
            Task task = new Task(description);
            taskList.add(task);
            listModel.addElement(task.getDescription() + " - " + task.getStatus());
        }
    }

    private static void changeTaskStatus() {
        int selectedIndex = taskJList.getSelectedIndex();

        if (selectedIndex != -1) {
            Task task = taskList.get(selectedIndex);
            String[] statusOptions = {"offen", "in Bearbeitung", "erledigt"};
            String newStatus = (String) JOptionPane.showInputDialog(null,
                    "Wählen Sie einen neuen Status:",
                    "Status ändern",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    statusOptions,
                    statusOptions[0]);

            if (newStatus != null && !newStatus.trim().isEmpty()) {
                task.setStatus(newStatus);
                listModel.setElementAt(task.getDescription() + " - " + task.getStatus(), selectedIndex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Klicke auf eine Aufgabe, um den Status zu ändern.\n(Klicke auf eine Aufgabe)");
        }
    }

    private static class Task {
        private String description;
        private String status;

        public Task(String description) {
            this.description = description;
            this.status = "offen";
        }

        public String getDescription() {
            return description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
