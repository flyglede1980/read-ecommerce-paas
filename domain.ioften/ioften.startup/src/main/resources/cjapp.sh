#!/bin/sh
#!/bin/bash

dir=.
app=cjapp
time=`date +%Y%m%d%H%M%S`
bakDir=$dir/zbak/$app'_'$time

echo -e '>>>'
echo -e '>>> '${app}' start ...'

echo -e ''
echo -e '>>> kill pid'
kill -12 `cat ${dir}/lib/${app}.pid`
# kill -9 `cat ${dir}/lib/${app}.pid`

echo -e ''
echo -e '>>> backup jar & logs'
mkdir -pv $bakDir
yes|mv $dir/logs/* $bakDir
yes|cp -rf $dir/lib/$app-0.0.1.jar $bakDir
#rm -rf $dir/logs/*
rm -rf $dir/hs_err_pid*

echo -e ''
echo -e '>>> boot '${app}
echo -e '\n'
nohup java -server -Xms1024M -Xmx2048M -jar $dir/lib/$app-0.0.1.jar --spring.profiles.active=pord > /dev/null 2>&1 &

# nohup java -server -Xms1024M -Xmx2048M -jar $dir/lib/$app-0.0.1.jar > /dev/null 2>&1 &
# nohup java -server -Xms2048M -Xmx4096M -jar $dir/lib/$app-0.0.1.jar --spring.profiles.active=pord > /dev/null 2>&1 &
# nohup java -server -Xms1024M -Xmx2048M -jar $dir/lib/$app-0.0.1.jar --spring.profiles.active=test > logs/$app.log 2>&1 &

echo $! > $dir/lib/$app.pid && cat $dir/lib/$app.pid
echo -e '<<< '${app}' started !!!\n'

sleep 5s
echo -e ''
tail -f $dir/logs/spring.log

