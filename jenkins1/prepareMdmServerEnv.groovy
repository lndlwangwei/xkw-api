def basePath = '/data/test'
// nginx config
def nginxBasePath = "$basePath/nginx"
def nginxLogPath = "$basePath/log"
def nginxDockerImageName = "xuekewang/nginx:v1"
def nginxContainerName = "docker-nginx"
// jetty config
def jettyBasePath = "$basePath/nginx"
def jettyDockerImageName = "xuekewang/jetty9:v1"

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
    }
}