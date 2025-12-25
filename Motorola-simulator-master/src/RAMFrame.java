import javax.swing.*;
import java.awt.*;

public class RAMFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    JList<String> memoryList;
    private DefaultListModel<String> listModel;

    public RAMFrame(RAM ram) {
        setTitle("RAM Memory");
        setSize(280, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI(ram);
    }

    private void initUI(RAM ram) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        listModel = new DefaultListModel<>();
        memoryList = new JList<>(listModel);

        memoryList.setFont(new Font("Consolas", Font.PLAIN, 12));
        memoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(memoryList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        loadRAM(ram);
    }

    private void loadRAM(RAM ram) {
        listModel.clear();
        for (String line : ram.getValues()) {
            if (line != null) { // sécurité
                listModel.addElement(line);
            }
        }
    }

    // rafraichir 
    public void refresh(RAM ram) {
        loadRAM(ram);
    }

    // Test simple
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RAM ram = new RAM();
            ram.Update(10, "3A");
            ram.Update(11, "FF");
            ram.Update(255, "7C");

            new RAMFrame(ram).setVisible(true);
        });
    }
}

