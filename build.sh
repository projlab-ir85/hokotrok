#!/bin/bash
mkdir -p out
javac -encoding UTF-8 -sourcepath src -d out $(find src -name "*.java")
if [ $? -eq 0 ]; then
    echo "Forditas sikeres."
    # Windows (Git Bash) alatt a konzol kodlapjanak UTF-8-ra allitasa
    # (Linux/macOS alatt a chcp nem elerheto, a hiba elnyeleste miatt nem gond).
    chcp.com 65001 >/dev/null 2>&1 || true
    java -cp out Main.Main
else
    echo "Forditas sikertelen!"
fi
