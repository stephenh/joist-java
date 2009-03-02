@echo off
if "%JAVA_HOME%"=="" echo Error: JAVA_HOME is not defined.
if not exist bin mkdir bin
if not exist bin\bd mkdir bin\bd
"%JAVA_HOME%/bin/javac" -cp "lib/bd/*" -sourcepath src/bd -d bin/bd src/bd/*.java
"%JAVA_HOME%/bin/java" -Xmx512m -cp "bin/bd;lib/bd/*;%JAVA_HOME%/lib/tools.jar" org.exigencecorp.bd.BuildRunner %*
