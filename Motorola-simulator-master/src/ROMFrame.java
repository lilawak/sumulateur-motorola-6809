import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;
import java.awt.EventQueue;
import javax.swing.JScrollPane;

public class ROMFrame extends JFrame {

    JList<?> list;

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    //executer l'application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ROMFrame frame = new ROMFrame(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ROMFrame(ROM rom) {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(600, 100, 280, 400);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 260, 360);
        contentPane.add(scrollPane);

        list = new JList();
        scrollPane.setViewportView(list);
        list.setFont(new Font("Consolas", Font.PLAIN, 12));

        list.setModel(new AbstractListModel() {
            String[] values = rom.getValues();
            public int getSize() {
                return values.length;
            }
            public Object getElementAt(int index) {
                return values[index];
            }
        });
    }

}


