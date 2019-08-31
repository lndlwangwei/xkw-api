#!/bin/bash
server_dir=$1
war_name=$2
unzip -d $server_dir	$server_dir/$war_name
if [ $? -eq 0 ]; then
rm -f $server_dir/$war_name
fi
exit $?
