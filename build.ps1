$null = New-Item -ItemType Directory -Force out
$files = Get-ChildItem -Recurse -Filter "*.java" -Path src | Select-Object -ExpandProperty FullName
javac -encoding UTF-8 -sourcepath src -d out $files
if ($LASTEXITCODE -eq 0) {
    Write-Host "Forditas sikeres."
    # A PowerShell konzol UTF-8-ra állítása (a magyar karakterek helyes megjelenítéséhez)
    [Console]::OutputEncoding = [System.Text.Encoding]::UTF8
    [Console]::InputEncoding  = [System.Text.Encoding]::UTF8
    java -cp out Main.Main
} else {
    Write-Host "Forditas sikertelen!"
}
