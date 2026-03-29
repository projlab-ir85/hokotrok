$null = New-Item -ItemType Directory -Force out
$files = Get-ChildItem -Recurse -Filter "*.java" -Path src | Select-Object -ExpandProperty FullName
javac -sourcepath src -d out $files
if ($LASTEXITCODE -eq 0) {
    Write-Host "Forditas sikeres."
    java -cp out Main.Main
} else {
    Write-Host "Forditas sikertelen!"
}
