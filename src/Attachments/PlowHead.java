package Attachments;

public abstract class PlowHead extends Attachment{
    protected int snowClean;
    protected int iceClean;
    protected int consumableAmountLeft;

    public abstract void Use();
}
