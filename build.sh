#!/bin/bash
mkdir -p out
javac -sourcepath src -d out $(find src -name "*.java")
if [ $? -eq 0 ]; then
    echo "Forditas sikeres."
    java -cp out Main.Main
else
    echo "Forditas sikertelen!"
fi
