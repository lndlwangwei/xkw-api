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

//def envProp = load('jenkins/script/envProp.groovy')
def nodes = ['dev':['gateway1', 'gateway2', 'gateway3'], '28test':['gateway1', 'gateway2']]
def serviceBasePath = '/data/service/gateways'

nodes.entrySet().each {entry ->
    def nodeName = entry.key
    def services = entry.value

    node(nodeName) {
        stage('deploy') {
            services.each {
                def servicePath = "$serviceBasePath/$it"
                if (!fileExists(servicePath)) {
                    sh "mkdir ${servicePath}"
                    writeFile encoding: 'utf-8', file: "$servicePath/test.txt", text: "this is $it"
                }
            }
        }
    }
}

//branches.entrySet().each {
//    it.value()
//}
//parallel branches

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