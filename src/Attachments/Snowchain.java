package Attachments;

public class Snowchain extends Attachment {
    protected int timeToLive;

    public Snowchain(int timeToLive){
        this.timeToLive = timeToLive;
    }

    public void Use(){}

    public void Fix(){}

    public int getTimeToLive(){return timeToLive;}
}
