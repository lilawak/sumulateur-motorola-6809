import java.util.HashMap;
public class ROM {

    public static HashMap<Integer,String> Memory = new HashMap<>();

    public static HashMap<String,String> Immediat = new HashMap<>();
    public static HashMap<String,String> Direct = new HashMap<>();
    public static HashMap<String,String> Indexe = new HashMap<>();
    public static HashMap<String,String> Etendu = new HashMap<>();
    public static HashMap<String,String> Inherent = new HashMap<>();
    public static HashMap<String,String> Empilement = new HashMap<>();
    public static HashMap<String,String> PostOctet = new HashMap<>();




    public ROM()
    {
        int i;
        for(i=0 ; i<= 1023 ; i++)
        {
            Memory.put(i,"FF");
        }



        //ordre d'empilement des registres
        Empilement.put("CCR","0");
        Empilement.put("A","0");
        Empilement.put("B","0");
        Empilement.put("DP","0");
        Empilement.put("X","0");
        Empilement.put("Y","0");
        Empilement.put("U","0");
        Empilement.put("PC","0");

        Inherent.put("CLRA", "4F");
        Inherent.put("CLRB", "5F");
        Inherent.put("COMA", "43");
        Inherent.put("COMB", "53");
        Inherent.put("INCA", "4C");
        Inherent.put("INCB", "5C");
        Inherent.put("END", "3F");

        Immediat.put("LDA", "86");
        Immediat.put("LDB", "C6");
        Immediat.put("LDX", "8E");
        Immediat.put("LDY", "108E");
        Immediat.put("LDS", "10CE");
        Immediat.put("LDU", "CE");
        Immediat.put("LDD", "CC");
        Immediat.put("ADDA", "8B");
        Immediat.put("ADDB", "CB");
        Immediat.put("SUBA", "80");
        Immediat.put("SUBB", "C0");
        Immediat.put("ADDD", "C3");
        Immediat.put("SUBD", "83");
        Immediat.put("PSHS", "34");
        Immediat.put("PSHU", "36");
        Immediat.put("PULS", "35");
        Immediat.put("PULU", "37");


        Direct.put("LDA", "96");
        Direct.put("LDB", "D6");
        Direct.put("LDD", "DC");
        Direct.put("LDX", "9E");
        Direct.put("LDY", "109E");
        Direct.put("LDS", "10DE");
        Direct.put("LDU", "DE");
        Direct.put("STA", "97");
        Direct.put("STB", "D7");
        Direct.put("STD", "DD");
        Direct.put("STX", "9F");
        Direct.put("STY", "109F");
        Direct.put("STS", "10DF");
        Direct.put("STU", "DF");
        Direct.put("ADDA", "9B");
        Direct.put("ADDB", "DB");
        Direct.put("ADDD", "D3");
        Direct.put("SUBA", "90");
        Direct.put("SUBB", "D0");
        Direct.put("SUBD", "93");

        Indexe.put("LDA", "A6");
        Indexe.put("LDB", "E6");
        Indexe.put("LDD", "EC");
        Indexe.put("LDX", "AE");
        Indexe.put("LDY", "10AE");
        Indexe.put("LDS", "10EE");
        Indexe.put("LDU", "EE");
        Indexe.put("STA", "A7");
        Indexe.put("STB", "E7");
        Indexe.put("STD", "ED");
        Indexe.put("STX", "AF");
        Indexe.put("STY", "10AF");
        Indexe.put("STS", "10EF");
        Indexe.put("STU", "EF");
        Indexe.put("ADDA", "AB");
        Indexe.put("ADDB", "EB");
        Indexe.put("ADDD", "E3");
        Indexe.put("SUBA", "A0");
        Indexe.put("SUBB", "E0");
        Indexe.put("SUBD", "A3");

        Etendu.put("LDA", "B6");
        Etendu.put("LDB", "F6");
        Etendu.put("LDD", "FC");
        Etendu.put("LDX", "BE");
        Etendu.put("LDY", "10BE");
        Etendu.put("LDS", "10FE");
        Etendu.put("LDU", "FE");
        Etendu.put("STA", "B7");
        Etendu.put("STB", "F7");
        Etendu.put("STD", "FD");
        Etendu.put("STX", "BF");
        Etendu.put("STY", "10BF");
        Etendu.put("STS", "10FF");
        Etendu.put("STU", "FF");
        Etendu.put("ADDA", "BB");
        Etendu.put("ADDB", "FB");
        Etendu.put("ADDD", "F3");
        Etendu.put("SUBA", "B0");
        Etendu.put("SUBB", "F0");
        Etendu.put("SUBD", "B3");

        //table d’encodage des modes d’adressage indexés
        PostOctet.put("CST", "00000000");//Adressage indexé avec déplacement constant 5 bits
        PostOctet.put("INC1", "10000000");//Auto-incrément +1 du registre d’index (,X+)
        PostOctet.put("INC2", "10000001");//Auto-incrément +2 ( ,X++)
        PostOctet.put("DEC1", "10000010");//Auto-décrément -1 ( ,-X)
        PostOctet.put("DEC2", "10000011");//Auto-décrément -2 ( ,--X)
        PostOctet.put("NULL", "10000100");//Offset nul (,X)
        PostOctet.put("DEPB", "10000101");//Déplacement contenu dans le registre B
        PostOctet.put("DEPA", "10000110");//Déplacement contenu dans le registre A
        PostOctet.put("DEP7", "10001000");//Déplacement 8 bits signé decalage 1octet
        PostOctet.put("DEPD", "10001011");//Déplacement 16 bits signé decalage 2octets
    }
    public static int k=0;

