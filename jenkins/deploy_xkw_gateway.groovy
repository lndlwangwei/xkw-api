node('dev') {
    def externalMethod = load("externalScript.groovy")

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