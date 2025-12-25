public class Registre {

    String val;
    String nom;
    public Registre(String nom){
        if(nom.equals("A")||nom.equals("B")||nom.equals("DP"))
        {
            this.val = "00";
        }
        else if (nom.equals("F"))
        {
            this.val = "00000100";
        }
        else if (nom.equals("PC"))
        {
            this.val = "8000";
        }
        else if (nom.equals("I"))
        {
            this.val = "Donner instruction";
        }
        else
        {
            this.val = "0000";
        }
        this.nom = nom;
    }



}


