//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MAIN{
    public static void main(String[] args) {
        RAM ram = new RAM();
        ROM rom = new ROM();
        CPU cpu =new CPU();

        CPUFrame cpuFrame = new CPUFrame(cpu);
        RAMFrame ramFrame = new RAMFrame(ram);
        ROMFrame romFrame = new ROMFrame(rom);
        Programme P = new Programme(ram, cpu, ramFrame, cpuFrame, rom, romFrame);
        ProgFrame progFrame = new ProgFrame(P,cpu,ram,rom);

        MainFrame a = new MainFrame(P, cpuFrame, ramFrame, progFrame, romFrame);
        a.setVisible(true);
    }
}