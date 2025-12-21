import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import java.awt.Font;

@SuppressWarnings("serial")
public class RAMFrame extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RAMFrame frame = new RAMFrame(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    @SuppressWarnings("rawtypes")
    JList list;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public RAMFrame(RAM ram) {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(487, 138, 134, 246);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 120, 209);
        contentPane.add(scrollPane);

        list = new JList();
        list.setFont(new Font("Segoe UI Historic", Font.PLAIN, 12));
        scrollPane.setViewportView(list);
        list.setModel(new AbstractListModel() {
            String[] values = ram.getValues();
            public int getSize() {
                return values.length;
            }
            public Object getElementAt(int index) {
                return values[index];
            }
        });
        list.setVisibleRowCount(64);
        list.setToolTipText("");
    }
}

