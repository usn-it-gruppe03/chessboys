package klasser;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class Fil {
    private static FileOutputStream filUt;
    private static FileInputStream filInn;
    private static ObjectOutputStream ut;
    private static ObjectInputStream inn;

    /** TODO
     * List opp Turnerings mapper / return valgt turnerings mappe?
     * Søk fra filer, må finne spesifikk mappe
     * søk mapper og list opp turneringer
     *
     * */

    /**
     *
     *
     * */
    public static void leggTilObjekt(Object objekt) {
        try {
            filUt = new FileOutputStream("admapp/src/turneringer/info.dat");
            ut = new ObjectOutputStream(filUt);

            ut.writeObject(objekt);

            ut.close();
            filUt.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList hentObjekt() {
        try {
            File sjekkFil = new File("admapp/src/turneringer/info.dat");
            if(sjekkFil.length() > 0){
                filInn = new FileInputStream(sjekkFil);
                inn = new ObjectInputStream(filInn);
                ArrayList<Object> objectList = new ArrayList<>();

                Object objekt1 = (Object) inn.readObject();

                objectList.addAll((Collection<?>) objekt1);

                if(objectList.size() != 0){
                    return objectList;
                }
                inn.close();
                filInn.close();

            }else{
                System.out.println(".dat er fuckings tom!");
            }


        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*METODE deSerialization er for EKSEMEPEL*/
    /*public static Object deSerialization(String file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        return object;
    }*/

    public Object søk(String spesifikkTurnering, String filNavn) {
        try {
            filInn = new FileInputStream("admapp/src/turneringer/" + spesifikkTurnering + "/" + filNavn);
            BufferedInputStream bis = new BufferedInputStream(filInn);
            inn = new ObjectInputStream(bis);
            Object object = inn.readObject();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
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
