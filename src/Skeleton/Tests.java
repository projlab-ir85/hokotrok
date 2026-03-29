package Skeleton;

import Attachments.PlowHeads.BroomHead;
import RoadComponents.RoadSection;

public class Tests {

    protected void test1() {
        System.out.println("busz keresztezodes");

        var s = TestSetup.createBusMovementIntersection();

        Skeleton.call("Tesztelő", "Busz", "lep()", true);

        boolean atIntersection = Skeleton.question("Keresztezodesnel van-e a Busz?");
        if (atIntersection) {
            Skeleton.call("Busz", "Keresztezes", "utvalaszto(i2)", true);
            RoadSection rs = s.i1.roadSelection(s.i2);
            Skeleton.returnCall("Keresztezes", "Busz", "utvalaszto(i2)", "utszakasz");
            if (rs != null) s.bus.setCurrRoadSection(rs);
        }

        Skeleton.returnCall("Busz", "Tesztelő", "lep()", "void");
        Skeleton.result(s.bus.getCurrRoadSection() != null);
    }

    protected void test2() {
        System.out.println("busz elore megy");

        var s = TestSetup.createBusMovementRoad();
        if (s.nextSection == null) {
            Skeleton.error("nextSection null – road tul rovid");
            Skeleton.result(false);
            return;
        }

        Skeleton.call("Tesztelő", "Busz", "lep()", true);
        Skeleton.call("Busz", "Utszakasz", "accept(busz)", true);

        boolean passable = Skeleton.question("Jarható-e az utszakasz? (nincs baleset)");
        if (passable) {
            Skeleton.call("Utszakasz", "Busz", "interaktal(utszakasz)", true);
            Skeleton.call("Busz", "Utszakasz", "jegNovel(1)", false);
            s.nextSection.iceIncrease(1);
            Skeleton.returnCall("Busz", "Utszakasz", "jegNovel(1)", "void");
            Skeleton.returnCall("Utszakasz", "Busz", "interaktal(utszakasz)", "void");
        }

        Skeleton.returnCall("Utszakasz", "Busz", "accept(busz)", String.valueOf(passable));
        Skeleton.returnCall("Busz", "Tesztelő", "lep()", "void");
        Skeleton.result(s.nextSection.getIce() > 0);
    }

    protected void test3() {
        System.out.println("busz savot valt");

        var s = TestSetup.createBusMovementRoad();
        if (s.busSection == null || s.busSection.right == null) {
            Skeleton.error("busSection vagy right null");
            Skeleton.result(false);
            return;
        }

        Skeleton.call("Tesztelő", "Busz", "lep()", true);
        Skeleton.call("Busz", "Utszakasz", "accept(busz)", true);

        boolean passable = Skeleton.question("Jarható-e az utszakasz?");
        if (!passable) {
            Skeleton.call("Utszakasz", "SzomszedosUtszakasz", "accept(busz)", true);
            boolean rightPassable = Skeleton.question("Jarható a szomszedos sav?");
            if (rightPassable) {
                Skeleton.call("SzomszedosUtszakasz", "Busz", "interaktal(utszakasz)", true);
                Skeleton.call("Busz", "SzomszedosUtszakasz", "jegNovel(1)", false);
                s.busSection.right.iceIncrease(1);
                Skeleton.returnCall("Busz", "SzomszedosUtszakasz", "jegNovel(1)", "void");
                Skeleton.returnCall("SzomszedosUtszakasz", "Busz", "interaktal(utszakasz)", "void");
                Skeleton.returnCall("SzomszedosUtszakasz", "Utszakasz", "accept(busz)", "true");
                Skeleton.result(s.busSection.right.getIce() > 0);
            } else {
                System.out.println("Busz vár (mindkét sáv járhatatlan).");
                Skeleton.returnCall("SzomszedosUtszakasz", "Utszakasz", "accept(busz)", "false");
                Skeleton.result(true);
            }
        } else {
            Skeleton.returnCall("Utszakasz", "Busz", "accept(busz)", "true");
            Skeleton.result(true);
        }

        Skeleton.returnCall("Busz", "Tesztelő", "lep()", "void");
    }

