#!/bin/bash

#停止临时服务，如果不存在此服务，没有副作用
/home/xkwx/scripts/goagent/rbm/stopTempServer.sh

#删除临时服务的代码
rm -rf /data/apps/tempServer/rbm/webapps/*

#将当前正式服务的代码复制到临时服务
cp -r /data/apps/rbm_server/webapps/ROOT/ /data/apps/tempServer/rbm/webapps

#修改临时服务的配置
sed -i 's/rbm.instance.type=prod/rbm.instance.type=temp/g' /data/apps/tempServer/rbm/webapps/ROOT/WEB-INF/classes/services.properties

#启动临时服务
java -javaagent:/data/lib/spring-instrument-5.0.2.RELEASE.jar -jar /data/jetty/start.jar jetty.base=/data/apps/tempServer/rbm &
#/data/jetty/bin/jetty.sh start
echo 'start success!'
exit $?
