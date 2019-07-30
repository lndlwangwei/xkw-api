node('dev') {

    git 'https://github.com/lndlwangwei/xkw-api'

//    def rootDir = pwd()
//    def externalMethod = load "${rootDir}/jenkins/externalScript.groovy"
//
//    echo "${rootDir}/jenkins/externalScript.groovy"
//
//    externalMethod.sayHello('wangwei')
//    echo 'hello wangwei'

    stage('init') {
        def envProp = load("${WORKSPACE}/jenkins/script/envProp.groovy")
        for (int i = 0; i < 3; i++) {
            echo envProp[i]
        }

        if (fileExists('/data/jenkins')) {
            echo 'file exists'
        }
        else {
            echo 'file not exists'
        }
    }
}