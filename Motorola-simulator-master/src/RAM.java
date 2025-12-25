import java.util.HashMap;

public class RAM {
    HashMap<Integer,String> Memory = new HashMap<Integer,String>();
    //memoire principale
    RAM()
    {
        int i;
        for(i=0 ; i<= 2025 ; i++)
        {
            Memory.put(i,"00");
        }
    }

    String Get(Integer address) {
        return Memory.get(address);
    }

    void Update(Integer address, String val)
    {
        Memory.replace(address, val);
    }
    void reset()
    {
        for(Integer i : Memory.keySet())
        {
            Memory.replace(i,"00");
        }
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
    String[] getValues() {
        String[] T = new String[2026];
        for(Integer i : Memory.keySet())
        {
            T[i] = "Adresse "+ String.format("%04X", i) + "            " + Memory.get(i);
        }
        return T;
    }
}


