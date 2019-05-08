package klasser;

public enum BrikkeType {

    KONGE_HVIT      ("bilde/ikon/sjakkbrikke/konge_hvit.png", 1),
    KONGE_SORT      ("bilde/ikon/sjakkbrikke/konge_sort.png", 1),
    DRONNING_HVIT   ("bilde/ikon/sjakkbrikke/dronning_hvit.png", 1),
    DRONNING_SORT   ("bilde/ikon/sjakkbrikke/dronning_sort.png", 1),
    TÅRN_HVIT       ("bilde/ikon/sjakkbrikke/tårn_hvit.png", 2),
    TÅRN_SORT       ("bilde/ikon/sjakkbrikke/tårn_sort.png", 2),
    LØPER_HVIT      ("bilde/ikon/sjakkbrikke/løper_hvit.png", 2),
    LØPER_SORT      ("bilde/ikon/sjakkbrikke/løper_sort.png", 2),
    SPRINGER_HVIT   ("bilde/ikon/sjakkbrikke/springer_hvit.png", 2),
    SPRINGER_SORT   ("bilde/ikon/sjakkbrikke/springer_sort.png", 2),
    BONDE_HVIT      ("bilde/ikon/sjakkbrikke/bonde_hvit.png", 8),
    BONDE_SORT      ("bilde/ikon/sjakkbrikke/bonde_sort.png", 8);

    private final String IKON_URL;
    private final int ANTALL;

    BrikkeType(String ikonURL, int antall){
        this.IKON_URL = ikonURL;
        this.ANTALL = antall;
    }

    public String getIkonURL(){
        return this.IKON_URL;
    }

    public int getAntall(){
        return this.ANTALL;
    }

}
