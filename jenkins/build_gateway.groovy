node('28test') {
    stage('build') {
        git 'https://github.com/lndlwangwei/xkw-api'

        sh 'mvn clean install -Dmaven.test.skip=true'
        archiveArtifacts 'gateway/target/*.jar'
//        dir('gateway') {
//            sh 'mvn clean install -Dmaven.test.skip=true'
//
//            archiveArtifacts 'target/*.jar'
//        }
    }
}