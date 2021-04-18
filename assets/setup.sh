#!/bin/bash

[ ! -d /app/log ] && mkdir -p /app/log &&
[ ! -d /app/static ] && mkdir -p /app/static &&
mv /app/startup.sh /usr/sbin/startup.sh &&
chmod +x /usr/sbin/startup.sh
