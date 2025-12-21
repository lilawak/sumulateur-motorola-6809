public class CPU {
    Registre A = new Registre("A");
    Registre B = new Registre("B");
    Registre X = new Registre("X");
    Registre Y = new Registre("Y");
    Registre PC = new Registre("PC");
    Registre DP = new Registre("DP");
    Registre S = new Registre("S");
    Registre U = new Registre("U");
    Registre F = new Registre("F");//flag reg
    Registre I = new Registre("I");//insruction

    void reset() {
        A = new Registre("A");
        B = new Registre("B");
        X = new Registre("X");
        Y = new Registre("Y");
        PC = new Registre("PC");
        DP = new Registre("DP");
        S = new Registre("S");
        U = new Registre("U");
        F = new Registre("F");
        I = new Registre("I");
    }
}
