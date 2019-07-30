node('28test') {
    stage('build') {
        dir('gateway') {
            git 'https://github.com/lndlwangwei/xkw-api'
            sh 'mvn clean install -Dmaven.test.skip=true'

            archiveArtifacts 'target/*.jar'
        }
    }
}