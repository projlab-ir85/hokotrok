package RoadComponents;

/**
 * Egy interfész a szimuláció során rendszeresen frissítendő objektumok számára.
 * Bármely osztály, amely megvalósítja ezt az interfészt (például a RoadSection),
 * képes reagálni az idő múlására a szimuláció minden egyes lépésében (tick).
 */
public interface Updateable {
    /**
     * Meghívódik a szimuláció minden egyes lépésekor.
     * Ebben a metódusban kell elvégezni az objektum állapotának frissítését, 
     * például az időjárás változását, a lejárt effektek törlését vagy a járművek mozgatását.
     */
    public void update();
}
