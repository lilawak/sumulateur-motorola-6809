import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class CPUFrame extends JFrame {

    JTextField RegA, RegB, RegX, RegY;
    JTextField RegPC, RegS, RegU, RegDP, RegFlags, Instruction;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CPU cpu = new CPU(); // IMPORTANT : pas null
                CPUFrame frame = new CPUFrame(cpu);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CPUFrame(CPU cpu) {

        /* ===== Fenêtre ===== */
        setTitle("CPU Monitor - 6809");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        /* ===== Couleurs & police ===== */
        Color bg = new Color(25, 25, 25);
        Color fg = new Color(0, 220, 120);
        Color panelBg = new Color(35, 35, 35);
        Font font = new Font("Consolas", Font.BOLD, 16);

        getContentPane().setBackground(bg);
        setLayout(new BorderLayout(10, 10));

        /* ===== Instruction ===== */
        Instruction = createField(cpu.I.val, font, fg);
        Instruction.setPreferredSize(new Dimension(0, 40));
        add(Instruction, BorderLayout.NORTH);

        /* ===== Panneau central ===== */
        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));
        center.setBackground(bg);
        add(center, BorderLayout.CENTER);

        /* ===== Registres généraux ===== */
        JPanel general = createPanel("Registres généraux", panelBg, fg);
        general.setLayout(new GridLayout(4, 2, 8, 8));

        RegA = createField(cpu.A.val, font, fg);
        RegB = createField(cpu.B.val, font, fg);
        RegX = createField(cpu.X.val, font, fg);
        RegY = createField(cpu.Y.val, font, fg);

        general.add(new JLabel("A")); general.add(RegA);
        general.add(new JLabel("B")); general.add(RegB);
        general.add(new JLabel("X")); general.add(RegX);
        general.add(new JLabel("Y")); general.add(RegY);

        /* ===== Pointeurs ===== */
        JPanel pointers = createPanel("Pointeurs", panelBg, fg);
        pointers.setLayout(new GridLayout(4, 2, 8, 8));

        RegPC = createField(cpu.PC.val, font, fg);
        RegS  = createField(cpu.S.val, font, fg);
        RegU  = createField(cpu.U.val, font, fg);
        RegDP = createField(cpu.DP.val, font, fg);

        pointers.add(new JLabel("PC")); pointers.add(RegPC);
        pointers.add(new JLabel("S"));  pointers.add(RegS);
        pointers.add(new JLabel("U"));  pointers.add(RegU);
        pointers.add(new JLabel("DP")); pointers.add(RegDP);

        center.add(general);
        center.add(pointers);

        /* ===== Flags ===== */
        JPanel flagsPanel = createPanel("Flags (E F H I N Z V C)", panelBg, fg);
        RegFlags = createField(cpu.F.val, font, fg);
        flagsPanel.add(RegFlags);

        add(flagsPanel, BorderLayout.SOUTH);

        /* ===== Couleur labels ===== */
        setLabelColor(this.getContentPane(), Color.WHITE);
    }

    /* ===== Methodes utilitaires ===== */

    private JTextField createField(String text, Font font, Color fg) {
        JTextField field = new JTextField(text);
        field.setFont(font);
        field.setForeground(fg);
        field.setBackground(Color.BLACK);
        field.setEditable(false);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(BorderFactory.createLineBorder(fg));
        return field;
    }

    private JPanel createPanel(String title, Color bg, Color fg) {
        JPanel panel = new JPanel();
        panel.setBackground(bg);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(fg),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Consolas", Font.BOLD, 14),
                fg
        ));
        return panel;
    }

    private void setLabelColor(Container container, Color color) {
        for (Component c : container.getComponents()) {
            if (c instanceof JLabel) {
                c.setForeground(color);
            } else if (c instanceof Container) {
                setLabelColor((Container) c, color);
            }
        }
    }
}

