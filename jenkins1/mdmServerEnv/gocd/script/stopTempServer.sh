#!/bin/bash
echo 'start sleeping'
#sleep 1m 
echo 'finish sleeping, stopping service'
rbmid=`ps -ef | grep 'tempServer/rbm' | grep -v grep | awk '{print $2}'`
if [ -z $rbmid ]; then
echo 'no process [jetty-temp-rbm] running'
exit $?
else
kill -9 $rbmid
# 杀进程后，睡眠一分钟，确保进行完全退出
#sleep 1m
exit $?
fi
