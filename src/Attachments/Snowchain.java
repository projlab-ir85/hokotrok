package Attachments;

import RoadComponents.RoadSection;

public class Snowchain extends Attachment {
    protected int timeToLive;
    private int initialTimeToLive;

    public Snowchain(int timeToLive){
        this.timeToLive = timeToLive;
        this.initialTimeToLive = timeToLive;
    }

    public void use(){
        timeToLive--;
    }

    public void fix(){
        timeToLive = initialTimeToLive;
    }

    public int getTimeToLive(){return timeToLive;}
}
