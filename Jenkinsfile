pipeline {
    agent any
    stages {
    script {
                        GIT_TAG = sh([script: 'git fetch --tag && git tag', returnStdout: true]).trim()
                        MAJOR_VERSION = sh([script: 'git tag | cut -d . -f 1', returnStdout: true]).trim()
                        MINOR_VERSION = sh([script: 'git tag | cut -d . -f 2', returnStdout: true]).trim()
                        PATCH_VERSION = sh([script: 'git tag | cut -d . -f 3', returnStdout: true]).trim()
                    }
        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Tag image') {
              steps {
                sh "docker build -t radradradrad/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."
              }
        }
               stage('Push image') {
              steps {
              sh "docker login docker.io -u radradradrad -p qwerty1234"
              sh "docker push radradradrad/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1))"
              }
        }

    }

}
