#!/bin/bash
OJDBC=${OJDBC:="/usr/local/java/instantclient_11_2/ojdbc6.jar"}

mvn install:install-file \
    -Dpackaging=jar \
    -DgroupId=com.oracle \
    -DartifactId=ojdbc6 \
    -Dversion=11.2.0.4.0 \
    -Dfile=$OJDBC
