package klasser;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class Fil {
    private static FileOutputStream filUt;
    private static FileInputStream filInn;
    private static ObjectOutputStream ut;
    private static ObjectInputStream inn;
    private final static String FILE_PATH_TO_INFO = "turneringer/info.dat";
    private final static String FILE_PATH_TO_FOLDER = "turneringer";

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
            filUt = new FileOutputStream(FILE_PATH_TO_INFO);
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
            File sjekkFil = new File(FILE_PATH_TO_INFO);
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
                System.out.println("info.dat-filen er tom!");
            }


        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



}//SLUTT PÅ KLASSE