    protected void test4() {
        System.out.println("busz holancot hasznal");

        var s = TestSetup.createBusWithSnowchainOnRoad();
        int ttlBefore = s.snowchain.getTimeToLive();

        Skeleton.call("Tesztelő", "Busz", "lep()", true);
        Skeleton.call("Busz", "Utszakasz", "accept(busz)", false);
        Skeleton.call("Busz", "Holanc", "use()", false);
        s.snowchain.use();
        Skeleton.returnCall("Holanc", "Busz", "use()", "void");
        Skeleton.returnCall("Busz", "Tesztelő", "lep()", "void");

        Skeleton.result(s.snowchain.getTimeToLive() == ttlBefore - 1);
    }

    protected void test5() {
        System.out.println("holanc javitasa");

        var s = TestSetup.createSnowchainFix();

        Skeleton.call("Tester", "Snowchain", "Fix()", true);
        s.snowchain.fix();

        Skeleton.returnCall("Snowchain", "Tester", "Fix()", "void");

        if (s.snowchain.getTimeToLive() == 999) {
            Skeleton.result(true);
        } else {
            Skeleton.result(false);
        }
    }

    protected void test6() {
        System.out.println("auto keresztezodesnel");

        var s = TestSetup.createCarMovementIntersection();

        Skeleton.call("Tesztelő", "Auto", "lep()", true);

        boolean atIntersection = Skeleton.question("Keresztezodesnel van-e az Auto?");
        if (atIntersection) {
            Skeleton.call("Auto", "Keresztezes", "utvalaszto(i2)", true);
            RoadSection rs = s.i1.roadSelection(s.i2);
            Skeleton.returnCall("Keresztezes", "Auto", "utvalaszto(i2)", "utszakasz");
            if (rs != null) s.car.setCurrRoadSection(rs);
        }

        Skeleton.returnCall("Auto", "Tesztelő", "lep()", "void");
        Skeleton.result(s.car.getCurrRoadSection() != null);
    }

    protected void test7() {
        System.out.println("auto elore megy");

        var s = TestSetup.createCarMovementRoad();
        if (s.nextSection == null) {
            Skeleton.error("nextSection null – road tul rovid");
            Skeleton.result(false);
            return;
        }

        Skeleton.call("Tesztelő", "Auto", "lep()", true);
        Skeleton.call("Auto", "Utszakasz", "accept(auto)", true);

        boolean passable = Skeleton.question("Jarható-e az utszakasz? (nincs baleset)");
        if (passable) {
            Skeleton.call("Utszakasz", "Auto", "interaktal(utszakasz)", true);
            Skeleton.call("Auto", "Utszakasz", "jegNovel(1)", false);
            s.nextSection.iceIncrease(1);
            Skeleton.returnCall("Auto", "Utszakasz", "jegNovel(1)", "void");
            Skeleton.returnCall("Utszakasz", "Auto", "interaktal(utszakasz)", "void");
        }

        Skeleton.returnCall("Utszakasz", "Auto", "accept(auto)", String.valueOf(passable));
        Skeleton.returnCall("Auto", "Tesztelő", "lep()", "void");
        Skeleton.result(s.nextSection.getIce() > 0);
    }

    protected void test8() {
        System.out.println("auto savot valt");

        var s = TestSetup.createCarMovementRoad();
        if (s.carSection == null || s.carSection.right == null) {
            Skeleton.error("carSection vagy right null");
            Skeleton.result(false);
            return;
        }

        Skeleton.call("Tesztelő", "Auto", "lep()", true);
        Skeleton.call("Auto", "Utszakasz", "accept(auto)", true);

        boolean passable = Skeleton.question("Jarható-e az utszakasz?");
        if (!passable) {
            Skeleton.call("Utszakasz", "SzomszedosUtszakasz", "accept(auto)", true);
            boolean rightPassable = Skeleton.question("Jarható a szomszedos sav?");
            if (rightPassable) {
                Skeleton.call("SzomszedosUtszakasz", "Auto", "interaktal(utszakasz)", true);
                Skeleton.call("Auto", "SzomszedosUtszakasz", "jegNovel(1)", false);
                s.carSection.right.iceIncrease(1);
                Skeleton.returnCall("Auto", "SzomszedosUtszakasz", "jegNovel(1)", "void");
                Skeleton.returnCall("SzomszedosUtszakasz", "Auto", "interaktal(utszakasz)", "void");
                Skeleton.returnCall("SzomszedosUtszakasz", "Utszakasz", "accept(auto)", "true");
                Skeleton.result(s.carSection.right.getIce() > 0);
            } else {
                System.out.println("Auto vár (mindkét sáv járhatatlan).");
                Skeleton.returnCall("SzomszedosUtszakasz", "Utszakasz", "accept(auto)", "false");
                Skeleton.result(true);
            }
        } else {
            Skeleton.returnCall("Utszakasz", "Auto", "accept(auto)", "true");
            Skeleton.result(true);
        }

        Skeleton.returnCall("Auto", "Tesztelő", "lep()", "void");
    }

