package tech.gomes.reading.management.indicator;

public enum NoteTypeIndicator {
    QUICK, REFERENCE, PERMANENT;

    public static NoteTypeIndicator getTypeByName(String name) {

        for (NoteTypeIndicator indicator : NoteTypeIndicator.values()) {
            if (indicator.name().equalsIgnoreCase(name)) {
                return indicator;
            }
        }
        return null;
    }
}
