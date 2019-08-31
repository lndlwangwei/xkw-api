#!/bin/bash
serverDir=$1
serverBakDir=$2

echo $serverDir
echo $serverBakDir

if [ -d $serverBakDir ]; then
 rm -rf $serverBakDir
fi

if [ -d $serverDir ]; then
 mv $serverDir $serverBakDir
fi
mkdir -p $serverDir
exit $?
