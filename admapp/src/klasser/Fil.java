package klasser;

import java.io.*;
import java.util.Arrays;

public class Fil implements Serializable {
    FileOutputStream fil;
    ObjectOutputStream ut;
    ObjectInputStream inn;

    /** TODO
     * List opp Turnerings mapper / return valgt turnerings mappe?
     * Søk fra filer, må finne spesifikk mappe
     * søk mapper og list opp turneringer
     *
     * */

    public void skrivTilFil(String filInn, Object objekt) {

        try {

            /*IF Spørring TODO... bruk søk metode for og finne ut om filInn eksisterer i "Turneringer" mappen*/
            if (true) {
                fil = new FileOutputStream("/turneringer/" + filInn);
                ut = new ObjectOutputStream(fil);
                ut.writeObject(objekt);
                ut.close();
                fil.close();
                System.out.println("Serialized data is now saved in ../turneringer/" + filInn);
                /*filInn ikke eksisterer i turnering*/
            }else {
                fil = new FileOutputStream("/turneringer/" + /*turneringsNavn +*/ "/" + filInn);
                ut = new ObjectOutputStream(fil);
                ut.writeObject(objekt);
                ut.close();
                fil.close();
                System.out.println("Serialized data is now saved in ../turneringer/" + /*turneringsNavn +*/ "/" + filInn);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lesFraFil() {

    }

    public void søk() {
        File file = new File("admapp/src/turneringer");
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        System.out.println(Arrays.toString(directories));
    }

    public String[] søkTurneringer() {
        File fil = new File("admapp/src/turneringer");
        String[] mapper = fil.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        return mapper;
    }//FERDIG ?

}//SLUTT PÅ KLASSE
