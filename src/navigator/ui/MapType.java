package navigator.ui;

public enum MapType {
    Catlin("Catlin2-allroads-2020"),
    Portland("Portland1-allroads-2020"),
    United_States("US-primary-2020");

    public final String filename;
    MapType(String filename) {
        this.filename = filename;
    }
}
