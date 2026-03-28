package Attachments;

import RoadComponents.RoadSection;

public class Snowchain extends Attachment {
    protected int timeToLive;

    public Snowchain(int timeToLive){
        this.timeToLive = timeToLive;
    }

    public void use(RoadSection rs){
        timeToLive--;
    }

    public void fix(){
        timeToLive = 30;
    }

    public int getTimeToLive(){return timeToLive;}
}
