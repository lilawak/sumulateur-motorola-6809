import java.util.HashMap;

public class ROM {
    static int k=0;

    static HashMap<Integer,String> Memory = new HashMap<Integer,String>();

    static HashMap<String,String> Immediat = new HashMap<String,String>();
    static HashMap<String,String> Direct = new HashMap<String,String>();
    static HashMap<String,String> Indexe = new HashMap<String,String>();
    static HashMap<String,String> Etendu = new HashMap<String,String>();
    static HashMap<String,String> Inherent = new HashMap<String,String>();

    static HashMap<String,String> Empilement = new HashMap<String,String>();

    static HashMap<String,String> PostOctet = new HashMap<String,String>();




    ROM()
    {
        int i;
        for(i=0 ; i<= 1023 ; i++)
        {
            Memory.put(i,"FF");
        }

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

        PostOctet.put("CST", "00000000");
        PostOctet.put("INC1", "10000000");
        PostOctet.put("INC2", "10000001");
        PostOctet.put("DEC1", "10000010");
        PostOctet.put("DEC2", "10000011");
        PostOctet.put("NULL", "10000100");
        PostOctet.put("DEPB", "10000101");
        PostOctet.put("DEPA", "10000110");
        PostOctet.put("DEP7", "10001000");
        PostOctet.put("DEPD", "10001011");
    }

