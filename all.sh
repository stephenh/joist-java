#!/bin/sh

for p in util domain domain-testing migrations features
do
    cd $p
    buildr $*
    code=$?
    cd ..
    if [ "$code" != "0" ]
    then
        exit $code
    fi
done

