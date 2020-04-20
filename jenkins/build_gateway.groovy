node('159test') {
    stage('build') {
        git 'https://github.com/lndlwangwei/xkw-api'

        sh 'mvn clean package -Dmaven.test.skip=true'
        dir('gateway-admin-client') {
//            sh 'npm --registry https://registry.npm.taobao.org install'
            sh "ng build --prod; cd dist/; zip -r ../gateway_client.zip ./"
        }

        archiveArtifacts 'gateway/target/*.jar'
        archiveArtifacts 'gateway-admin/target/*.jar'
    }
}