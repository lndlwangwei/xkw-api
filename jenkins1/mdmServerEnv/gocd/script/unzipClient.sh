#!/bin/bash
clientDir=$1
clientZipName=$2
unzip -d $clientDir $clientDir/$clientZipName
if [ $? -eq 0 ]; then
rm -f $clientDir/$clientZipName
fi
exit $?