    void fill(String Method, String inst, String Op, String Ind) {
        if(Method.matches("Inherent")) {
            Memory.replace(k,Inherent.get(inst));
            k++;
        }
        else if(Method.matches("Immediat")) {
            if(Immediat.get(inst).length()==4) {
                Memory.replace(k,Immediat.get(inst).substring(0,2));
                k++;
                Memory.replace(k,Immediat.get(inst).substring(2));
                k++;
            }
            else {
                Memory.replace(k,Immediat.get(inst));
                k++;
            }


            if(Op.length() == 4) {
                Memory.replace(k,Op.substring(0,2));
                k++;
                Memory.replace(k,Op.substring(2,4));
                k++;
            }
            else {
                Memory.replace(k,Op);
                k++;
            }
        }
        else if(Method.matches("Direct")) {
            if(Direct.get(inst).length() == 4) {
                Memory.replace(k,Direct.get(inst).substring(0,2));
                k++;
                Memory.replace(k,Direct.get(inst).substring(2));
                k++;
            }
            else {
                Memory.replace(k,Direct.get(inst));
                k++;
            }
            Memory.replace(k,Op);
            k++;
        }
        else if(Method.matches("Etendu")) {
            if(Etendu.get(inst).length() == 4) {
                Memory.replace(k,Etendu.get(inst).substring(0,2));
                k++;
                Memory.replace(k,Etendu.get(inst).substring(2));
                k++;
            }
            else {
                Memory.replace(k,Etendu.get(inst));
                k++;
            }

            Memory.replace(k,Op.substring(0,2));
            k++;
            Memory.replace(k,Op.substring(2,4));
            k++;
        }
        else if(Method.matches("Indexe")) {
            if(Indexe.get(inst).length() == 4) {
                Memory.replace(k,Indexe.get(inst).substring(0,2));
                k++;
                Memory.replace(k,Indexe.get(inst).substring(2));
                k++;
            }
            else {
                Memory.replace(k,Indexe.get(inst));
                k++;
            }

            //Indexed processing

            //Post octet calculation
            PostOctHandler(Op,Ind);

        }
        else if(Method.matches("PULPUSH")) {
            Memory.replace(k,Immediat.get(inst));
            k++;
            for(String i : Empilement.keySet()) {
                if(Op.contains(i)) {
                    Empilement.replace(i,"1");
                }
                else
                {
                    Empilement.replace(i,"0");
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
        String Pos="";
        if(Ind.matches("NULL")) {
            System.out.println(Op);
            Pos=PostOctet.get("NULL");
            if(Op.charAt(1) == 'X') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(1) == 'Y') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+32);
            }
            else if(Op.charAt(1) == 'U') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64);
            }
            else if(Op.charAt(1) == 'D') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64+32);
            }
            Memory.replace(k,Pos);
            k++;
        }
        else if(Ind.matches("CST")) {
            Pos=PostOctet.get("CST");
            String cst = ""+Op.substring(0,Op.indexOf(','));
            int signe = 0;

            if(cst.charAt(0) == '-')
            {
                cst = cst.substring(1);//34
                signe = 16;
            }

            if(Op.charAt(Op.indexOf(',')+1) == 'X') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+Integer.parseInt(cst)+signe);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'Y') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+32+Integer.parseInt(cst,10)+signe);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'U') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64+Integer.parseInt(cst,10)+signe);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'D') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64+32+Integer.parseInt(cst,10)+signe);
            }
            Memory.replace(k,Pos);
            k++;
        }
        else if(Ind.matches("INC1")) {
            Pos=PostOctet.get("INC1");
            if(Op.charAt(1) == 'X') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(1) == 'Y') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+32);
            }
            else if(Op.charAt(1) == 'U') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64);
            }
            else if(Op.charAt(1) == 'D') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64+32);
            }
            Memory.replace(k,Pos);
            k++;
        }
        else if(Ind.matches("INC2")) {
            Pos=PostOctet.get("INC2");
            if(Op.charAt(1) == 'X') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(1) == 'Y') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+32);
            }
            else if(Op.charAt(1) == 'U') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64);
            }
            else if(Op.charAt(1) == 'D') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64+32);
            }
            Memory.replace(k,Pos);
            k++;
        }
        else if(Ind.matches("DEC1")) {
            Pos=PostOctet.get("DEC1");
            if(Op.charAt(2) == 'X') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(2) == 'Y') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+32);
            }
            else if(Op.charAt(2) == 'U') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64);
            }
            else if(Op.charAt(2) == 'D') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64+32);
            }
            Memory.replace(k,Pos);
            k++;
        }
        else if(Ind.matches("DEC2")) {
            Pos=PostOctet.get("DEC2");
            if(Op.charAt(3) == 'X') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(3) == 'Y') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+32);
            }
            else if(Op.charAt(3) == 'U') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64);
            }
            else if(Op.charAt(3) == 'D') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64+32);
            }
            Memory.replace(k,Pos);
            k++;
        }
        else if(Ind.matches("DEPA")) {
            Pos=PostOctet.get("DEPA");
            if(Op.charAt(Op.indexOf(',')+1) == 'X') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'Y') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+32);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'U') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'D') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64+32);
            }
            Memory.replace(k,Pos);
            k++;
        }
        else if(Ind.matches("DEPB")) {
            Pos=PostOctet.get("DEPB");
            if(Op.charAt(Op.indexOf(',')+1) == 'X') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'Y') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+32);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'U') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'D') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64+32);
            }
            Memory.replace(k,Pos);
            k++;
        }
        else if(Ind.matches("DEPD")) {
            Pos=PostOctet.get("DEPD");
            if(Op.charAt(Op.indexOf(',')+1) == 'X') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'Y') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+32);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'U') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'D') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64+32);
            }
            Memory.replace(k,Pos);
            k++;
        }
        else if(Ind.matches("DEP7")) {
            Pos=PostOctet.get("DEP7");
            if(Op.charAt(Op.indexOf(',')+1) == 'X') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2));
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'Y') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+32);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'U') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64);
            }
            else if(Op.charAt(Op.indexOf(',')+1) == 'D') {
                Pos=String.format("%02X", Integer.parseInt(Pos,2)+64+32);
            }
            Memory.replace(k,Pos);
            k++;
            Memory.replace(k,String.format("%02X",Integer.parseInt(Op)));
            k++;
        }
        return Pos;
    }
    void reset() {
        for(Integer i:Memory.keySet())
        {
            Memory.put(i,"FF");
        }
        k=0;
    }


    String[] getValues() {
        String[] T = new String[Memory.size()];
        int c=0;
        for (Integer i : Memory.keySet())
        {
            T[c]= "$"+String.format("%04X",i+Integer.parseInt("8000",16)) + "            " + Memory.get(i);
            c++;
        }
        return T;
    }
}