    protected void test9() {
        System.out.println("hokotro keresztezodesnel");

        var s = TestSetup.createSnowplowMovementIntersection();

        Skeleton.call("Tesztelő", "Hokotro", "lep()", true);

        boolean atIntersection = Skeleton.question("Keresztezodesnel van-e a Hokotro?");
        if (atIntersection) {
            Skeleton.call("Hokotro", "Keresztezes", "utvalaszto(i2)", true);
            RoadSection rs = s.i1.roadSelection(s.i2);
            Skeleton.returnCall("Keresztezes", "Hokotro", "utvalaszto(i2)", "utszakasz");
            if (rs != null) s.snowplow.setCurrRoadSection(rs);
        }

        Skeleton.returnCall("Hokotro", "Tesztelő", "lep()", "void");
        Skeleton.result(s.snowplowSection != null);
    }

    protected void test10() {
        System.out.println("hokotro elore megy");

        var s = TestSetup.createSnowplowMovementRoad();
        if (s.nextSection == null) {
            Skeleton.error("nextSection null");
            Skeleton.result(false);
            return;
        }
        int snowBefore = s.nextSection.getSnow();

        Skeleton.call("Tesztelő", "Hokotro", "lep()", true);
        Skeleton.call("Hokotro", "Utszakasz", "accept(hokotro)", true);

        boolean passable = Skeleton.question("Jarható-e az utszakasz? (nincs baleset)");
        if (passable) {
            Skeleton.call("Utszakasz", "Hokotro", "interaktal(utszakasz)", true);
            Skeleton.call("Hokotro", "Kotrofej", "hasznal(utszakasz)", true);
            s.snowplow.getActivePlowHead().use(s.nextSection);
            Skeleton.returnCall("Kotrofej", "Hokotro", "hasznal(utszakasz)", "void");
            Skeleton.returnCall("Hokotro", "Utszakasz", "interaktal(utszakasz)", "void");
        }

        Skeleton.returnCall("Utszakasz", "Hokotro", "accept(hokotro)", String.valueOf(passable));
        Skeleton.returnCall("Hokotro", "Tesztelő", "lep()", "void");
        Skeleton.result(s.nextSection.getSnow() < snowBefore);
    }

    protected void test11() {
        System.out.println("hokotro savot valt");

        var s = TestSetup.createSnowplowMovementRoad();
        if (s.snowplowSection == null || s.snowplowSection.right == null) {
            Skeleton.error("snowplowSection vagy right null");
            Skeleton.result(false);
            return;
        }

        Skeleton.call("Tesztelő", "Hokotro", "lep()", true);
        Skeleton.call("Hokotro", "Utszakasz", "accept(hokotro)", true);

        boolean passable = Skeleton.question("Jarható-e az utszakasz?");
        if (!passable) {
            Skeleton.call("Utszakasz", "SzomszedosUtszakasz", "accept(hokotro)", true);
            boolean rightPassable = Skeleton.question("Jarható a szomszedos sav?");
            if (rightPassable) {
                Skeleton.call("SzomszedosUtszakasz", "Hokotro", "interaktal(utszakasz)", true);
                Skeleton.call("Hokotro", "Kotrofej", "hasznal(utszakasz)", true);
                s.snowplow.getActivePlowHead().use(s.snowplowSection.right);
                Skeleton.returnCall("Kotrofej", "Hokotro", "hasznal(utszakasz)", "void");
                Skeleton.returnCall("Hokotro", "SzomszedosUtszakasz", "interaktal(utszakasz)", "void");
                Skeleton.returnCall("SzomszedosUtszakasz", "Utszakasz", "accept(hokotro)", "true");
                Skeleton.result(true);
            } else {
                System.out.println("Hókotró vár (mindkét sáv járhatatlan).");
                Skeleton.returnCall("SzomszedosUtszakasz", "Utszakasz", "accept(hokotro)", "false");
                Skeleton.result(true);
            }
        } else {
            Skeleton.returnCall("Utszakasz", "Hokotro", "accept(hokotro)", "true");
            Skeleton.result(true);
        }

        Skeleton.returnCall("Hokotro", "Tesztelő", "lep()", "void");
    }

