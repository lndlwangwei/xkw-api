node('28test') {
    stage('build') {
        git 'https://github.com/lndlwangwei/xkw-api'

        dir('gateway') {
            sh 'mvn clean install -Dmaven.test.skip=true'

            archiveArtifacts 'target/*.jar'
        }
    }
}