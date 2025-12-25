import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener{
        // detecter taille 
    static Toolkit T = Toolkit.getDefaultToolkit();
    static Dimension ScreenSize = T.getScreenSize();

    private JPanel contentPane;
        // donner chemin pour trouver les icones 
    static String pwd = System.getProperty("user.dir");

        static JButton NewButton,
            OpenButton,
            SaveButton,
            StepButton,
            QuitButton;
    JMenuItem QuitOption;

    JCheckBoxMenuItem ProgShow,
            RAMShow,
            ROMShow;
    static JMenuItem SaveUnderOption, OuvrirOption;




    static ProgFrame a;
    RAMFrame b;
    ROMFrame c;
    


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame(null, null, null, null, null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    



    public MainFrame(Programme P, CPUFrame cpuF, RAMFrame ramF, ProgFrame progF, ROMFrame romF) {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        a = progF;
        b = ramF;
        c = romF;



        cpuF.setVisible(true);
        ramF.setVisible(true);
        progF.setVisible(true);
        romF.setVisible(true);


        setTitle("MOTO_6809");
        setBounds(0, 0, ScreenSize.width, 128);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    // auto close all 
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                cpuF.dispose();
                ramF.dispose();
                progF.dispose();
                romF.dispose();
            }
        });
        // affichage  de menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu FichierMenu = new JMenu("Fichier");
        menuBar.add(FichierMenu);

        JMenuItem NouveauOption = new JMenuItem("Nouveau");
        NouveauOption.setHorizontalTextPosition(SwingConstants.LEFT);
        NouveauOption.setHorizontalAlignment(SwingConstants.LEFT);
        FichierMenu.add(NouveauOption);
        NouveauOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProgFrame.clearTXT();
            }
        });

        OuvrirOption = new JMenuItem("Ouvrir");
        FichierMenu.add(OuvrirOption);
        OuvrirOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProgFrame.Reset();
                File(e);
            }
        });

        SaveUnderOption = new JMenuItem("Enregistrer sous...");
        FichierMenu.add(SaveUnderOption);
        SaveUnderOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ProgFrame.Syn())
                {
                    File(e);
                }
                else {
                    ProgFrame.lblNewLabel.setText("Syntax ERROR");
                    ProgFrame.lblNewLabel.setForeground(new Color(255, 0, 0));
                }

            }
        });

        JSeparator separator = new JSeparator();
        FichierMenu.add(separator);

        JMenuItem AssembleOption = new JMenuItem("Assembler");
        FichierMenu.add(AssembleOption);
        AssembleOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProgFrame.EXEC();
            }
        });

        JSeparator separator_1 = new JSeparator();
        FichierMenu.add(separator_1);

        QuitOption = new JMenuItem("Quitter");
        FichierMenu.add(QuitOption);
        QuitOption.addActionListener(this);

        //barre vide 
        JMenu SimulationMenu = new JMenu("Simulation");
        menuBar.add(SimulationMenu);
        JMenu OutilsMenu = new JMenu("Outils");
        menuBar.add(OutilsMenu);
        JMenu OptionsMenu = new JMenu("Options");
        menuBar.add(OptionsMenu);

        JMenu FenetresMenu = new JMenu("Fenetres");
        menuBar.add(FenetresMenu);
        ProgShow = new JCheckBoxMenuItem("Programme");
        ProgShow.setSelected(true);
        FenetresMenu.add(ProgShow);
        ProgShow.addActionListener(this);

        RAMShow = new JCheckBoxMenuItem("RAM");
        RAMShow.setSelected(true);
        FenetresMenu.add(RAMShow);
        RAMShow.addActionListener(this);

        ROMShow = new JCheckBoxMenuItem("ROM");
        ROMShow.setSelected(true);
        FenetresMenu.add(ROMShow);
        ROMShow.addActionListener(this);


  

        JMenu HelpMenu = new JMenu("Help");
        menuBar.add(HelpMenu);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.setBounds(10, 0, ScreenSize.width-34, 33);
        contentPane.add(panel);
        panel.setLayout(null);

        NewButton = new JButton("");
        NewButton.setHorizontalTextPosition(SwingConstants.CENTER);
        NewButton.setIconTextGap(0);
        NewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProgFrame.clearTXT();
            }
        });
        NewButton.setFocusable(false);
        NewButton.setIcon(new ImageIcon(pwd+"/files/New.png"));
        NewButton.setBounds(2, 3, 27, 27);
        panel.add(NewButton);

        OpenButton = new JButton("");
        OpenButton.setIcon(new ImageIcon(pwd+"/files/Open.png"));
        OpenButton.setIconTextGap(0);
        OpenButton.setHorizontalTextPosition(SwingConstants.CENTER);
        OpenButton.setFocusable(false);
        OpenButton.setBounds(32, 3, 27, 27);
        panel.add(OpenButton);
        OpenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProgFrame.Reset();
                File(e);
            }
        });

        SaveButton = new JButton("");
        SaveButton.setIcon(new ImageIcon(pwd+"/files/Save.png"));
        SaveButton.setIconTextGap(0);
        SaveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        SaveButton.setFocusable(false);
        SaveButton.setBounds(62, 3, 27, 27);
        panel.add(SaveButton);
        SaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ProgFrame.Syn())
                {
                    File(e);
                }
                else {
                    ProgFrame.lblNewLabel.setText("Syntax ERROR");
                    ProgFrame.lblNewLabel.setForeground(new Color(255, 0, 0));
                }
            }
        });

        StepButton = new JButton("");
        StepButton.setIcon(new ImageIcon(pwd+"/files/Step.png"));
        StepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProgFrame.Step();
            }
        });
        StepButton.setIconTextGap(0);
        StepButton.setHorizontalTextPosition(SwingConstants.CENTER);
        StepButton.setFocusable(false);
        StepButton.setBounds(109, 3, 27, 27);
        panel.add(StepButton);

        QuitButton = new JButton("");
        QuitButton.setIcon(new ImageIcon(pwd+"/files/Quit.png"));
        QuitButton.setIconTextGap(0);
        QuitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        QuitButton.setFocusable(false);
        QuitButton.setBounds(ScreenSize.width-65, 3, 27, 27);
        QuitButton.addActionListener(this);
        panel.add(QuitButton);

        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(99, 3, 0, 27);
        panel.add(separator_2);

        JSeparator separator_3 = new JSeparator();
        separator_3.setBounds(162, 3, 0, 27);
        panel.add(separator_3);

        JLabel lblNewLabel = new JLabel(" YASSINE MAKIL ");
        lblNewLabel.setFont(new Font("Sitka Text", Font.BOLD, 13));
        lblNewLabel.setBounds(ScreenSize.width-175, 46, 157, 13);
        contentPane.add(lblNewLabel);


    }
        //ACTION DU BT
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == QuitButton || e.getSource() == QuitOption) {
            System.exit(0);
        }
        else if(e.getSource() == ProgShow) {
            if(a.isVisible())
            {
                a.dispose();
            }

            else
            {
                a.setVisible(true);
            }
        }
        else if(e.getSource() == RAMShow) {
            if(b.isVisible())
            {
                b.dispose();
            }
            else
            {
                b.setVisible(true);
            }
        }
        else if(e.getSource() == ROMShow) {
            if(c.isVisible())
            {
                c.dispose();
            }
            else
            {
                c.setVisible(true);
            }
        }


    }
    // files handler 
    private static void File (ActionEvent e){
        JFileChooser OpenFile = new JFileChooser(pwd+"/Programmes");
        if(e.getSource() == OpenButton||e.getSource() == OuvrirOption) {

            int Status = OpenFile.showOpenDialog(null);

            if(Status == JFileChooser.APPROVE_OPTION) {
                System.out.println("User got: "+ OpenFile.getSelectedFile().getAbsolutePath());
                try {
                    BufferedReader Reader = new BufferedReader(new FileReader(OpenFile.getSelectedFile().getAbsolutePath()));
                    String temp;
                    String txt = "";
                    while((temp=Reader.readLine())!=null) {
                        txt += temp;
                        txt += '\n';
                    }
                    ProgFrame.textArea.setText(txt);
                    Reader.close();
                }
                catch(Exception err) {
                    err.printStackTrace();
                }
            }
            else
            {
                System.out.println("User canceled the selection");
            }
        }
        else
        {
            int Status = OpenFile.showSaveDialog(null);

            if(Status == JFileChooser.APPROVE_OPTION) {
                System.out.println("User got: "+ OpenFile.getSelectedFile().getAbsolutePath());
                try {
                    BufferedWriter Writer = new BufferedWriter(new FileWriter(OpenFile.getSelectedFile().getAbsolutePath()));
                    Writer.write(ProgFrame.textArea.getText());
                    Writer.close();
                }
                catch(Exception err) {
                    err.printStackTrace();
                }
            }
            else
            {
                System.out.println("User canceled the selection");
            }
        }
    }
}

