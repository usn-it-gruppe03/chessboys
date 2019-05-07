package klasser;

import java.io.*;

public class Fil implements Serializable {
    FileOutputStream filUt;
    FileInputStream filInn;
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
                filUt = new FileOutputStream("/turneringer/" + filInn);
                ut = new ObjectOutputStream(filUt);
                ut.writeObject(objekt);
                ut.close();
                filUt.close();
                System.out.println("Serialized data is now saved in ../turneringer/" + filInn);
                /*filInn ikke eksisterer i turnering*/
            }else {
                filUt = new FileOutputStream("/turneringer/" + /*turneringsNavn +*/ "/" + filInn);
                ut = new ObjectOutputStream(filUt);
                ut.writeObject(objekt);
                ut.close();
                filUt.close();
                System.out.println("Serialized data is now saved in ../turneringer/" + /*turneringsNavn +*/ "/" + filInn);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lesFraFil() {

    }

    public static Object deSerialization(String file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        return object;
    }

    public void søk(String spesifikkTurnering, String filNavn) {
        try {
            filInn = new FileInputStream("admapp/src/turneringer/" + spesifikkTurnering + "/" + filNavn );
            BufferedInputStream bis = new BufferedInputStream(filInn);
            inn = new ObjectInputStream(bis);
            Object object = inn.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
