@echo off
echo %cd
cd C:\ITMO\s1\Programming\Homework5\ScannerAnalog\src
echo %cd%
javac WordStatInput.java && java -jar WordStatInputTest.jar
::pause