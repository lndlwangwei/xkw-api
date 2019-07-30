node('dev') {
//    def externalMethod = load("externalScript.groovy")
//
//    externalMethod.sayHello('wangwei')
    echo 'hello wangwei'

    stage('init') {
        git 'https://github.com/lndlwangwei/xkw-api/tree/master/jenkins'
        if (fileExists('/data/jenkins')) {
            echo 'file exists'

        }
        else {
            echo 'file not exists'
        }
    }
}