    void fill(String Method, String inst, String Op, String Ind) {

        // =====================================================
        // MODE INHERENT (inhérent)
        // =====================================================
        // Instruction sans opérande (ex : CLRA, INCA)
        // Le processeur 6809 lit uniquement l’opcode
        if(Method.matches("Inherent")) {

            // Écriture de l’opcode en mémoire à l’adresse k
            Memory.replace(k, Inherent.get(inst));

            // Incrément du compteur de programme (PC simulé)
            k++;
        }

        // =====================================================
        // MODE IMMEDIAT (immédiat)
        // =====================================================
        // L’opérande est incluse dans l’instruction (#valeur)
        // Exemple : LDA #$12 ou LDD #$1234
        else if(Method.matches("Immediat")) {

            // Certains opcodes du 6809 utilisent un préfixe (10)
            // Ils occupent donc 2 octets
            if(Immediat.get(inst).length() == 4) {

                // Écriture du préfixe
                Memory.replace(k, Immediat.get(inst).substring(0,2));
                k++;

                // Écriture de l’opcode réel
                Memory.replace(k, Immediat.get(inst).substring(2));
                k++;
            }
            else {
                // Opcode simple sur 1 octet
                Memory.replace(k, Immediat.get(inst));
                k++;
            }

            // Écriture de l’opérande immédiate
            // Si elle fait 16 bits → 2 octets
            if(Op.length() == 4) {
                Memory.replace(k, Op.substring(0,2)); // MSB
                k++;
                Memory.replace(k, Op.substring(2,4)); // LSB
                k++;
            }
            else {
                // Opérande sur 8 bits
                Memory.replace(k, Op);
                k++;
            }
        }

        // =====================================================
        // MODE DIRECT
        // =====================================================
        // Adresse sur 8 bits
        // Le 6809 complète l’adresse avec le registre DP
        else if(Method.matches("Direct")) {

            // Opcode avec ou sans préfixe
            if(Direct.get(inst).length() == 4) {
                Memory.replace(k, Direct.get(inst).substring(0,2));
                k++;
                Memory.replace(k, Direct.get(inst).substring(2));
                k++;
            }
            else {
                Memory.replace(k, Direct.get(inst));
                k++;
            }

            // Écriture de l’adresse directe (8 bits)
            Memory.replace(k, Op);
            k++;
        }

        // =====================================================
        // MODE ETENDU
        // =====================================================
        // Adresse mémoire complète sur 16 bits
        else if(Method.matches("Etendu")) {

            // Opcode (avec ou sans préfixe)
            if(Etendu.get(inst).length() == 4) {
                Memory.replace(k, Etendu.get(inst).substring(0,2));
                k++;
                Memory.replace(k, Etendu.get(inst).substring(2));
                k++;
            }
            else {
                Memory.replace(k, Etendu.get(inst));
                k++;
            }

            // Écriture de l’adresse étendue (MSB puis LSB)
            Memory.replace(k, Op.substring(0,2));
            k++;
            Memory.replace(k, Op.substring(2,4));
            k++;
        }

        // =====================================================
        // MODE INDEXE
        // =====================================================
        // Le 6809 utilise un post-octet pour décrire
        // le calcul de l’adresse effective
        else if(Method.matches("Indexe")) {

            // Écriture de l’opcode indexé
            if(Indexe.get(inst).length() == 4) {
                Memory.replace(k, Indexe.get(inst).substring(0,2));
                k++;
                Memory.replace(k, Indexe.get(inst).substring(2));
                k++;
            }
            else {
                Memory.replace(k, Indexe.get(inst));
                k++;
            }

            // Calcul et écriture du post-octet
            // Le CPU lira ce byte pour :
            // - choisir le registre (X, Y, U, S)
            // - appliquer le déplacement
            // - gérer l’auto-incrément / décrément
            PostOctHandler(Op, Ind);
        }

        // =====================================================
        // MODE PUL / PSH (empilement / dépilement)
        // =====================================================
        // Le 6809 peut empiler plusieurs registres en une instruction
        else if(Method.equals("PULPUSH")) {

            // Écriture de l’opcode PSHS / PULS / PSHU / PULU
            Memory.replace(k, Immediat.get(inst));
            k++;

            // Analyse de la liste des registres à empiler
            for(String i : Empilement.keySet()) {

                // Si le registre est présent dans l’opérande
                // on met son bit à 1
                if(Op.contains(i)) {
                    Empilement.replace(i, "1");
                }
                else {
                    // Sinon bit à 0
                    Empilement.replace(i, "0");
                }
            }
    //"PC", "U", "Y", "X", "DP", "B", "A", "CCR"
            String X = Empilement.get("PC")+Empilement.get("U")+Empilement.get("Y")+Empilement.get("X")+Empilement.get("DP")+Empilement.get("B")+Empilement.get("A")+"0";
            X = String.format("%02X", Integer.parseInt(X,2));
            Memory.replace(k,X);
            k++;
        }
    }

