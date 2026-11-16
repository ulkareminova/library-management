package model;

public enum BookCategory {
    FICTION("Fiksiya"),
    SCIENCE("Elm"),
    HISTORY("Tarix"),
    TECHNOLOGY("Texnologiya");


    private final String displayName;
    BookCategory(String displayName){
        this.displayName = displayName;
    }
    public String getDisplayName(){
        return this.displayName;
    }

}
