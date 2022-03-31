pipeline {
    agent any
    environment {
        DOCKER_PASSWORD = credentials("zA5m5RJlBXq1QFFUyq6bry1ci2ToWbrJUkFezTOyoUYThZjNv6UgGzx5TBJ9D1ugebOsGarCxVGDKpZyhiBaJPe8HA3jATpsR6AunIRPU3qBrDTjEAbHg2R+9NKI6d0H/vcjxUAfAA8FbUoZk3LmX9p2Q00xJXPZPZ5ZKLJmwoo=")
    }

    stages {
        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Tag image') {
              steps {
                script {
                    GIT_TAG = sh([script: 'git fetch --tag && git tag', returnStdout: true]).trim()
                    MAJOR_VERSION = sh([script: 'git tag | cut -d . -f 1', returnStdout: true]).trim()
                    MINOR_VERSION = sh([script: 'git tag | cut -d . -f 2', returnStdout: true]).trim()
                    PATCH_VERSION = sh([script: 'git tag | cut -d . -f 3', returnStdout: true]).trim()
                }
                sh "docker build -t radradradrad/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."
              }
        }
    }

}
