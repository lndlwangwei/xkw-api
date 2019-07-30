node('28test') {
    stage('build') {
        git 'https://github.com/lndlwangwei/xkw-api'
        sh 'mvn install -Dmaven.test.skip=true'

        archiveArtifacts 'target/*.jar'
    }
}