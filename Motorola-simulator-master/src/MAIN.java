
public class MAIN {

    public static void main(String[] args) {
        RAM ram = new RAM();
        ROM rom = new ROM();
        CPU cpu =new CPU();

        CPUFrame cpuF = new CPUFrame(cpu);
        RAMFrame ramF = new RAMFrame(ram);
        ROMFrame romF = new ROMFrame(rom);
        Programme P = new Programme(ram, cpu, ramF, cpuF, rom, romF);
        ProgFrame progF = new ProgFrame(P,cpu,ram,rom);

        MainFrame a = new MainFrame(P, cpuF, ramF, progF, romF);
        a.setVisible(true);
    }
}