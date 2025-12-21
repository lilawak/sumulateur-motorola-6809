import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.swing.AbstractListModel;

public class Programme {




    static RAM ram;
    static ROM rom;
    static CPU cpu;
    static RAMFrame ramF;
    static ROMFrame romF;
    static CPUFrame cpuF;
    static HashMap<String,Integer> Empilement = new HashMap<String,Integer>();
    static String [] REGS =  new String[] {
            "PC", "U", "Y", "X", "DP", "B", "A", "CCR"
    };



    Programme(RAM a, CPU b, RAMFrame c, CPUFrame d, ROM e, ROMFrame f){

        Programme.ram = a;
        Programme.ramF = c;
        Programme.cpu = b;
        Programme.cpuF = d;
        Programme.rom = e;
        Programme.romF = f;
        Empilement.put("CCR",0);
        Empilement.put("A",0);
        Empilement.put("B",0);
        Empilement.put("DP",0);
        Empilement.put("X",0);
        Empilement.put("Y",0);
        Empilement.put("U",0);
        Empilement.put("PC",0);
    }



    static String[] noArgumentInstructions = new String[] {
            "CLRA", "CLRB", "INCA", "INCB",

            "COMA", "COMB",

            "END"
    };

    static String[] oneArgumentInstructions = new String[] {
            // Data Movement Instructions
            "LDA", "LDB", "LDX", "LDY","LDS", "LDU", "LDD",

            "STA", "STB", "STX", "STY","STS", "STU", "STD",

            //Stack manipulation:
            "PSHS", "PSHU", "PULS", "PULU",

            // Arithmetic and Logic Instructions
            "ADDA", "ADDB", "ADDD", "SUBA", "SUBB", "SUBD",

    };


    static String etendu = "\\$[0-9A-Fa-f]{4}";
    static String etenduIndirect = "\\[\\$[0-9A-Fa-f]+\\]";
    static String Direct = "\\$[0-9A-Fa-f]{2}";
    static String immediat1Oct = "#\\$[0-9A-Fa-f]{2}";
    static String immediat2Oct = "#\\$[0-9A-Fa-f]{4}";
    static String StackADD =  "(PC|U|Y|X|DP|B|A|CCR)(,(PC|U|Y|X|DP|B|A|CCR))*";
    //Indexe

    static String DeplacementNULL= "\\,[[Xx][Yy][Uu][Ss]]";
    static String AutoInc= "\\,[[Xx][Yy][Uu][Ss]]\\+";
    static String AutoInc2= "\\,[[Xx][Yy][Uu][Ss]]\\+\\+";
    static String AutoDec= "\\,\\-[[Xx][Yy][Uu][Ss]]";
    static String AutoDec2= "\\,\\-\\-[[Xx][Yy][Uu][Ss]]";
    static String DeplacementCst4Pos= "([0-9]|1[0-4]),[[Xx][Yy][Uu][Ss]]";
    static String DeplacementCst4Neg= "\\-([0-9]|1[0-5]),[[Xx][Yy][Uu][Ss]]";
    static String DeplacementCst7Pos= "(([1][5-9])|([2-9][0-9])|(1[0-9][0-9])|(2[0-1][0-9])|(22[0-7])),[[Xx][Yy][Uu][Ss]]";
    static String DeplacementCst7Neg= "\\-(([1][5-9])|([2-9][0-9])|(1[0-9][0-9])|(2[0-1][0-9])|(22[0-7])),[[Xx][Yy][Uu][Ss]]";
    static String DeplacementReg= "[[Aa][Bb]],[[Xx][Yy][Uu][Ss]]";
    static String DeplacementD= "[Dd],[[Xx][Yy][Uu][Ss]]";
    //Indexe Inidirect

    static String DeplacementNULLI= "\\[\\,[[Xx][Yy][Uu][Ss]]\\]";
    static String AutoIncI= "\\[\\,[[Xx][Yy][Uu][Ss]]\\+\\]";
    static String AutoInc2I= "\\[\\,[[Xx][Yy][Uu][Ss]]\\+\\+\\]";
    static String AutoDecI= "\\[\\,\\-[[Xx][Yy][Uu][Ss]]\\]";
    static String AutoDec2I= "\\[\\,\\-\\-[[Xx][Yy][Uu][Ss]]\\]";
    static String DeplacementCst4PosI= "\\[([0-9]|1[0-4]),[[Xx][Yy][Uu][Ss]]\\]";
    static String DeplacementCst4NegI= "\\[\\-([0-9]|1[0-5]),[[Xx][Yy][Uu][Ss]]\\]";
    static String DeplacementCst7PosI= "\\[(([1][5-9])|([2-9][0-9])|(1[0-9][0-9])|(2[0-1][0-9])|(22[0-7])),[[Xx][Yy][Uu][Ss]]\\]";
    static String DeplacementCst7NegI= "\\[\\-(([1][5-9])|([2-9][0-9])|(1[0-9][0-9])|(2[0-1][0-9])|(22[0-7])),[[Xx][Yy][Uu][Ss]]\\]";
    static String DeplacementRegI= "\\[[[Aa][Bb]],[[Xx][Yy][Uu][Ss]]\\]";
    static String DeplacementDI= "\\[[Dd],[[Xx][Yy][Uu][Ss]]\\]";
    //ram initialisation

    static String fillRam = ";\\$[0-9A-Fa-f]{4} [dD][bB] \\$[0-9A-Fa-f]{2}";
    //---------
    static Pattern etenduPattern = Pattern.compile(etendu);
    static Pattern DirectPattern = Pattern.compile(Direct);
    static Pattern etenduIndirectPattern = Pattern.compile(etenduIndirect);
    static Pattern immediat1OctPattern = Pattern.compile(immediat1Oct);
    static Pattern immediat2OctPattern = Pattern.compile(immediat2Oct);
    static Pattern StackADDPattern = Pattern.compile(StackADD);


    //Indexe

