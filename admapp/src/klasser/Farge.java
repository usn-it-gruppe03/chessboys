package klasser;

import java.util.ArrayList;
import java.util.Arrays;

public enum Farge {
    SORT,HVIT;

    public static ArrayList<String> getFarger(){
        return new ArrayList<String>(Arrays.asList(
                HVIT.toString(),
                SORT.toString()
        ));
    }
}