    protected void test12() {
        System.out.println("soprofej hasznalata");

        var p = TestSetup.createBroomHeadUsage();

        Skeleton.call("Tesztelő", "Hokotro", "lep()", true);
        Skeleton.call("Hokotro", "Soprofej", "hasznal(utszakasz)", true);
        Skeleton.call("Soprofej", "Utszakasz", "hocsokken(hoTakarit)", false);
        Skeleton.call("Soprofej", "SzomszedosUtszakasz", "honovel(hoTakarit)", false);

        p.head.use(p.section);

        Skeleton.returnCall("SzomszedosUtszakasz", "Soprofej", "honovel(hoTakarit)", "void");
        Skeleton.returnCall("Utszakasz", "Soprofej", "hocsokken(hoTakarit)", "void");
        Skeleton.returnCall("Soprofej", "Hokotro", "hasznal(utszakasz)", "void");
        Skeleton.returnCall("Hokotro", "Tesztelő", "lep()", "void");

        Skeleton.result(p.section.getSnow() == 0 && p.rightSection.getSnow() > 0);
    }

    protected void test13() {
        System.out.println("hanyofej hasznalata");

        var p = TestSetup.createThrowHeadUsage();

        Skeleton.call("Tesztelő", "Hokotro", "lep()", true);
        Skeleton.call("Hokotro", "Hanyofej", "hasznal(utszakasz)", true);
        Skeleton.call("Hanyofej", "Utszakasz", "hocsokken(hoTakarit)", false);

        p.head.use(p.section);

        Skeleton.returnCall("Utszakasz", "Hanyofej", "hocsokken(hoTakarit)", "void");
        Skeleton.returnCall("Hanyofej", "Hokotro", "hasznal(utszakasz)", "void");
        Skeleton.returnCall("Hokotro", "Tesztelő", "lep()", "void");

        Skeleton.result(p.section.getSnow() == 0);
    }

    protected void test14() {
        System.out.println("jegtoro fej hasznalata");

        var p = TestSetup.createIceBreakerHeadUsage();
        int iceBefore = p.section.getIce();

        Skeleton.call("Tesztelő", "Hokotro", "lep()", true);
        Skeleton.call("Hokotro", "Jegtorof", "hasznal(utszakasz)", true);
        Skeleton.call("Jegtorof", "Utszakasz", "jegcsokken(jegTakarit)", false);
        Skeleton.call("Jegtorof", "Utszakasz", "honovel(hoTakarit)", false);

        p.head.use(p.section);

        Skeleton.returnCall("Utszakasz", "Jegtorof", "honovel(hoTakarit)", "void");
        Skeleton.returnCall("Utszakasz", "Jegtorof", "jegcsokken(jegTakarit)", "void");
        Skeleton.returnCall("Jegtorof", "Hokotro", "hasznal(utszakasz)", "void");
        Skeleton.returnCall("Hokotro", "Tesztelő", "lep()", "void");

        Skeleton.result(p.section.getIce() == 0 && p.section.getSnow() == iceBefore);
    }

    protected void test15() {
        System.out.println("soszoro fej hasznalata");

        var p = TestSetup.createSaltShakerUsage();

        Skeleton.call("Tesztelő", "Hokotro", "lep()", true);
        Skeleton.call("Hokotro", "Soszorof", "hasznal(utszakasz)", true);
        Skeleton.call("Soszorof", "Utszakasz", "fogyoeszkozHozzaad(so)", false);

        p.head.use(p.section);

        Skeleton.returnCall("Utszakasz", "Soszorof", "fogyoeszkozHozzaad(so)", "void");
        Skeleton.returnCall("Soszorof", "Hokotro", "hasznal(utszakasz)", "void");
        Skeleton.returnCall("Hokotro", "Tesztelő", "lep()", "void");

        Skeleton.result(p.section.getConsumableCount() > 0);
    }

