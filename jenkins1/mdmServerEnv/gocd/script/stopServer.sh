#!/bin/bash

server_name=$1
echo 'start sleeping'

echo $server_name
echo 'finish sleeping, stopping service'
pid=`ps -ef | grep '${server_name}' | grep -v grep | awk '{print $2}'`
echo $pid
sleep 1m
if [ -z $pid ]; then
echo 'no process [$server_name] running'
exit $?
else
kill -9 $pid
# 杀进程后，睡眠一分钟，确保进行完全退出
sleep 1m
exit $?
fi
