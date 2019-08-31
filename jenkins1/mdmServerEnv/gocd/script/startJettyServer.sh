#!/bin/bash
. /etc/profile

jetty_base=$1
java_agent_jar=$2

java -javaagent:${java_agent_jar} -jar ${JETTY_HOME}/start.jar jetty.base=${jetty_base} &

