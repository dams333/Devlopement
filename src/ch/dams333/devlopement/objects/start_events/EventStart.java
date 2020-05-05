package ch.dams333.devlopement.objects.start_events;

public enum EventStart {

    NULL("Null", ""),
    JOIN("Join", "Quand un joueur rejoint"),
    QUIT("Quit", "Quand un joueur quitte"),
    DEATH("Death", "Quand un joueur meurt (s'active aussi quand un joueur meurt de PVP)"),
    Kill("Kill", "Quand un joueur se fait tuer (le joueur associé à la ligne est le tueur)");

    private String text = "";
    private String name = "";

    EventStart(String text, String name){
        this.text = text;
        this.name = name;
    }

    public String toString(){
        return text;
    }
    public String toName(){
        return name;
    }

    public static EventStart fromString(String text) {
        for (EventStart startingEvent : EventStart.values()) {
            if (startingEvent.text.equalsIgnoreCase(text)) {
                return startingEvent;
            }
        }
        return null;
    }

    public static EventStart fromName(String text) {
        for (EventStart startingEvent : EventStart.values()) {
            if (startingEvent.name.equalsIgnoreCase(text)) {
                return startingEvent;
            }
        }
        return null;
    }


}
