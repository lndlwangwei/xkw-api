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
def nodes = ['dev', '28test']
def branches = [:]
branches['dev'] = {
    node('dev') {
        echo 'dev'
    }
}
branches['28test'] = {
    node('28test') {
        echo '28test'
    }
}
//nodes.each {nodeName ->
//    branches[nodeName] = {
//        node(nodeName) {
//            echo nodeName
//        }
//    }
//}

parallel branches

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