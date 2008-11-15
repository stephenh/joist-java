@echo off
if "%JAVA_HOME%"=="" echo Error: JAVA_HOME is not defined.
if not exist bin mkdir bin
"%JAVA_HOME%/bin/javac" -sourcepath src/build -d bin src/build/*.java
"%JAVA_HOME%/bin/java" -Xmx512m -cp "bin;%JAVA_HOME%/lib/tools.jar" Build %*
