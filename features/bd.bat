@echo off
if "%JAVA_HOME%"=="" echo Error: JAVA_HOME is not defined.
if not exist bin mkdir bin
if not exist bin\bd mkdir bin\bd
rem set FOO=blah
rem for %%b in ("lib\*.jar") do set FOO=%FOO% and %%b
rem echo %FOO%
"%JAVA_HOME%\bin\javac" -cp "lib\bd.jar;lib\exigence-domainobjects.jar;lib\exigence-utilities.jar" -sourcepath src\build -d bin\bd src\build\*.java
"%JAVA_HOME%\bin\java" -Xmx512m -cp "bin\bd;lib\bd.jar;lib\exigence-utilites.jar;lib\exigence-domainobjects.jar;lib\commons-lang-2.3.jar;lib\log4j-1.2.15.jar;lib\postgresql-8.3-603.jdbc3.jar;lib\c3p0-0.9.1.2.jar;%JAVA_HOME%\lib\tools.jar" org.exigencecorp.bd.BuildRunner %*
