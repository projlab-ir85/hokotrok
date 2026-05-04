#!/bin/bash
# Windows (Git Bash) alatt a konzol kodlapjanak UTF-8-ra allitasa
# (Linux/macOS alatt a chcp nem elerheto, a hiba elnyeleste miatt nem gond).
chcp.com 65001 >/dev/null 2>&1 || true
java -cp out Main.Main
