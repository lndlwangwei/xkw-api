#! /bin/sh
# 在切换服务前，先睡眠1分钟，防止服务还没启动完成，就切换过去了
#sleep 1m;

. /etc/profile

# 将$2端口的服务作为临时服务，此服务不再接受新请求
sudo sed -i 's/server 127.0.0.1:'$2';/server 127.0.0.1:'$2' backup;/g' ${NGINX_HOME}/conf/nginx.conf

# 将$1端口的服务作为优先服务，请求会逐渐流到这个服务器
sudo sed -i 's/server 127.0.0.1:'$1' backup;/server 127.0.0.1:'$1';/g' ${NGINX_HOME}/conf/nginx.conf

sudo ${NGINX_HOME}/sbin/nginx -s reload
exit $?
