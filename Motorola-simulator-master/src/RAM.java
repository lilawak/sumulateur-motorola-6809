import java.util.HashMap;

public class RAM {
    HashMap<Integer,String> Memory = new HashMap<Integer,String>();

    RAM() //memory initial
    {
        int i;
        for(i=0 ; i<= 1023 ; i++)
        {
            Memory.put(i,"00");
        }
    }
    //Made By Nimo:
    void reset() //reseter
    {
        for(Integer i : Memory.keySet())
        {
            Memory.replace(i,"00");
        }
    }

    String Get(Integer address) {
        return Memory.get(address);
    }

    void Update(Integer address, String val)//changer la valeur d'une case
    {
        Memory.replace(address, val);
    }

    String[] getValues() {
        String[] T = new String[1024];
        for(Integer i : Memory.keySet())
        {
            T[i] = "$" + String.format("%04X", i) + "            " + Memory.get(i);
        }
        return T;
    }

    @Override
    public String toString() {
        String txt = "";
        for(Integer i : Memory.keySet()) {
            txt = txt.concat(String.valueOf(i));
            txt = txt.concat("   = ");
            txt = txt.concat(Memory.get(i));
            txt = txt.concat("\n");
        }
        return txt;
    }
}

