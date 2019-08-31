//  要求服务器系统是centos6.5及以上

def basePath = '/data/test'
// nginx config
def nginxBasePath = "$basePath/nginx"
def nginxLogPath = "$basePath/log"
def nginxDockerImageName = "xuekewang/nginx:v1"
def nginxContainerName = "docker-nginx"
// jetty config
def jettyBasePath = "$basePath/nginx"
def jettyDockerImageName = "xuekewang/jetty-9:v1"
// gocd config
def gocdBasePath = "$basePath/gocd"
def gocdAgentImageName = 'gocd/gocd-agent-alpine-3.10:v19.7.0'
def gocdAgentContainerName = 'gocd-agent'

node('28test') {
    git 'https://github.com/lndlwangwei/xkw-api'
    def scriptHome = "$WORKSPACE/jenkins1"

    if (!fileExists(basePath)) {
        sh "mkdir $basePath"
    }

    stage('prepare nginx') {

        if (!fileExists(nginxBasePath)) {
            sh "mkdir $nginxBasePath"
        }

        // 复制nginx配置文件
        sh "cp -r $scriptHome/mdmServerEnv/nginx/conf $nginxBasePath"

        // 创建nginx日志目录
        if (!fileExists(nginxLogPath)) {
            sh "mkdir $nginxLogPath"
        }

        sh "docker pull $nginxDockerImageName"

        sh "docker stop $nginxContainerName"
        def status = sh(script: "docker rm $nginxContainerName", returnStatus: true)
        echo "status: $status"

        sh "docker run -d -p 9080:9080 -v $nginxLogPath:/var/log/nginx -v $nginxBasePath/conf:/etc/nginx/ --name=$nginxContainerName $nginxDockerImageName"
    }

    stage('prepare jetty') {
        if (!fileExists(nginxBasePath)) {
            sh "mkdir $nginxBasePath"
        }

        sh "docker pull $jettyDockerImageName"
    }

    stage('prepare gocd agent') {
        if (!fileExists(gocdBasePath)) {
            sh "mkdir $gocdBasePath"
        }

        sh "docker pull $gocdAgentImageName"

        sh(script: "docker rm $gocdAgentContainerName", returnStatus: true)
        sh "docker run -d -e GO_SERVER_URL=\"http://36.110.49.106:8154/go\" --name $gocdAgentContainerName $gocdAgentImageName"

        // 准备go agent要调用的脚本
        sh "cp $scriptHome/mdmServerEnv/gocd/script $gocdBasePath"
        sh "chmod +x $scriptHome/mdmServerEnv/gocd/script/*"
    }
}