package Skeleton;

public class Tests {
    protected void test1(){
        System.out.println("busz keresztezodes");
    }

    protected void test2(){
        System.out.println("busz elore megy");
    }

    protected void test3(){
        System.out.println("busz savot valt");
    }

    protected void test4(){
        System.out.println("busz holancot hasznal");
    }

    protected void test5(){
        System.out.println("holanc javitasa");

        var s = TestSetup.createSnowchainFix();

        Skeleton.call("Tester", "Snowchain", "Fix()", true);
        s.snowchain.fix();

        Skeleton.returnCall("Snowchain", "Tester", "Fix()", "void");

        if(s.snowchain.getTimeToLive() == 999){
            Skeleton.result(true);
        }else{
            Skeleton.result(false);
        }
    }

    protected void test6(){
        System.out.println("auto keresztezodesnel");
    }

    protected void test7(){
        System.out.println("auto elore megy ");
    }

    protected void test8(){
        System.out.println("auto savot valt");
    }

    protected void test9(){
        System.out.println("hokotro keresztezodesnel");
    }

    protected void test10(){
        System.out.println("hokotro elore megy");
    }

    protected void test11(){
        System.out.println("hokotro savot valt");
    }

    protected void test12(){
        System.out.println("soprofej hasznalata");
    }

    protected void test13(){
        System.out.println("hanyofej hasznalata");
    }

    protected void test14(){
        System.out.println("jegtoro fej hasznalata");
    }

    protected void test15(){
        System.out.println("soszoro fej hasznalata");
    }

    protected void test16(){
        System.out.println("sarkanyfej hasznalata");
    }

    protected void test17(){
        System.out.println("utszakasz frissitese, fogyoeszkoz hatasa");
    }

    protected void test18(){
        System.out.println("hokotro vasarlas");
    }

    protected void test19(){
        System.out.println("busz vasarlas");
    }

    protected void test20(){
        System.out.println("soprofej vasarlas");
    }

    protected void test21(){
        System.out.println("fogyoeszkoz vasarlas");
    }

    protected void test22(){
        System.out.println("holanc vasarlas");
    }

    protected void test23(){
        System.out.println("baleset");

        Skeleton.call("Tester","Object1", "Method1", true);
        Skeleton.call("Object1","Object2", "Method2", false);
        Skeleton.call("Object2","Object1", "Method3", false);
        Skeleton.returnCall("Object1","Tester","Method1", "void");

        boolean asd = Skeleton.question("question?");

        if(asd){
            Skeleton.result(true);
        }else{
            Skeleton.error("error");
            Skeleton.result(false);
        }
    }
}
