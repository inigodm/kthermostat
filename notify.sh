#!/bin/sh
/usr/bin/inotifywait -e create -mrq /wars | while read line; do
    sleep 3
    cp /wars/*.war /usr/local/tomcat/webapps
    done