    static Pattern DeplacementNULLPattern = Pattern.compile(DeplacementNULL);
    static Pattern AutoIncPattern = Pattern.compile(AutoInc);
    static Pattern AutoInc2Pattern = Pattern.compile(AutoInc2);
    static Pattern AutoDecPattern = Pattern.compile(AutoDec);
    static Pattern AutoDec2Pattern = Pattern.compile(AutoDec2);
    static Pattern DeplacementCst4PosPattern = Pattern.compile(DeplacementCst4Pos);
    static Pattern DeplacementCst4NegPattern = Pattern.compile(DeplacementCst4Neg);
    static Pattern DeplacementCst7PosPattern = Pattern.compile(DeplacementCst7Pos);
    static Pattern DeplacementCst7NegPattern = Pattern.compile(DeplacementCst7Neg);
    static Pattern DeplacementRegPattern = Pattern.compile(DeplacementReg);
    static Pattern DeplacementDPattern = Pattern.compile(DeplacementD);
    static Pattern DeplacementNULLPatternI = Pattern.compile(DeplacementNULLI);
    static Pattern AutoIncPatternI = Pattern.compile(AutoIncI);
    static Pattern AutoInc2PatternI = Pattern.compile(AutoInc2I);
    static Pattern AutoDecPatternI = Pattern.compile(AutoDecI);
    static Pattern AutoDec2PatternI = Pattern.compile(AutoDec2I);
    static Pattern DeplacementCst4PosPatternI = Pattern.compile(DeplacementCst4PosI);
    static Pattern DeplacementCst4NegPatternI = Pattern.compile(DeplacementCst4NegI);
    static Pattern DeplacementCst7PosPatternI = Pattern.compile(DeplacementCst7PosI);
    static Pattern DeplacementCst7NegPatternI = Pattern.compile(DeplacementCst7NegI);
    static Pattern DeplacementRegPatternI = Pattern.compile(DeplacementRegI);
    static Pattern DeplacementDPatternI = Pattern.compile(DeplacementDI);



    static Pattern fillRamPattern = Pattern.compile(fillRam);



