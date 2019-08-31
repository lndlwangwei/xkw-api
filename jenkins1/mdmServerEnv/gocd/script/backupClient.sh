#!/bin/bash
clientDir=$1
clientBakDir=$2
if [ -d $clientBakDir ]; then
 rm -rf $clientBakDir
fi

if [ -d $clientDir ]; then
 mv $clientDir $clientBakDir
fi
mkdir -p $clientDir
exit $?
