#!/bin/sh

if [ "$JAVA_HOME" == "" ] ; then
    echo "Error: JAVA_HOME is not defined."
    exit
fi

mkdir -p bin/bd
"$JAVA_HOME/bin/javac" -cp "lib/bd/*" -sourcepath src/bd -d bin/bd src/bd/*.java
"$JAVA_HOME/bin/java" -Xmx512m -cp "bin/bd;lib/bd/*;$JAVA_HOME/lib/tools.jar" org.exigencecorp.bd.BuildRunner $*