    static Boolean Syntax(String T)// to check if syntax is correct
    {
        String[] Tab1 = T.split("\\n");

        if(!Tab1[Tab1.length-1].trim().equals("END"))
        {
            return false;
        }
        System.out.println("END checked");

        for(String i:Tab1)
        {
            i = i.trim();
            String[] Tab2 = i.split("\\s+");//ADDA $0000
            System.out.println(i);
            if(fillRamPattern.matcher(i).matches())
            {
                System.out.println("put in ram");
            }
            else if(Arrays.stream(noArgumentInstructions).anyMatch(value -> value.equals(Tab2[0]))) {
                if (Tab2.length == 1 )
                {
                    rom.fill("Inherent", i, null, null);
                    System.out.println("syntax correct");
                }
                else
                {
                    return false;
                }
            }
            else if(Arrays.stream(oneArgumentInstructions).anyMatch(value -> value.equals(Tab2[0]))) {
                if (Tab2.length == 2 )
                {
                    if(Tab2[0].matches("LDA") || Tab2[0].matches("LDB") || Tab2[0].matches("ADDA") || Tab2[0].matches("ADDB") || Tab2[0].matches("SUBA") || Tab2[0].matches("SUBB")) {
                        if(	DeplacementRegPatternI.matcher(Tab2[1]).matches() || DeplacementCst7PosPatternI.matcher(Tab2[1]).matches()||DeplacementCst7NegPatternI.matcher(Tab2[1]).matches() || DeplacementCst4PosPatternI.matcher(Tab2[1]).matches()||DeplacementCst4NegPatternI.matcher(Tab2[1]).matches()||AutoDec2PatternI.matcher(Tab2[1]).matches() || AutoDecPatternI.matcher(Tab2[1]).matches() || AutoInc2PatternI.matcher(Tab2[1]).matches() || AutoIncPatternI.matcher(Tab2[1]).matches() || DeplacementNULLPatternI.matcher(Tab2[1]).matches() ||DeplacementRegPattern.matcher(Tab2[1]).matches() || DeplacementCst7PosPattern.matcher(Tab2[1]).matches()||DeplacementCst7NegPattern.matcher(Tab2[1]).matches() || DeplacementCst4PosPattern.matcher(Tab2[1]).matches()||DeplacementCst4NegPattern.matcher(Tab2[1]).matches()||AutoDec2Pattern.matcher(Tab2[1]).matches() || AutoDecPattern.matcher(Tab2[1]).matches() || AutoInc2Pattern.matcher(Tab2[1]).matches() || AutoIncPattern.matcher(Tab2[1]).matches() || DeplacementNULLPattern.matcher(Tab2[1]).matches() || etenduPattern.matcher(Tab2[1]).matches() || etenduIndirectPattern.matcher(Tab2[1]).matches() || DirectPattern.matcher(Tab2[1]).matches()  || immediat1OctPattern.matcher(Tab2[1]).matches())
                        {

                            if(immediat1OctPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Immediat", Tab2[0], Tab2[1].substring(2), null);
                            }
                            else if(DirectPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Direct", Tab2[0], Tab2[1].substring(1), null);
                            }
                            else if(etenduPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Etendu", Tab2[0], Tab2[1].substring(1), null);
                            }
                            else if(DeplacementNULLPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Indexe", Tab2[0], Tab2[1], "NULL");
                            }
                            else if(DeplacementCst4PosPattern.matcher(Tab2[1]).matches() || DeplacementCst4NegPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Indexe", Tab2[0], Tab2[1], "CST");
                            }
                            else if(AutoIncPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Indexe", Tab2[0], Tab2[1], "INC1");
                            }
                            else if(AutoInc2Pattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Indexe", Tab2[0], Tab2[1], "INC2");
                            }
                            else if(AutoDecPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Indexe", Tab2[0], Tab2[1], "DEC1");
                            }
                            else if(AutoDec2Pattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Indexe", Tab2[0], Tab2[1], "DEC2");
                            }
                            else if(DeplacementRegPattern.matcher(Tab2[1]).matches()) {
                                if(Tab2[0].charAt(0)=='A') {
                                    rom.fill("Indexe", Tab2[0], Tab2[1], "DEPA");
                                }
                                else {
                                    rom.fill("Indexe", Tab2[0], Tab2[1], "DEPB");
                                }
                            }
                            else if(DeplacementDPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Indexe", Tab2[0], Tab2[1], "DEPD");
                            }
                            else if (DeplacementCst7PosPatternI.matcher(Tab2[1]).matches()||DeplacementCst7NegPatternI.matcher(Tab2[1]).matches()) {
                                rom.fill("Indexe", Tab2[0], Tab2[1], "DEP7");
                            }


                            System.out.println(Tab2[0]);
                            System.out.println( Tab2[1]);
                            System.out.println("-------\nsysntax correct 1");
                        }
                        else
                        {
                            System.out.println("false");
                            return false;
                        }
                    }
                    else if(Tab2[0].matches("LDX") || Tab2[0].matches("LDY") || Tab2[0].matches("LDD") || Tab2[0].matches("LDS") || Tab2[0].matches("LDU")) {
                        if(	DeplacementRegPatternI.matcher(Tab2[1]).matches() || DeplacementCst7PosPatternI.matcher(Tab2[1]).matches()||DeplacementCst7NegPatternI.matcher(Tab2[1]).matches() || DeplacementCst4PosPatternI.matcher(Tab2[1]).matches()||DeplacementCst4NegPatternI.matcher(Tab2[1]).matches()||AutoDec2PatternI.matcher(Tab2[1]).matches() || AutoDecPatternI.matcher(Tab2[1]).matches() || AutoInc2PatternI.matcher(Tab2[1]).matches() || AutoIncPatternI.matcher(Tab2[1]).matches() || DeplacementNULLPatternI.matcher(Tab2[1]).matches() ||DeplacementRegPattern.matcher(Tab2[1]).matches() || DeplacementCst7PosPattern.matcher(Tab2[1]).matches()||DeplacementCst7NegPattern.matcher(Tab2[1]).matches() || DeplacementCst4PosPattern.matcher(Tab2[1]).matches()||DeplacementCst4NegPattern.matcher(Tab2[1]).matches()||AutoDec2Pattern.matcher(Tab2[1]).matches() || AutoDecPattern.matcher(Tab2[1]).matches() || AutoInc2Pattern.matcher(Tab2[1]).matches() || AutoIncPattern.matcher(Tab2[1]).matches() || DeplacementNULLPattern.matcher(Tab2[1]).matches() || immediat2OctPattern.matcher(Tab2[1]).matches() ||etenduPattern.matcher(Tab2[1]).matches() || etenduIndirectPattern.matcher(Tab2[1]).matches() || DirectPattern.matcher(Tab2[1]).matches()  )
                        {
                            if(immediat2OctPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Immediat", Tab2[0], Tab2[1].substring(2), null);
                            }
                            else if(DirectPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Direct", Tab2[0], Tab2[1].substring(1), null);
                            }
                            else if(etenduPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Etendu", Tab2[0], Tab2[1].substring(1), null);
                            }

                            System.out.println("sysntax correct 2");
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else if(Tab2[0].matches("STA") || Tab2[0].matches("STB") ||  Tab2[0].matches("STX") || Tab2[0].matches("STY") || Tab2[0].matches("STD") || Tab2[0].matches("STS") || Tab2[0].matches("STU")) {
                        if(	DeplacementRegPatternI.matcher(Tab2[1]).matches() || DeplacementCst7PosPatternI.matcher(Tab2[1]).matches()||DeplacementCst7NegPatternI.matcher(Tab2[1]).matches() || DeplacementCst4PosPatternI.matcher(Tab2[1]).matches()||DeplacementCst4NegPatternI.matcher(Tab2[1]).matches()||AutoDec2PatternI.matcher(Tab2[1]).matches() || AutoDecPatternI.matcher(Tab2[1]).matches() || AutoInc2PatternI.matcher(Tab2[1]).matches() || AutoIncPatternI.matcher(Tab2[1]).matches() || DeplacementNULLPatternI.matcher(Tab2[1]).matches() ||DeplacementRegPattern.matcher(Tab2[1]).matches() || DeplacementCst7PosPattern.matcher(Tab2[1]).matches()||DeplacementCst7NegPattern.matcher(Tab2[1]).matches() || DeplacementCst4PosPattern.matcher(Tab2[1]).matches()||DeplacementCst4NegPattern.matcher(Tab2[1]).matches()||AutoDec2Pattern.matcher(Tab2[1]).matches() || AutoDecPattern.matcher(Tab2[1]).matches() || AutoInc2Pattern.matcher(Tab2[1]).matches() || AutoIncPattern.matcher(Tab2[1]).matches() || DeplacementNULLPattern.matcher(Tab2[1]).matches() || etenduPattern.matcher(Tab2[1]).matches() || etenduIndirectPattern.matcher(Tab2[1]).matches() || DirectPattern.matcher(Tab2[1]).matches())
                        {
                            if(DirectPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Direct", Tab2[0], Tab2[1].substring(1), null);
                            }
                            else if(etenduPattern.matcher(Tab2[1]).matches()) {
                                rom.fill("Etendu", Tab2[0], Tab2[1].substring(1), null);
                            }
                            System.out.println("sysntax correct 3");
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else if(Tab2[0].matches("PSHS") || Tab2[0].matches("PSHU") || Tab2[0].matches("PULS") || Tab2[0].matches("PULU")){
                        if(StackADDPattern.matcher(Tab2[1]).matches())
                        {
                            rom.fill("PULPUSH", Tab2[0], Tab2[1], null);
                            System.out.println("sysntax correct 4");
                        }
                        else
                        {
                            return false;
                        }

                    }
                    else {
                        return false;
                    }

                }
                else {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        cpu.I.val = Tab1[0];
        cpuF.Instruction.setText(cpu.I.val);
        ROMUP();
        return true;
    }

    static void Execute(String T,int c) {
        Integer index;
        String value;

        String[] Tab1 = T.split("\\n");

        cpu.I.val = Tab1[c+1];

        String i = Tab1[c];
        i = i.trim();
        System.out.println(i);
        String[] Tab2 = i.split("\\s+");//ADDA $0000
        if(fillRamPattern.matcher(i).matches())//;$0000 db $FF
        {
            index = Integer.parseInt(Tab2[0].substring(2), 16);
            value =Tab2[2].substring(1);
            ram.Update(index,value);
            RAMUP();
            System.out.println("value" + value +" loaded in ram : " + index);
        }
        else if(Arrays.stream(noArgumentInstructions).anyMatch(v -> v.equals(Tab2[0]))) {
            if(Tab2[0].equals("CLRA")) {
                CLRA();
            }
            else if(Tab2[0].equals("CLRB")) {
                CLRB();
            }
            else if(Tab2[0].equals("COMA")) {
                COMA();
            }
            else if(Tab2[0].equals("CLRB")) {
                CLRB();
            }
            else if(Tab2[0].equals("INCA")) {
                INCA();
            }
            else if(Tab2[0].equals("INCB")) {
                INCB();
            }
            else if(Tab2[0].equals("END")) {
                END();
            }


        }
        else{


            if(Tab2[0].equals("LDA")) {
                if(immediat1OctPattern.matcher(Tab2[1]).matches()) {
                    value = Tab2[1].substring(2);
                    LDAImm(value);
                }
                else
                {
                    LDAInd(Tab2[1]);
                }
            }
            else if(Tab2[0].equals("LDB")) {
                if(immediat1OctPattern.matcher(Tab2[1]).matches()) {
                    value = Tab2[1].substring(2);
                    LDBImm(value);
                }
                else
                {
                    LDBInd(Tab2[1]);
                }
            }
            else if(Tab2[0].equals("LDD")) {
                if(immediat2OctPattern.matcher(Tab2[1]).matches()) {
                    value = Tab2[1].substring(2);
                    LDDImm(value);
                }
                else
                {
                    LDDInd(Tab2[1]);
                }
            }
            else if(Tab2[0].equals("LDX")) {
                if(immediat2OctPattern.matcher(Tab2[1]).matches()) {
                    value = Tab2[1].substring(2);
                    LDXImm(value);
                }
                else
                {
                    LDXInd(Tab2[1]);
                }
            }
            else if(Tab2[0].equals("LDY")) {
                if(immediat2OctPattern.matcher(Tab2[1]).matches()) {
                    value = Tab2[1].substring(2);
                    LDYImm(value);
                }
                else
                {
                    LDYInd(Tab2[1]);
                }
            }
            else if(Tab2[0].equals("LDS")) {
                if(immediat2OctPattern.matcher(Tab2[1]).matches()) {
                    value = Tab2[1].substring(2);
                    LDSImm(value);
                }
                else
                {
                    LDSInd(Tab2[1]);
                }
            }
            else if(Tab2[0].equals("LDU")) {
                if(immediat2OctPattern.matcher(Tab2[1]).matches()) {
                    value = Tab2[1].substring(2);
                    LDUImm(value);
                }
                else
                {
                    LDUInd(Tab2[1]);
                }
            }
            else if(Tab2[0].equals("STA")) {

                STA(Tab2[1]);

            }
            else if(Tab2[0].equals("STB")) {

                STB(Tab2[1]);

            }
            else if(Tab2[0].equals("STX")) {

                STX(Tab2[1]);

            }
            else if(Tab2[0].equals("STY")) {

                STY(Tab2[1]);

            }
            else if(Tab2[0].equals("STU")) {

                STU(Tab2[1]);

            }
            else if(Tab2[0].equals("STS")) {

                STS(Tab2[1]);

            }
            else if(Tab2[0].equals("ADDA")) {
                if(immediat1OctPattern.matcher(Tab2[1]).matches()) {
                    value = Tab2[1].substring(2);
                    ADDAImm(value);
                }
                else
                {
                    ADDAInd(Tab2[1]);
                }
            }
            else if(Tab2[0].equals("ADDB")) {
                if(immediat1OctPattern.matcher(Tab2[1]).matches()) {
                    value = Tab2[1].substring(2);
                    ADDBImm(value);
                }
                else
                {
                    ADDBInd(Tab2[1]);
                }
            }
            else if(Tab2[0].equals("ADDD")) {
                if(immediat2OctPattern.matcher(Tab2[1]).matches()) {
                    value = Tab2[1].substring(2);
                    ADDDImm(value);
                }
                else
                {
                    ADDDInd(Tab2[1]);
                }
            }
            else if(Tab2[0].equals("SUBA")) {
                if(immediat1OctPattern.matcher(Tab2[1]).matches()) {
                    value = Tab2[1].substring(2);
                    SUBAImm(value);
                }
                else
                {
                    SUBAInd(Tab2[1]);
                }
            }
            else if(Tab2[0].equals("SUBB")) {
                if(immediat1OctPattern.matcher(Tab2[1]).matches()) {
                    value = Tab2[1].substring(2);
                    SUBBImm(value);
                }
                else
                {
                    SUBBInd(Tab2[1]);
                }
            }
            else if(Tab2[0].equals("PSHS")) {
                value = Tab2[1];
                PSHS(value);
            }
            else if(Tab2[0].equals("PSHU")) {
                value = Tab2[1];
                PSHU(value);
            }
            else if(Tab2[0].equals("PULS")) {
                value = Tab2[1];
                PULS(value);
            }
            else if(Tab2[0].equals("PULU")) {
                value = Tab2[1];
                PULU(value);
            }




        }
        CPUUP();
        RAMUP();
    }



    static int ADDHandler(String RawADD) {
        int a = 0;
        System.out.println(RawADD);
        if(etenduPattern.matcher(RawADD).matches()) {
            a = Integer.parseInt(RawADD.substring(1), 16);
            nextInst(2);
        }
        else if(DirectPattern.matcher(RawADD).matches()) {
            RawADD =  cpu.DP.val + RawADD.substring(1);
            a = Integer.parseInt(RawADD, 16);
            nextInst(1);
        }
        else if(etenduIndirectPattern.matcher(RawADD).matches()) {
            RawADD = new StringBuilder(new StringBuilder(RawADD.substring(2)).reverse().toString().substring(1)).reverse().toString();
            a = Integer.parseInt(RawADD.substring(1), 16);
            a = Integer.parseInt((ram.Get(a)+ram.Get(a+1)).substring(1), 16);
            nextInst(3);
        }
        else {
            System.out.println(RawADD);
            if(RawADD.charAt(RawADD.length()-1) == ']') {
                RawADD = new StringBuilder (new StringBuilder(RawADD.substring(1)).reverse().toString().substring(1)).reverse().toString();
                a = ADDHandler(RawADD);
                System.out.println("heres a: "+a);
                a = Integer.parseInt((ram.Get(a)+ram.Get(a+1)).substring(1), 16);
                nextInst(1);
            }
            else
            {
                a = ADDIndHandler(RawADD);

            }
        }

        return a;
    }


    static int ADDIndHandler(String RawADD) {
        int Res = 0;
        if(DeplacementNULLPattern.matcher(RawADD).matches()) {//,X
            if(RawADD.charAt(1) == 'X' ) {
                Res = Integer.parseInt(cpu.X.val, 16);
            }
            if(RawADD.charAt(1) == 'Y' ) {
                Res = Integer.parseInt(cpu.Y.val, 16);
            }
            if(RawADD.charAt(1) == 'S' ) {
                Res = Integer.parseInt(cpu.S.val, 16);
            }
            if(RawADD.charAt(1) == 'U' ) {
                Res = Integer.parseInt(cpu.U.val, 16);
            }
            nextInst(1);
        }
        else if(AutoIncPattern.matcher(RawADD).matches()) {//,X+
            if(RawADD.charAt(1) == 'X' ) {
                Res = Integer.parseInt(cpu.X.val, 16);
                cpu.X.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.X.val, 16) + 1
                );
            }
            if(RawADD.charAt(1) == 'Y' ) {
                Res = Integer.parseInt(cpu.Y.val, 16);
                cpu.Y.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.Y.val, 16) + 1
                );
            }
            if(RawADD.charAt(1) == 'S' ) {
                Res = Integer.parseInt(cpu.S.val, 16);
                cpu.S.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.S.val, 16) + 1
                );
            }
            if(RawADD.charAt(1) == 'U' ) {
                Res = Integer.parseInt(cpu.U.val, 16);
                cpu.U.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.U.val, 16) + 1
                );
            }
            nextInst(1);
        }
        else if(AutoInc2Pattern.matcher(RawADD).matches()) {//,X++
            if(RawADD.charAt(1) == 'X' ) {
                Res = Integer.parseInt(cpu.X.val, 16);
                cpu.X.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.X.val, 16) + 2
                );
            }
            if(RawADD.charAt(1) == 'Y' ) {
                Res = Integer.parseInt(cpu.Y.val, 16);
                cpu.Y.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.Y.val, 16) + 2
                );
            }
            if(RawADD.charAt(1) == 'S' ) {
                Res = Integer.parseInt(cpu.S.val, 16);
                cpu.S.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.S.val, 16) + 2
                );
            }
            if(RawADD.charAt(1) == 'U' ) {
                Res = Integer.parseInt(cpu.U.val, 16);
                cpu.U.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.U.val, 16) + 2
                );
            }
            nextInst(1);
        }
        else if(AutoDecPattern.matcher(RawADD).matches()) {//,-X
            if(RawADD.charAt(2) == 'X' ) {
                Res = Integer.parseInt(cpu.X.val, 16);
                cpu.X.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.X.val, 16) - 1
                );
            }
            if(RawADD.charAt(2) == 'Y' ) {
                Res = Integer.parseInt(cpu.Y.val, 16);
                cpu.Y.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.Y.val, 16) - 1
                );
            }
            if(RawADD.charAt(2) == 'S' ) {
                Res = Integer.parseInt(cpu.S.val, 16);
                cpu.S.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.S.val, 16) - 1
                );
            }
            if(RawADD.charAt(2) == 'U' ) {
                Res = Integer.parseInt(cpu.U.val, 16);
                cpu.U.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.U.val, 16) - 1
                );
            }
            nextInst(1);
        }
        else if(AutoDec2Pattern.matcher(RawADD).matches()) {//,--X
            if(RawADD.charAt(3) == 'X' ) {
                Res = Integer.parseInt(cpu.X.val, 16);
                cpu.X.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.X.val, 16) - 2
                );
            }
            if(RawADD.charAt(3) == 'Y' ) {
                Res = Integer.parseInt(cpu.Y.val, 16);
                cpu.Y.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.Y.val, 16) - 2
                );
            }
            if(RawADD.charAt(3) == 'S' ) {
                Res = Integer.parseInt(cpu.S.val, 16);
                cpu.S.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.S.val, 16) - 2
                );
            }
            if(RawADD.charAt(3) == 'U' ) {
                Res = Integer.parseInt(cpu.U.val, 16);
                cpu.U.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.U.val, 16) - 2
                );
            }
            nextInst(1);
        }
        else if(DeplacementCst4PosPattern.matcher(RawADD).matches()||DeplacementCst4NegPattern.matcher(RawADD).matches()) {

            int i=0;
            String a = ""+RawADD.charAt(i);

            while(a.length()!=RawADD.indexOf(',')) {
                i++;
                a = a+RawADD.charAt(i);

            }

            if(RawADD.charAt(RawADD.indexOf(',')+1) == 'X' ) {

                Res = Integer.parseInt(cpu.X.val, 16)+Integer.parseInt(a);

            }
            if(RawADD.charAt(RawADD.indexOf(',')+1) == 'Y' ) {

                Res = Integer.parseInt(cpu.Y.val, 16)+Integer.parseInt(a);

            }
            if(RawADD.charAt(RawADD.indexOf(',')+1) == 'S' ) {

                Res = Integer.parseInt(cpu.S.val, 16)+Integer.parseInt(a);

            }
            if(RawADD.charAt(RawADD.indexOf(',')+1) == 'U' ) {

                Res = Integer.parseInt(cpu.U.val, 16)+Integer.parseInt(a);

            }
            nextInst(1);
        }
        else if(DeplacementCst7PosPattern.matcher(RawADD).matches()||DeplacementCst7NegPattern.matcher(RawADD).matches()) {

            int i=0;
            String a = ""+RawADD.charAt(i);

            while(a.length()!=RawADD.indexOf(',')) {
                i++;
                a = a+RawADD.charAt(i);

            }

            if(RawADD.charAt(RawADD.indexOf(',')+1) == 'X' ) {

                Res = Integer.parseInt(cpu.X.val, 16)+Integer.parseInt(a);

            }
            if(RawADD.charAt(RawADD.indexOf(',')+1) == 'Y' ) {

                Res = Integer.parseInt(cpu.Y.val, 16)+Integer.parseInt(a);

            }
            if(RawADD.charAt(RawADD.indexOf(',')+1) == 'S' ) {

                Res = Integer.parseInt(cpu.S.val, 16)+Integer.parseInt(a);

            }
            if(RawADD.charAt(RawADD.indexOf(',')+1) == 'U' ) {

                Res = Integer.parseInt(cpu.U.val, 16)+Integer.parseInt(a);

            }
            nextInst(2);
        }
        else if(DeplacementRegPattern.matcher(RawADD).matches()) {
            int a;

            if(RawADD.charAt(0) == 'A') {
                a = Integer.parseInt(cpu.A.val);
            }
            else {
                a = Integer.parseInt(cpu.B.val);
            }

            if(RawADD.charAt(RawADD.indexOf(',')+1) == 'X' ) {

                Res = Integer.parseInt(cpu.X.val, 16)+a;

            }
            if(RawADD.charAt(RawADD.indexOf(',')+1) == 'Y' ) {

                Res = Integer.parseInt(cpu.Y.val, 16)+a;

            }
            if(RawADD.charAt(RawADD.indexOf(',')+1) == 'S' ) {

                Res = Integer.parseInt(cpu.S.val, 16)+a;

            }
            if(RawADD.charAt(RawADD.indexOf(',')+1) == 'U' ) {

                Res = Integer.parseInt(cpu.U.val, 16)+a;

            }
            nextInst(1);
        }
        return Res;
    }


    static void nextInst (int x) {
        cpu.PC.val = String.format(
                "%02X",
                Integer.parseInt(cpu.PC.val, 16) + x
        );
    }


    static void CPUUP() {
        cpuF.RegA.setText(cpu.A.val);
        cpuF.RegB.setText(cpu.B.val);
        cpuF.RegX.setText(cpu.X.val);
        cpuF.RegY.setText(cpu.Y.val);
        cpuF.RegS.setText(cpu.S.val);
        cpuF.RegU.setText(cpu.U.val);
        cpuF.Instruction.setText(cpu.I.val);
        cpuF.RegDP.setText(cpu.DP.val);
        cpuF.RegFlags.setText(cpu.F.val);
        cpuF.Instruction.setText(cpu.I.val);
        cpuF.RegPC.setText(cpu.PC.val);

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    static void RAMUP() {
        ramF.list.setModel(new AbstractListModel() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;
            String[] values = ram.getValues();
            public int getSize() {
                return values.length;
            }
            public Object getElementAt(int index) {
                return values[index];
            }
        });
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    static void ROMUP() {
        romF.list.setModel(new AbstractListModel() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;
            String[] values = rom.getValues();
            public int getSize() {
                return values.length;
            }
            public Object getElementAt(int index) {
                return values[index];
            }
        });
    }

    static void END() {
        nextInst(1);
        nextInst(1);
    }
    static void CLRA() {
        cpu.A.val = "00";
        nextInst(1);
        flags("00");
        nextInst(1);
    }

    static void CLRB() {
        cpu.B.val = "00";
        nextInst(1);
        flags("00");
        nextInst(1);
    }

    static void COMA() {

        String val = Integer.toBinaryString(Integer.parseInt(cpu.A.val, 16));
        while (val.length() < 8) {
            val = "0" + val;
        }
        val = val.replace('0', '2').replace('1', '0').replace('2','1');

        BigInteger bigInt = new BigInteger(val, 2);

        val = bigInt.toString(16).toUpperCase();

        cpu.A.val=flags(val);

        nextInst(1);
    }

    static void COMB() {
        String val = Integer.toBinaryString(Integer.parseInt(cpu.B.val, 16));
        while (val.length() < 8) {
            val = "0" + val;
        }
        val = val.replace('0', '2').replace('1', '0').replace('2','1');

        BigInteger bigInt = new BigInteger(val, 2);

        val = bigInt.toString(16).toUpperCase();

        cpu.B.val=flags(val);

        nextInst(1);
    }

    static void INCA() {
        String val = String.format("%02X", Integer.parseInt(cpu.A.val, 16)+1);

        cpu.A.val=flags(val);

        nextInst(1);
    }

    static void INCB() {
        String val = String.format("%02X", Integer.parseInt(cpu.B.val, 16)+1);

        cpu.B.val=flags(val);

        nextInst(1);
    }

    static void LDAInd(String index) {
        int a = ADDHandler(index);
        System.out.println(a);
        String val = ram.Get(a);
        cpu.A.val=flags(val);
        nextInst(1);
    }
    static void LDAImm(String val) {
        cpu.A.val = flags(val);
        nextInst(2);
    }
    static void LDBInd(String index) {
        int a =  ADDHandler(index);
        System.out.println(a);
        String val = ram.Get(a);
        cpu.B.val=flags(val);
        nextInst(1);
    }

    static void LDBImm(String val) {
        cpu.B.val = flags(val);
        nextInst(2);
    }
    static void LDXInd(String index) {
        int a = ADDHandler(index);
        System.out.println(a);
        System.out.println(a+1);
        cpu.X.val = flags(ram.Get(a)+""+ram.Get(a+1));
        nextInst(1);
    }

    static void LDXImm(String val) {
        cpu.X.val = flags(val);
        nextInst(3);
    }

    static void LDYInd(String index) {
        int a = ADDHandler(index);
        System.out.println(a);
        cpu.Y.val = flags(ram.Get(a)+""+ram.Get(a+1));
        nextInst(2);
    }

    static void LDYImm(String val) {
        cpu.Y.val = flags(val);
        nextInst(4);
    }



    static void LDDInd(String index) {
        int a =  ADDHandler(index);
        System.out.println(a);
        flags(ram.Get(a)+ram.Get(a+1));
        cpu.A.val = ram.Get(a);
        cpu.B.val = ram.Get(a+1);
        nextInst(1);
    }

    static void LDDImm(String val) {
        System.out.println("insude dmdnaiod");
        flags(val);
        cpu.A.val = val.substring(0,2);
        cpu.B.val = val.substring(2);
        nextInst(3);
    }

    static void LDSInd(String index) {
        int a = ADDHandler(index);
        System.out.println(a);
        cpu.S.val = flags(ram.Get(a)+""+ram.Get(a+1));
        nextInst(2);
    }

    static void LDSImm(String val) {
        cpu.S.val = flags(val);
        nextInst(4);
    }
    static void LDUInd(String index) {
        int a = ADDHandler(index);
        System.out.println(a);
        cpu.U.val = flags(ram.Get(a)+""+ram.Get(a+1));
        nextInst(1);
    }

    static void LDUImm(String val) {
        cpu.U.val = flags(val);
        nextInst(3);
    }

    static void STA(String tab2) {
        ram.Update(ADDHandler(tab2),flags(cpu.A.val));
        nextInst(1);
    }

    static void STB(String tab2) {
        ram.Update(ADDHandler(tab2),flags(cpu.B.val));
        nextInst(1);
    }

    static void STX(String tab2) {
        int a = ADDHandler(tab2);
        flags(cpu.X.val);
        ram.Update(a,cpu.X.val.substring(0, 2));
        ram.Update(a+1,cpu.X.val.substring(2));
        nextInst(1);
    }

    static void STY(String tab2) {
        int a = ADDHandler(tab2);
        flags(cpu.Y.val);
        ram.Update(a,cpu.Y.val.substring(0, 2));
        ram.Update(a+1,cpu.Y.val.substring(2));
        nextInst(2);
    }

    static void STS(String tab2) {
        int a = ADDHandler(tab2);
        flags(cpu.S.val);
        ram.Update(a,cpu.S.val.substring(0, 2));
        ram.Update(a+1,cpu.S.val.substring(2));
        nextInst(2);
    }
    static void STU(String tab2) {
        int a = ADDHandler(tab2);
        flags(cpu.U.val);
        ram.Update(a,cpu.U.val.substring(0, 2));
        ram.Update(a+1,cpu.U.val.substring(2));
        nextInst(1);
    }
    static void STD(String tab2) {
        int a = ADDHandler(tab2);
        flags(cpu.A.val+cpu.B.val);
        ram.Update(a,cpu.A.val);
        ram.Update(a+1,cpu.B.val);
        nextInst(1);
    }

    static void ADDAInd(String tab2) {
        String val = String.format(
                "%02X",
                Integer.parseInt(cpu.A.val, 16) + Integer.parseInt(ram.Get(ADDHandler(tab2)), 16)
        );
        val=flags(val);
        cpu.A.val=val;
        nextInst(1);
    }

    static void ADDAImm(String val) {
        val = String.format(
                "%02X",
                Integer.parseInt(cpu.A.val, 16) + Integer.parseInt(val, 16)
        );
        val=flags(val);
        cpu.A.val=val;
        nextInst(1);
    }

    static void ADDBInd(String tab2) {
        String val = String.format(
                "%02X",
                Integer.parseInt(cpu.B.val, 16) + Integer.parseInt(ram.Get(ADDHandler(tab2)), 16)
        );
        val=flags(val);
        cpu.B.val=val;
        nextInst(1);
    }

    static void ADDBImm(String val) {
        val = String.format(
                "%02X",
                Integer.parseInt(cpu.B.val, 16) + Integer.parseInt(val, 16)
        );
        val=flags(val);
        cpu.B.val=val;
        nextInst(1);
    }

    static void ADDDInd(String tab2) {
        int a =ADDHandler(tab2);
        String val1= cpu.A.val + cpu.B.val;
        String val =ram.Get(a) + ram.Get(a+1);
        val = String.format(
                "%02X",
                Integer.parseInt(val1, 16) + Integer.parseInt(val, 16)
        );
        while (val.length() < 4) {
            val = "0" + val;
        }
        val=flags(val);
        cpu.A.val = val.substring(0,2);
        cpu.B.val = val.substring(2);
        nextInst(1);
    }

    static void ADDDImm(String val) {
        String val1= cpu.A.val + cpu.B.val;
        val = String.format(
                "%02X",
                Integer.parseInt(val1, 16) + Integer.parseInt(val, 16)
        );
        while (val.length() < 4) {
            val = "0" + val;
        }
        val=flags(val);
        cpu.A.val = val.substring(0,2);
        cpu.B.val = val.substring(2);
        nextInst(1);
    }


    static void SUBAInd(String tab2) {
        String val = String.format(
                "%02X",
                Integer.parseInt(cpu.A.val, 16) - Integer.parseInt(ram.Get(ADDHandler(tab2)), 16)
        );
        val=flags(val);
        cpu.A.val=val;
        nextInst(1);
    }

    static void SUBAImm(String val) {
        val = String.format(
                "%02X",
                Integer.parseInt(cpu.A.val, 16) - Integer.parseInt(val, 16)
        );
        val=flags(val);
        cpu.A.val=val;
        nextInst(1);
    }

    static void SUBBInd(String tab2) {
        String val = String.format(
                "%02X",
                Integer.parseInt(cpu.B.val, 16) - Integer.parseInt(ram.Get(ADDHandler(tab2)), 16)
        );
        val=flags(val);
        cpu.B.val=val;
        nextInst(1);
    }

    static void SUBBImm(String val) {
        val = String.format(
                "%02X",
                Integer.parseInt(cpu.B.val, 16) - Integer.parseInt(val, 16)
        );
        val=flags(val);
        cpu.B.val=val;
        nextInst(1);
    }


    static void PULS(String A) {

        String T="";
        if(A.charAt(0) == 'A') {
            T = ram.Get(Integer.parseInt(cpu.S.val, 16));
            cpu.S.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.S.val, 16) + 1
            );
            T = flags(T);
            cpu.A.val=T;
        }
        else if(A.charAt(0) == 'B') {
            T = ram.Get(Integer.parseInt(cpu.S.val, 16));
            cpu.S.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.S.val, 16) + 1
            );
            T = flags(T);
            cpu.B.val=T;
        }
        else if(A.charAt(0) == 'D') {
            T = ram.Get(Integer.parseInt(cpu.S.val,16));
            cpu.S.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.S.val, 16) + 1
            );
            T = flags(T);
            cpu.DP.val=T;
        }
        else if(A.charAt(0) == 'X') {
            T = ram.Get(Integer.parseInt(cpu.S.val, 16));
            cpu.S.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.S.val, 16) + 1
            );
            T = T + ram.Get(Integer.parseInt(cpu.S.val, 16));
            cpu.S.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.S.val, 16) + 1
            );
            T = flags(T);
            cpu.X.val=T;
        }
        else if(A.charAt(0) == 'Y') {
            T = ram.Get(Integer.parseInt(cpu.S.val, 16));
            cpu.S.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.S.val, 16) + 1
            );
            T = T + ram.Get(Integer.parseInt(cpu.S.val));
            cpu.S.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.S.val, 16) + 1
            );
            T = flags(T);
            cpu.Y.val=T;
        }


        while (A.contains(",")){
            if(A.contains(",")) {
                A = A.substring(A.indexOf(",")+1);
            }
            if(A.charAt(0) == 'A') {
                T = ram.Get(Integer.parseInt(cpu.S.val, 16));
                cpu.S.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.S.val, 16) + 1
                );
                T = flags(T);
                cpu.A.val=T;
            }
            else if(A.charAt(0) == 'B') {
                T = ram.Get(Integer.parseInt(cpu.S.val, 16));
                cpu.S.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.S.val, 16) + 1
                );
                T = flags(T);
                cpu.B.val=T;
            }
            else if(A.charAt(0) == 'D' || A.charAt(1) == 'P') {
                T = ram.Get(Integer.parseInt(cpu.S.val, 16));
                cpu.S.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.S.val, 16) + 1
                );
                T = flags(T);
                cpu.DP.val=T;
            }
            else if(A.charAt(0) == 'X') {
                T = ram.Get(Integer.parseInt(cpu.S.val, 16));
                cpu.S.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.S.val, 16) + 1
                );
                T = T + ram.Get(Integer.parseInt(cpu.S.val, 16));
                cpu.S.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.S.val, 16) + 1
                );
                T = flags(T);
                cpu.X.val=T;
            }
            else if(A.charAt(0) == 'Y') {
                T = ram.Get(Integer.parseInt(cpu.S.val, 16));
                cpu.S.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.S.val, 16) + 1
                );
                T = T + ram.Get(Integer.parseInt(cpu.S.val, 16));
                cpu.S.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.S.val, 16) + 1
                );
                T = flags(T);
                cpu.Y.val=T;
            }

        }

        nextInst(2);

    }

    static void PULU(String A) {
        String T="";
        if(A.charAt(0) == 'A') {
            T = ram.Get(Integer.parseInt(cpu.U.val, 16));
            cpu.U.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.U.val, 16) + 1
            );
            T = flags(T);
            cpu.A.val=T;
        }
        else if(A.charAt(0) == 'B') {
            T = ram.Get(Integer.parseInt(cpu.U.val, 16));
            cpu.U.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.U.val, 16) + 1
            );
            T = flags(T);
            cpu.B.val=T;
        }
        else if(A.charAt(0) == 'D') {
            T = ram.Get(Integer.parseInt(cpu.U.val, 16));
            cpu.U.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.U.val, 16) + 1
            );
            T = flags(T);
            cpu.DP.val=T;
        }
        else if(A.charAt(0) == 'X') {
            T = ram.Get(Integer.parseInt(cpu.U.val, 16));
            cpu.U.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.U.val, 16) + 1
            );
            T = T + ram.Get(Integer.parseInt(cpu.U.val, 16));
            cpu.U.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.U.val, 16) + 1
            );
            T = flags(T);
            cpu.X.val=T;
        }
        else if(A.charAt(0) == 'Y') {
            T = ram.Get(Integer.parseInt(cpu.U.val, 16));
            cpu.U.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.U.val, 16) + 1
            );
            T = T + ram.Get(Integer.parseInt(cpu.U.val, 16));
            cpu.U.val = String.format(
                    "%04X",
                    Integer.parseInt(cpu.U.val, 16) + 1
            );
            T = flags(T);
            cpu.Y.val=T;
        }


        while (A.contains(",")){
            if(A.contains(",")) {
                A = A.substring(A.indexOf(",")+1);
            }
            if(A.charAt(0) == 'A') {
                T = ram.Get(Integer.parseInt(cpu.U.val, 16));
                cpu.U.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.U.val, 16) + 1
                );
                T = flags(T);
                cpu.A.val=T;
            }
            else if(A.charAt(0) == 'B') {
                T = ram.Get(Integer.parseInt(cpu.U.val, 16));
                cpu.U.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.U.val, 16) + 1
                );
                T = flags(T);
                cpu.B.val=T;
            }
            else if(A.charAt(0) == 'D' || A.charAt(1) == 'P') {
                T = ram.Get(Integer.parseInt(cpu.U.val, 16));
                cpu.U.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.U.val, 16) + 1
                );
                T = flags(T);
                cpu.DP.val=T;
            }
            else if(A.charAt(0) == 'X') {
                T = ram.Get(Integer.parseInt(cpu.U.val, 16));
                cpu.U.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.U.val, 16) + 1
                );
                T = T + ram.Get(Integer.parseInt(cpu.U.val, 16));
                cpu.U.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.U.val, 16) + 1
                );
                T = flags(T);
                cpu.X.val=T;
            }
            else if(A.charAt(0) == 'Y') {
                T = ram.Get(Integer.parseInt(cpu.U.val, 16));
                cpu.U.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.U.val, 16) + 1
                );
                T = T + ram.Get(Integer.parseInt(cpu.U.val, 16));
                cpu.U.val = String.format(
                        "%04X",
                        Integer.parseInt(cpu.U.val, 16) + 1
                );
                T = flags(T);
                cpu.Y.val=T;
            }

        }
        nextInst(2);
    }

    static void PSHS(String A) {
        Stack_Handler(A);
        nextInst(2);
        for(String i:REGS) {

            if(Empilement.get(i)==1) {
                if(i.matches("PC")) {
                    cpu.S.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.S.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.S.val, 16), cpu.PC.val.substring(2));
                    cpu.S.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.S.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.S.val, 16), cpu.PC.val.substring(0,2));
                }
                else if(i.matches("U")) {
                    cpu.S.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.S.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.S.val, 16), cpu.U.val.substring(2));
                    cpu.S.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.S.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.S.val, 16), cpu.U.val.substring(0,2));
                }
                else if(i.matches("Y")) {
                    cpu.S.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.S.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.S.val, 16), cpu.Y.val.substring(2));
                    cpu.S.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.S.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.S.val, 16), cpu.Y.val.substring(0,2));
                }
                else if(i.matches("X")) {
                    cpu.S.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.S.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.S.val, 16), cpu.X.val.substring(2));
                    cpu.S.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.S.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.S.val, 16), cpu.X.val.substring(0,2));
                }
                else if(i.matches("DP")) {
                    cpu.S.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.S.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.S.val, 16), cpu.DP.val);
                }
                else if(i.matches("B")) {
                    cpu.S.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.S.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.S.val, 16), cpu.B.val);
                }
                else if(i.matches("A")) {
                    cpu.S.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.S.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.S.val, 16), cpu.A.val);
                }

            }
        }
    }

    static void PSHU(String A) {
        Stack_Handler(A);
        nextInst(2);
        for(String i:REGS) {

            if(Empilement.get(i)==1) {
                if(i.matches("PC")) {
                    cpu.U.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.U.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.U.val, 16), cpu.PC.val.substring(2));
                    cpu.U.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.U.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.U.val, 16), cpu.PC.val.substring(0,2));
                }
                else if(i.matches("U")) {
                    cpu.U.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.U.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.U.val, 16), cpu.U.val.substring(2));
                    cpu.U.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.U.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.U.val, 16), cpu.U.val.substring(0,2));
                }
                else if(i.matches("Y")) {
                    cpu.U.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.U.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.U.val, 16), cpu.Y.val.substring(2));
                    cpu.U.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.U.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.U.val, 16), cpu.Y.val.substring(0,2));
                }
                else if(i.matches("X")) {
                    cpu.U.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.U.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.U.val, 16), cpu.X.val.substring(2));
                    cpu.U.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.U.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.U.val, 16), cpu.X.val.substring(0,2));
                }
                else if(i.matches("DP")) {
                    cpu.U.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.U.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.U.val, 16), cpu.DP.val);
                }
                else if(i.matches("B")) {
                    cpu.U.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.U.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.U.val, 16), cpu.B.val);
                }
                else if(i.matches("A")) {
                    cpu.U.val = String.format(
                            "%04X",
                            Integer.parseInt(cpu.U.val, 16) - 1
                    );
                    ram.Update(Integer.parseInt(cpu.U.val, 16), cpu.A.val);
                }

            }
        }
    }


    static void Stack_Handler(String IND) {//PSHS X,A,B
        for(String i : Empilement.keySet()) {
            if(IND.contains(i)) {
                Empilement.replace(i,1);
            }
            else
            {
                Empilement.replace(i,0);
            }
        }
    }



    static String flags(String val) {

        val = CarryF(val);
        ZeroF(val);
        NegF(val);
        return val;
    }


    static void ZeroF(String val) {
        if(val.matches("0000") || val.matches("00"))
        {
            System.out.println("zero");
            //carryflag change
            char[] charArray = cpu.F.val.toCharArray();

            // Change the character at the specified index
            charArray[5] = '1';

            // Create a new string with the modified char array
            cpu.F.val = new String(charArray);
        }else {

            char[] charArray = cpu.F.val.toCharArray();

            // Change the character at the specified index
            charArray[5] = '0';

            // Create a new string with the modified char array
            cpu.F.val = new String(charArray);
        }
    }

    static String CarryF(String val) {
        if(val.length() == 5 || val.length() == 3)
        {
            System.out.println("there's a carry");
            val= val.substring(1);
            //carryflag change
            char[] charArray = cpu.F.val.toCharArray();

            // Change the character at the specified index
            charArray[7] = '1';

            // Create a new string with the modified char array
            cpu.F.val = new String(charArray);
        }

        return val;
    }

    static void NegF(String val) {
        if(val.charAt(0)=='F' || val.charAt(0)=='E'|| val.charAt(0)=='D' || val.charAt(0)=='C'|| val.charAt(0)=='B'|| val.charAt(0)=='A'|| val.charAt(0)=='9' || val.charAt(0) == '8')
        {
            System.out.println("Neg");
            //carryflag change
            char[] charArray = cpu.F.val.toCharArray();

            // Change the character at the specified index
            charArray[4] = '1';

            // Create a new string with the modified char array
            cpu.F.val = new String(charArray);
        }else {

            char[] charArray = cpu.F.val.toCharArray();

            // Change the character at the specified index
            charArray[4] = '0';

            // Create a new string with the modified char array
            cpu.F.val = new String(charArray);
        }
    }


}
