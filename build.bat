@echo off
if not exist out mkdir out
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -sourcepath src -d out @sources.txt
if %ERRORLEVEL% == 0 (
    echo Forditas sikeres.
    rem A Windows konzol UTF-8-ra allitasa (a magyar karakterek helyes megjelenitesehez)
    chcp 65001 >nul
    java -cp out Main.Main
) else (
    echo Forditas sikertelen!
)
del sources.txt
