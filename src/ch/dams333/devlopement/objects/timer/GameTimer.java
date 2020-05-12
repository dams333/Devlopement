package ch.dams333.devlopement.objects.timer;

import java.util.UUID;

public class GameTimer {

    private UUID uuid;
    private UUID owner;
    private String name;
    private int time;

    public GameTimer(UUID uuid, UUID owner, String name) {
        this.uuid = uuid;
        this.owner = owner;
        this.name = name;
        time = 0;
    }

    public GameTimer(UUID owner, String name) {
        this.owner = owner;
        this.name = name;
        this.uuid = UUID.randomUUID();
        time = 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public void addSecond(){
        this.time++;
    }

    public void reset(){
        this.time = 0;
    }

    public String toText(String format){

        String hourF = "h";
        String minuteF = "h";
        String secondF = "s";

        if(format.contains(hourF)){
            int sec = time % 60;
            int hour = time / 60;
            int min = hour % 60;
            hour = hour/60;
            return format.replaceFirst(hourF, String.valueOf(hour)).replaceFirst(minuteF, String.valueOf(min)).replaceFirst(secondF, String.valueOf(sec));
        }else if(format.contains(minuteF)){
            int min = (time % 3600) / 60;
            int sec = time % 60;
            return format.replaceFirst(minuteF, String.valueOf(min)).replaceFirst(secondF, String.valueOf(sec));
        }else if(format.contains(secondF)){
            int sec = time;
            return format.replaceFirst(secondF, String.valueOf(sec));
        }else{
            return format;
        }
    }

    private String add0IfNeed(int time){
        if(time < 10){
            return "0" + time;
        }else{
            return String.valueOf(time);
        }
    }
}
