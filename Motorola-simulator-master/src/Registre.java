public class Registre {

    //gonna make child classes (registre_acc, registre adressage ...)

    String val; //valeur
    String nom;
    RAM ram;
    Registre(String nom){
        if(nom.matches("A")||nom.matches("B")||nom.matches("DP"))
        {
            this.val = "00";
        }
        else if (nom.matches("PC"))
        {
            this.val = "8000";
        }
        else if (nom.matches("F"))
        {
            this.val = "00000100";
        }
        else if (nom.matches("I"))
        {
            this.val = "";
        }
        else
        {
            this.val = "0000";
        }
        this.nom = nom;
    }



}

