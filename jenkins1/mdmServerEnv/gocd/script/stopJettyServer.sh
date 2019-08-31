#!/bin/bash

jetty_base=$1
pid_file=$jetty_base/jetty/jetty.pid
#pid_file=/home/xkwx/apps/qbm-wordhandler1/jetty/jetty.pid
if [ ! -f $pid_file ];then
	echo '找不到$pid_file'
	exit 1
fi

pid=$(cat $pid_file)
kill -9 $pid

exit $?
