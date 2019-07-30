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
////envProp.nodes().each {nodeName ->
////    node(nodeName) {
////        echo nodeName
////    }
////}
//def branches = [:]
//for (int i = 0; i < 2; i++) {
//    def nodeName
//    if (i == 0) nodeName = 'dev'
//    else nodeName = '28test'
//
//    echo nodeName
//
//    branches[nodeName] = node(nodeName) {
//        echo nodeName
//    }
//}
//
//parallel branches


def stringsToEcho = ["dev", "28test"]

// The map we'll store the parallel steps in before executing them.
def stepsForParallel = stringsToEcho.collectEntries {
    ["echoing ${it}" : transformIntoStep(it)]
}

// Actually run the steps in parallel - parallel takes a map as an argument,
// hence the above.
parallel stepsForParallel

// Take the string and echo it.
def transformIntoStep(inputString) {
    // We need to wrap what we return in a Groovy closure, or else it's invoked
    // when this method is called, not when we pass it to parallel.
    // To do this, you need to wrap the code below in { }, and either return
    // that explicitly, or use { -> } syntax.
    return {
        node(inputString) {
            echo inputString
        }
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