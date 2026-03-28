package Attachments;

import RoadComponents.RoadSection;

public class Snowchain extends Attachment {
    protected int timeToLive;

    public Snowchain(int timeToLive){
        this.timeToLive = timeToLive;
    }

    public void Use(RoadSection rs){
        timeToLive--;
    }

    public void Fix(){
        timeToLive = 999;
    }

    public int getTimeToLive(){return timeToLive;}
}
