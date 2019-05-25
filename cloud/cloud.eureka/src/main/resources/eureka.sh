#!/bin/sh
#!/bin/bash

dir=.
app=cloud.eureka
time=`date +%Y%m%d%H%M%S`
bakDir=$dir/zbak/$app'_'$time

echo -e '>>>'
echo -e '>>> '${app}' start ...'

echo -e ''
echo -e '>>> kill pid'
kill -12 `cat lib/${app}.pid`
# kill -9 `cat lib/${app}.pid`

echo -e ''
echo -e '>>> backup jar & logs'
mkdir -pv $bakDir
yes|mv $dir/logs/$app/* $bakDir
yes|cp -rf $dir/lib/$app-0.0.1.jar $bakDir
#rm -rf $dir/logs/*
rm -rf $dir/hs_err_pid*

echo -e ''
echo -e '>>> boot '${app}
echo -e '\n'
nohup java -server -Xms512M -Xmx2048M -jar $dir/lib/$app-0.0.1.jar --spring.profiles.active=pord1 > /dev/null 2>&1 &

# nohup java -server -Xms1024M -Xmx1024M -jar $dir/lib/$app-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
# nohup java -server -Xms1024M -Xmx2048M -jar $dir/lib/$app-0.0.1.jar --spring.profiles.active=pord > /dev/null 2>&1 &
# nohup java -server -Xms1024M -Xmx1024M -jar $dir/lib/$app-0.0.1-SNAPSHOT.jar --spring.profiles.active=test > logs/$app.log 2>&1 &

echo $! > $dir/lib/$app.pid && cat $dir/lib/$app.pid
echo -e '<<< '${app}' started !!!\n'

sleep 5s
echo -e ''
tail -f $dir/logs/$app-server/spring.log