    protected void test16() {
        System.out.println("sarkanyfej hasznalata");

        var p = TestSetup.createDragonHeadUsage();

        Skeleton.call("Tesztelő", "Hokotro", "lep()", true);
        Skeleton.call("Hokotro", "Sarkanyfej", "hasznal(utszakasz)", true);
        Skeleton.call("Sarkanyfej", "Utszakasz", "fogyoeszkozHozzaad(kerozin)", false);

        p.head.use(p.section);

        Skeleton.returnCall("Utszakasz", "Sarkanyfej", "fogyoeszkozHozzaad(kerozin)", "void");
        Skeleton.returnCall("Sarkanyfej", "Hokotro", "hasznal(utszakasz)", "void");
        Skeleton.returnCall("Hokotro", "Tesztelő", "lep()", "void");

        Skeleton.result(p.section.getConsumableCount() > 0);
    }

    protected void test17() {
        System.out.println("utszakasz frissitese, fogyoeszkoz hatasa");

        var r = TestSetup.createRoadSectionUpdate();
        int snowBefore = r.section.getSnow();

        Skeleton.call("Tesztelő", "Utszakasz", "frissit()", true);

        boolean consumableAlive = Skeleton.question("Elfogyott-e a fogyoeszkoz? (Elettartam != 0)");

        Skeleton.call("Utszakasz", "Fogyoeszkoz", "hatas(utszakasz)", true);
        r.section.update();
        Skeleton.returnCall("Fogyoeszkoz", "Utszakasz", "hatas(utszakasz)", String.valueOf(consumableAlive));

        Skeleton.returnCall("Utszakasz", "Tesztelő", "frissit()", "void");

        // update() csökkenti a havat (consumable), majd 1-gyel növeli
        Skeleton.result(r.section.getSnow() < snowBefore);
    }

    protected void test18() {
        System.out.println("hokotro vasarlas");

        var ps = TestSetup.createPurchaseSetup();
        int cashBefore = ps.player.getCash();

        Skeleton.call("Tesztelő", "Shop", "hokotroVasarol(player)", true);

        boolean hasEnough = Skeleton.question("Van eleg penz a hokotro megvasarlasahoz?");
        if (hasEnough) {
            Skeleton.call("Shop", "Player", "vonLe(ar)", false);
            Skeleton.call("Shop", "Hokotro", "new Hokotro()", false);
            Skeleton.call("Shop", "Player", "hozzaadJarmu(hokotro)", false);
            ps.shop.buySnowplow(ps.player);
            Skeleton.returnCall("Player", "Shop", "hozzaadJarmu(hokotro)", "void");
            Skeleton.result(ps.player.getCash() == cashBefore - 2000 && ps.player.getVehicleCount() == 1);
        } else {
            System.out.println("Nincs eleg egyenleg, vasarlas megszakad.");
            Skeleton.result(true);
        }

        Skeleton.returnCall("Shop", "Tesztelő", "hokotroVasarol(player)", "void");
    }

    protected void test19() {
        System.out.println("busz vasarlas");

        var ps = TestSetup.createPurchaseSetup();
        int cashBefore = ps.player.getCash();

        Skeleton.call("Tesztelő", "Shop", "buszVasarol(player)", true);

        boolean hasEnough = Skeleton.question("Van eleg penz a busz megvasarlasahoz?");
        if (hasEnough) {
            Skeleton.call("Shop", "Player", "vonLe(ar)", false);
            Skeleton.call("Shop", "Busz", "new Busz()", false);
            Skeleton.call("Shop", "Player", "hozzaadJarmu(busz)", false);
            ps.shop.buyBus(ps.player);
            Skeleton.returnCall("Player", "Shop", "hozzaadJarmu(busz)", "void");
            Skeleton.result(ps.player.getCash() == cashBefore - 2000 && ps.player.getVehicleCount() == 1);
        } else {
            System.out.println("Nincs eleg egyenleg, vasarlas megszakad.");
            Skeleton.result(true);
        }

        Skeleton.returnCall("Shop", "Tesztelő", "buszVasarol(player)", "void");
    }

