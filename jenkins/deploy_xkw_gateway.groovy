node('dev') {
    echo WORKSPACE
    def externalMethod = load("${WORKSPACE}/jenkins/externalScript.groovy")

    echo externalMethod

    externalMethod.sayHello('wangwei')
    echo 'hello wangwei'

    stage('init') {
        git 'https://github.com/lndlwangwei/xkw-api'
        if (fileExists('/data/jenkins')) {
            echo 'file exists'

        }
        else {
            echo 'file not exists'
        }
    }
}