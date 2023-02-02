@echo off
echo %cd
cd C:\ITMO\s1\Programming\Homework6_Mod\src
echo %cd%
javac IntList.java && javac MyScanner.java && javac WsppSecondG.java && java -jar WsppSecondGTest.jar
pause