1. **Fordítás**

A fordításhoz JDK 17 vagy újabb szükséges. A projekt gyökérkönyvtárából (hokotrok\\):

**Windows (cmd.exe):**

mkdir out  
dir /s /b src\\\*.java \> sources.txt  
javac \-sourcepath src \-d out @sources.txt

**Windows (PowerShell):**

javac \-sourcepath src \-d out (Get-ChildItem \-Recurse \-Filter "\*.java" src | Select-Object \-ExpandProperty FullName)

**Linux / macOS / Git Bash (ERŐSEN AJÁNLOTT\!):**

mkdir \-p out  
javac \-sourcepath src \-d out $(find src \-name "\*.java")

2. **Futtatás**

A fordítás után a program az alábbi paranccsal indítható:

java \-cp out Main.Main

A program indításkor kiírja a tesztmenüt. A tesztelő beírja a kívánt teszt sorszámát (1–23), majd a program interaktívan kérdez rá a szimulált döntésekre. A válaszadás y (igen) vagy n (nem) karakterrel történik. Minden teszthez előre meghatározott a helyes forgatókönyv:

| Tesztek | Kérdések száma | Helyes válasz |
| :---- | :---- | :---- |
| 1, 2, 6, 7, 9, 10, 17–23 | 1 | y |
| 3, 8, 11 | 2 | n majd y |
| 4, 5, 12–16 | 0 | *(nincs kérdés)* |