    protected void test20() {
        System.out.println("soprofej vasarlas");

        var ps = TestSetup.createPurchaseSetup();
        int cashBefore = ps.player.getCash();
        int headsBefore = ps.snowplow.getPlowHeadCount();

        Skeleton.call("Tesztelő", "Shop", "kotrofejVasarol(player, hokotro, Soprofej)", true);

        boolean hasEnough = Skeleton.question("Van eleg penz a soprofej megvasarlasahoz?");
        if (hasEnough) {
            Skeleton.call("Shop", "Player", "vonLe(ar)", false);
            Skeleton.call("Shop", "Soprofej", "new Soprofej()", false);
            Skeleton.call("Shop", "Hokotro", "hozzaadKotrofej(soprofej)", false);
            ps.shop.buyAttachment(ps.player, ps.snowplow, new BroomHead());
            Skeleton.returnCall("Hokotro", "Shop", "hozzaadKotrofej(soprofej)", "void");
            Skeleton.result(ps.player.getCash() == cashBefore - 500
                    && ps.snowplow.getPlowHeadCount() == headsBefore + 1);
        } else {
            System.out.println("Nincs eleg egyenleg, vasarlas megszakad.");
            Skeleton.result(true);
        }

        Skeleton.returnCall("Shop", "Tesztelő", "kotrofejVasarol(player, hokotro, Soprofej)", "void");
    }

    protected void test21() {
        System.out.println("fogyoeszkoz vasarlas");

        var ps = TestSetup.createPurchaseSetup();
        int cashBefore = ps.player.getCash();

        Skeleton.call("Tesztelő", "Shop", "fogyoeszkozVasarol(player, hokotro, so)", true);

        boolean hasEnough = Skeleton.question("Van eleg penz a fogyoeszkoz megvasarlasahoz?");
        if (hasEnough) {
            Skeleton.call("Shop", "Player", "vonLe(ar)", false);
            Skeleton.call("Shop", "Hokotro", "aktivFejFeltolt(mennyiseg)", false);
            ps.shop.buyConsumeable(ps.player, ps.snowplow, "salt");
            Skeleton.returnCall("Hokotro", "Shop", "aktivFejFeltolt(mennyiseg)", "void");
            Skeleton.result(ps.player.getCash() == cashBefore - 100);
        } else {
            System.out.println("Nincs eleg egyenleg, vasarlas megszakad.");
            Skeleton.result(true);
        }

        Skeleton.returnCall("Shop", "Tesztelő", "fogyoeszkozVasarol(player, hokotro, so)", "void");
    }

    protected void test22() {
        System.out.println("holanc vasarlas");

        var ps = TestSetup.createPurchaseSetup();
        int cashBefore = ps.player.getCash();

        Skeleton.call("Tesztelő", "Shop", "holancVasarol(player, busz)", true);

        boolean hasEnough = Skeleton.question("Van eleg penz a holanc megvasarlasahoz?");
        if (hasEnough) {
            Skeleton.call("Shop", "Player", "vonLe(ar)", false);
            Skeleton.call("Shop", "Holanc", "new Holanc()", false);
            Skeleton.call("Shop", "Busz", "hozzaadHolanc(holanc)", false);
            ps.shop.buySnowchain(ps.player, ps.bus);
            Skeleton.returnCall("Busz", "Shop", "hozzaadHolanc(holanc)", "void");
            Skeleton.result(ps.bus.getHasSnowchain() && ps.player.getCash() == cashBefore - 300);
        } else {
            System.out.println("Nincs eleg egyenleg, vasarlas megszakad.");
            Skeleton.result(true);
        }

        Skeleton.returnCall("Shop", "Tesztelő", "holancVasarol(player, busz)", "void");
    }

    protected void test23() {
        System.out.println("baleset");

        var a = TestSetup.createAccidentSetup();

        Skeleton.call("Tesztelő", "Busz", "lep()", true);
        Skeleton.call("Busz", "Utszakasz", "accept(busz)", true);

        boolean accident = Skeleton.question("Jeges-e az utszakasz es van-e mar jarmu? (baleset)");
        if (accident) {
            Skeleton.call("Utszakasz", "Busz1", "elakadt = true", false);
            Skeleton.call("Utszakasz", "Busz2", "elakadt = true", false);
            a.bus1.setStuck(true);
            a.bus2.setStuck(true);
            Skeleton.returnCall("Utszakasz", "Busz", "accept(busz)", "false");
        } else {
            Skeleton.returnCall("Utszakasz", "Busz", "accept(busz)", "true");
        }

        Skeleton.returnCall("Busz", "Tesztelő", "lep()", "void");
        Skeleton.result(accident ? (a.bus1.isStuck() && a.bus2.isStuck()) : true);
    }
}
