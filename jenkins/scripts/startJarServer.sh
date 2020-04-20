#!/usr/bin/env bash
workspace=$1
artifactName=$2
env=$3
javaagentJar=$4

if [ -f ${workspace}/pid ]; then
    echo 'pid exists, 服务已经启动！'
    exit 0
fi

javaagentOption=''
if [ -n "$javaagentJar" ]; then
    echo "has jar!"
    javaagentOption="-javaagent:"$javaagentJar
fi

echo $javaagentOption

java $javaagentOption -Dfile.encoding=utf-8 -Djava.io.tmpdir=/data/tmp -jar ${workspace}/${artifactName} --spring.profiles.active=${env} > /dev/null &

echo "$!" > ${workspace}/pid
echo "$!"