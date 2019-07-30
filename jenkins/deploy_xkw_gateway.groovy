//node('dev') {
//    git 'https://github.com/lndlwangwei/xkw-api'
//    def envProp = load("${WORKSPACE}/jenkins/script/envProp.groovy")
//
//    stage('deploy') {
//        def serviceBasePath = '/data/service/gateway'
//        def claster1 = envProp.gatewayClaster1()
//
//        nodes.each {node ->
//            echo node
//        }
//
//        if (fileExists('/data/jenkins')) {
//            echo 'file exists'
//        }
//        else {
//            echo 'file not exists'
//        }
//    }
//}

def envProp = load('jenkins/script/envProp.groovy')
envProp.nodes().each {nodeName ->
    node(nodeName) {
        echo nodeName
    }
}

//node('28test') {
//    git 'https://github.com/lndlwangwei/xkw-api'
//    def envProp = load("${WORKSPACE}/jenkins/script/envProp.groovy")
//
//    stage('deploy') {
//        def serviceBasePath = '/data/service/gateway'
//        def claster1 = envProp.gatewayClaster1()
//
//
//        claster1.each {node ->
//            echo node
//        }
//
//        if (fileExists('/data/jenkins')) {
//            echo 'file exists'
//        }
//        else {
//            echo 'file not exists'
//        }
//    }
//}

// 获取node names
//@NonCPS
//def nodeNames() {
//    return jenkins.model.Jenkins.instance.nodes.collect { node -> node.name }
//}