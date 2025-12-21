import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class ProgFrame extends JFrame {

    private JPanel contentPane;
    static Button RESET;
    static Button StepButton;
    static Button ExButton;
    static int C = 0;
    static RAM ram;
    static CPU cpu;
    static ROM rom;
    static JTextArea textArea;
    static JLabel lblNewLabel;

    static String TEXT = "";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ProgFrame frame = new ProgFrame(null, null, null, null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ProgFrame(Programme P, CPU a, RAM b, ROM c) {

        cpu = a;
        ram = b;
        rom = c;

        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(275, 138, 214, 332);

        contentPane = new JPanel();
        contentPane.setBackground(Color.BLACK); // 🔴 FOND NOIR
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 180, 225);
        contentPane.add(scrollPane);

        textArea = new JTextArea();
        textArea.setFont(new Font("Consolas", Font.BOLD, 13));
        textArea.setForeground(Color.GREEN);
        textArea.setBackground(Color.BLACK);
        textArea.setCaretColor(Color.GREEN);
        scrollPane.setViewportView(textArea);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel.setBounds(10, 235, 180, 21);
        contentPane.add(panel);
        panel.setLayout(null);

        lblNewLabel = new JLabel("");
        lblNewLabel.setForeground(Color.RED);
        lblNewLabel.setBounds(0, 0, 180, 21);
        panel.add(lblNewLabel);

        ExButton = new Button("Assembler");
        ExButton.setFocusable(false);
        ExButton.setBounds(116, 262, 74, 21);
        contentPane.add(ExButton);
        ExButton.addActionListener(e -> EXEC());

        StepButton = new Button("Step by Step");
        StepButton.setEnabled(false);
        StepButton.setBounds(10, 262, 74, 21);
        contentPane.add(StepButton);
        StepButton.addActionListener(e -> Step());

        RESET = new Button("R");
        RESET.setEnabled(false);
        RESET.setBounds(90, 262, 21, 21);
        contentPane.add(RESET);
        RESET.addActionListener(e -> Reset());
    }

    static void Reset() {
        RESET.setEnabled(false);
        StepButton.setEnabled(false);
        ExButton.setEnabled(true);
        C = 0;
        if (cpu != null) cpu.reset();
        if (ram != null) ram.reset();
        if (rom != null) rom.reset();
        Programme.CPUUP();
        Programme.RAMUP();
        Programme.ROMUP();
        textArea.setEditable(true);
        lblNewLabel.setText("");
    }

    static void Step() {
        TEXT = textArea.getText().toUpperCase();
        String[] Tab1 = TEXT.split("\\n");
        if (Tab1[C].trim().matches("END")) {
            StepButton.setEnabled(false);
        } else {
            Programme.Execute(TEXT, C);
            C++;
        }
    }

    static void EXEC() {
        TEXT = textArea.getText().toUpperCase();
        if (Programme.Syntax(TEXT)) {
            RESET.setEnabled(true);
            StepButton.setEnabled(true);
            ExButton.setEnabled(false);
            lblNewLabel.setText("Syntax CORRECT");
            lblNewLabel.setForeground(new Color(0, 200, 0));
            textArea.setEditable(false);
        } else {
            lblNewLabel.setText("Syntax ERROR");
            lblNewLabel.setForeground(Color.RED);
        }
    }

    static void clearTXT() {
        textArea.setText("");
    }
    static boolean Syn() {
        return Programme.Syntax(TEXT);
    }

}