    static String PostOctHandler(String Op, String Ind) {

        // Pos contiendra le post-octet final (en hexadécimal)
        String Pos="";

        // =====================================================
        // MODE INDEXÉ : OFFSET NUL (,X ,Y ,U ,S)
        // =====================================================
        // Le processeur utilise directement le registre d’index
        // sans déplacement
        if(Ind.matches("NULL")) {

            System.out.println(Op); // affichage de l’opérande (debug)

            // Post-octet de base pour offset nul
            Pos = PostOctet.get("NULL");

            // Sélection du registre d’index
            // Les bits 5 et 6 du post-octet codent X, Y, U ou S
            if(Op.charAt(1) == 'X') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(1) == 'Y') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 32);
            }
            else if(Op.charAt(1) == 'U') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64);
            }
            else if(Op.charAt(1) == 'D') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64 + 32);
            }

            // Écriture du post-octet en mémoire
            Memory.replace(k, Pos);
            k++; // PC++
        }

        // =====================================================
        // MODE INDEXÉ : DÉPLACEMENT CONSTANT 5 BITS
        // =====================================================
        // Exemple : 5,X  ou  -3,Y
        else if(Ind.matches("CST")) {

            // Base du post-octet
            Pos = PostOctet.get("CST");

            // Extraction du déplacement avant la virgule
            String cst = "" + Op.substring(0, Op.indexOf(','));
            int signe = 0;

            // Si le déplacement est négatif
            // le 6809 utilise un bit de signe
            if(cst.charAt(0) == '-') {
                cst = cst.substring(1);
                signe = 16; // bit signe du déplacement
            }

            // Ajout du registre d’index au post-octet
            if(Op.charAt(Op.indexOf(',')+1) == 'X') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2)
                        + Integer.parseInt(cst) + signe);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'Y') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2)
                        + 32 + Integer.parseInt(cst) + signe);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'U') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2)
                        + 64 + Integer.parseInt(cst) + signe);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'D') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2)
                        + 64 + 32 + Integer.parseInt(cst) + signe);
            }

            Memory.replace(k, Pos);
            k++;
        }

        // =====================================================
        // MODE INDEXÉ : AUTO-INCRÉMENT +1 (,X+)
        // =====================================================
        else if(Ind.matches("INC1")) {

            Pos = PostOctet.get("INC1");

            // Le CPU utilise l’adresse contenue dans le registre
            // puis incrémente le registre de 1
            if(Op.charAt(1) == 'X') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(1) == 'Y') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 32);
            }
            else if(Op.charAt(1) == 'U') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64);
            }
            else if(Op.charAt(1) == 'D') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64 + 32);
            }

            Memory.replace(k, Pos);
            k++;
        }

        // =====================================================
        // MODE INDEXÉ : AUTO-INCRÉMENT +2 (,X++)
        // =====================================================
        else if(Ind.matches("INC2")) {

            Pos = PostOctet.get("INC2");

            // Incrément de 2 (accès mot 16 bits)
            if(Op.charAt(1) == 'X') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(1) == 'Y') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 32);
            }
            else if(Op.charAt(1) == 'U') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64);
            }
            else if(Op.charAt(1) == 'D') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64 + 32);
            }

            Memory.replace(k, Pos);
            k++;
        }

        // =====================================================
        // MODE INDEXÉ : AUTO-DÉCRÉMENT -1 (,-X)
        // =====================================================
        else if(Ind.matches("DEC1")) {

            Pos = PostOctet.get("DEC1");

            // Le registre est décrémenté avant l’accès mémoire
            if(Op.charAt(2) == 'X') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(2) == 'Y') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 32);
            }
            else if(Op.charAt(2) == 'U') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64);
            }
            else if(Op.charAt(2) == 'D') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64 + 32);
            }

            Memory.replace(k, Pos);
            k++;
        }

        // =====================================================
        // MODE INDEXÉ : AUTO-DÉCRÉMENT -2 (,--X)
        // =====================================================
        else if(Ind.matches("DEC2")) {

            Pos = PostOctet.get("DEC2");

            // Décrément de 2 (accès mot)
            if(Op.charAt(3) == 'X') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(3) == 'Y') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 32);
            }
            else if(Op.charAt(3) == 'U') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64);
            }
            else if(Op.charAt(3) == 'D') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64 + 32);
            }

            Memory.replace(k, Pos);
            k++;
        }

        // =====================================================
        // MODE INDEXÉ : DÉPLACEMENT PAR REGISTRE A (A,X)
        // =====================================================
        else if(Ind.matches("DEPA")) {

            Pos = PostOctet.get("DEPA");

            // Le CPU ajoute le contenu du registre A
            // au registre d’index
            if(Op.charAt(Op.indexOf(',')+1) == 'X') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'Y') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 32);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'U') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'D') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64 + 32);
            }

            Memory.replace(k, Pos);
            k++;
        }

        // =====================================================
        // MODE INDEXÉ : DÉPLACEMENT PAR REGISTRE B (B,X)
        // =====================================================
        else if(Ind.matches("DEPB")) {

            Pos = PostOctet.get("DEPB");

            // Le CPU utilise le contenu du registre B
            if(Op.charAt(Op.indexOf(',')+1) == 'X') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'Y') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 32);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'U') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'D') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64 + 32);
            }

            Memory.replace(k, Pos);
            k++;
        }

        // =====================================================
        // MODE INDEXÉ : DÉPLACEMENT 16 BITS
        // =====================================================
        else if(Ind.matches("DEPD")) {

            Pos = PostOctet.get("DEPD");

            // Le CPU lira ensuite 2 octets supplémentaires
            // pour calculer l’adresse effective
            if(Op.charAt(Op.indexOf(',')+1) == 'X') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'Y') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 32);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'U') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'D') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64 + 32);
            }

            Memory.replace(k, Pos);
            k++;
        }

        // =====================================================
        // MODE INDEXÉ : DÉPLACEMENT 8 BITS
        // =====================================================
        else if(Ind.matches("DEP7")) {

            Pos = PostOctet.get("DEP7");

            // Le CPU lira ensuite 1 octet supplémentaire
            if(Op.charAt(Op.indexOf(',')+1) == 'X') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'Y') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 32);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'U') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'D') {
                Pos = String.format("%02X", Integer.parseInt(Pos,2) + 64 + 32);
            }

            // Écriture du post-octet
            Memory.replace(k, Pos);
            k++;

            // Écriture du déplacement 8 bits
            Memory.replace(k, String.format("%02X", Integer.parseInt(Op)));
            k++;
        }

        // Retour du post-octet généré
        return Pos;
    }

    // =====================================================
    // Remise à zéro de la mémoire et du PC
    // =====================================================
    void reset() {
        for(Integer i : Memory.keySet()) {
            Memory.put(i, "FF"); // ROM vide
        }
        k = 0; // PC remis à zéro
    }

    // =====================================================
    // Lecture de la mémoire ROM
    // =====================================================
    // Retourne un listing avec adresses logiques ($8000)
    String[] getValues() {

        String[] T = new String[Memory.size()];
        int c = 0;

        for (Integer i : Memory.keySet()) {

            // Affichage type assembleur 6809
            T[c] = "Adresse " + String.format("%04X", i + Integer.parseInt("8000",16))
                    + "            "
                    + Memory.get(i);
            c++;
        }
        return T;
    }
}
