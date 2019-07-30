node('dev') {

    git 'https://github.com/lndlwangwei/xkw-api'

    def rootDir = pwd()
    def externalMethod = load "${rootDir}/jenkins/externalScript.groovy"

    echo "${rootDir}/jenkins/externalScript.groovy"

    externalMethod.sayHello('wangwei')
    echo 'hello wangwei'

    stage('init') {

        if (fileExists('/data/jenkins')) {
            echo 'file exists'

        }
        else {
            echo 'file not exists'
        }
    }
}