# Tesztfuttato szkript
# Hasznalat: powershell -ExecutionPolicy Bypass -File run_tests.ps1 [-TestFilter <pattern>] [-Update]
#   -Update : felulirja az expected fajlokat a tenyleges kimenettel (csak fejlesztoi hasznalatra)

param(
    [string]$TestFilter = "*",
    [switch]$Update
)

$ErrorActionPreference = "Stop"

# UTF-8 kodolas eroltetese BOM nelkul (a Java oldalon a Main is UTF-8-ra valt).
# A BOM-ot tartalmazo UTF-8 elrontana a stdin-en az elso parancsot ('\ufeff' eloleg).
$utf8NoBom = New-Object System.Text.UTF8Encoding $false
[Console]::OutputEncoding = $utf8NoBom
[Console]::InputEncoding  = $utf8NoBom
$OutputEncoding           = $utf8NoBom

# 1) Forditas, ha nincs lekepezett kimenet
if (-not (Test-Path "out\Main\Main.class")) {
    Write-Host "Forditas..."
    $null = New-Item -ItemType Directory -Force out
    $files = Get-ChildItem -Recurse -Filter "*.java" -Path src | Select-Object -ExpandProperty FullName
    & javac -sourcepath src -d out $files
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Forditas sikertelen!" -ForegroundColor Red
        exit 1
    }
}

$inputDir    = Join-Path $PSScriptRoot "tests\inputs"
$expectedDir = Join-Path $PSScriptRoot "tests\expected"
$actualDir   = Join-Path $PSScriptRoot "tests\actual"
$null = New-Item -ItemType Directory -Force $actualDir

# ANSI eltavolitasa
function Strip-Ansi([string]$text) {
    if ($null -eq $text) { return "" }
    return ($text -replace "`e\[[0-9;]*[A-Za-z]", "") -replace "\x1B\[[0-9;]*[A-Za-z]", ""
}

# Sorvegek normalizalasa
function Normalize([string]$text) {
    $t = Strip-Ansi $text
    $t = $t -replace "`r", ""
    # Sorvegi szokozok torlese
    $lines = $t -split "`n" | ForEach-Object { $_.TrimEnd() }
    return ($lines -join "`n").TrimEnd()
}

$tests = Get-ChildItem -Path $inputDir -Filter "$TestFilter.in" | Sort-Object Name
if ($tests.Count -eq 0) {
    Write-Host "Nincsenek tesztek a megadott szurovel." -ForegroundColor Yellow
    exit 0
}

$pass = 0
$fail = 0
$failed = @()

foreach ($test in $tests) {
    $name = [System.IO.Path]::GetFileNameWithoutExtension($test.Name)
    $expectedFile = Join-Path $expectedDir "$name.out"
    $actualFile   = Join-Path $actualDir   "$name.out"

    # Futtatas: stdin = inputfile, stdout = actual
    $input = Get-Content $test.FullName -Raw
    $actualRaw = $input | & java -cp out Main.Main 2>&1 | Out-String
    $actualNorm = Normalize $actualRaw

    [System.IO.File]::WriteAllText($actualFile, $actualNorm, $utf8NoBom)

    if ($Update) {
        $null = New-Item -ItemType Directory -Force $expectedDir
        [System.IO.File]::WriteAllText($expectedFile, $actualNorm, $utf8NoBom)
        Write-Host ("[UPDATE] {0}" -f $name) -ForegroundColor Cyan
        continue
    }

    if (-not (Test-Path $expectedFile)) {
        Write-Host ("[MISSING EXPECTED] {0}" -f $name) -ForegroundColor Yellow
        $fail++
        $failed += $name
        continue
    }

    $expectedNorm = Normalize ([System.IO.File]::ReadAllText($expectedFile, $utf8NoBom))

    if ($actualNorm -eq $expectedNorm) {
        Write-Host ("[PASS] {0}" -f $name) -ForegroundColor Green
        $pass++
    } else {
        Write-Host ("[FAIL] {0}" -f $name) -ForegroundColor Red
        $fail++
        $failed += $name
        # elso elteres megmutatasa
        $actualLines   = $actualNorm   -split "`n"
        $expectedLines = $expectedNorm -split "`n"
        $max = [Math]::Max($actualLines.Count, $expectedLines.Count)
        for ($k = 0; $k -lt $max; $k++) {
            $a = if ($k -lt $actualLines.Count)   { $actualLines[$k]   } else { "<EOF>" }
            $e = if ($k -lt $expectedLines.Count) { $expectedLines[$k] } else { "<EOF>" }
            if ($a -ne $e) {
                Write-Host ("  sor {0}:" -f ($k+1)) -ForegroundColor Yellow
                Write-Host ("    elvart : {0}" -f $e) -ForegroundColor DarkYellow
                Write-Host ("    kapott : {0}" -f $a) -ForegroundColor DarkYellow
                break
            }
        }
    }
}

Write-Host ""
Write-Host ("Eredmeny: {0} sikeres / {1} sikertelen / {2} osszes" -f $pass, $fail, ($pass + $fail))
if ($fail -gt 0) { exit 1 } else { exit 0 }
