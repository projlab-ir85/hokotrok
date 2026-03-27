package Skeleton;

import Attachments.Snowchain;
import Vehicles.Bus;

public class TestSetup {
    public static class SnowchainFix{
        public Bus bus;
        public Snowchain snowchain;
    }

    public static SnowchainFix createSnowchainFix(){
        SnowchainFix s = new SnowchainFix();

        Skeleton.call("Skeleton", "Bus", "new Bus()", false);
        s.bus = new Bus();

        Skeleton.call("Skeleton", "Snowchain", "new Snowchain()", false);
        s.snowchain = new Snowchain(999);

        Skeleton.call("Skeleton", "Bus", "addSnowchain(snowchain)", false);
        s.bus.addSnowchain(s.snowchain);

        System.out.println("\n");
        return s;
    }
}
