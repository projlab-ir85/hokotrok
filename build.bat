@echo off
if not exist out mkdir out
dir /s /b src\*.java > sources.txt
javac -sourcepath src -d out @sources.txt
if %ERRORLEVEL% == 0 (
    echo Forditas sikeres.
    java -cp out Main.Main
) else (
    echo Forditas sikertelen!
)
del sources.txt
