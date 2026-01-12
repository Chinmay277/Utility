pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "chinmay277"
        DOCKER_REPO     = "demo2"
        DOCKER_TAG      = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Detect Changed Services') {
            steps {
                script {
                    def changedFiles = sh(
                        script: "git diff --name-only HEAD~1 HEAD",
                        returnStdout: true
                    ).trim().split("\n")

                    def services = []

                    changedFiles.each { file ->
                        if (file.startsWith("services/")) {
                            def serviceName = file.split("/")[1]
                            services << serviceName
                        }
                    }

                    services = services.unique()

                    if (services.isEmpty()) {
                        echo "No service changes detected. Skipping deployment."
                        currentBuild.result = 'SUCCESS'
                        env.CHANGED_SERVICES = ""
                    } else {
                        env.CHANGED_SERVICES = services.join(",")
                        echo "Changed services: ${env.CHANGED_SERVICES}"
                    }
                }
            }
        }

        stage('Build & Push Changed Services') {
            when {
                expression { env.CHANGED_SERVICES?.trim() }
            }
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )]) {

                        sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"

                        env.CHANGED_SERVICES.split(",").each { service ->
                            echo "Building and pushing ${service}"

                            sh """
                              docker build \
                                -t ${DOCKER_REGISTRY}/${DOCKER_REPO}:${service}-${DOCKER_TAG} \
                                services/${service}

                              docker push ${DOCKER_REGISTRY}/${DOCKER_REPO}:${service}-${DOCKER_TAG}
                            """
                        }
                    }
                }
            }
        }

        stage('Deploy Changed Services to Minikube') {
            when {
                expression { env.CHANGED_SERVICES?.trim() }
            }
            steps {
                script {
                    env.CHANGED_SERVICES.split(",").each { service ->
                        echo "Deploying ${service}"

                        sh """
                          sed -i 's|IMAGE_PLACEHOLDER|${DOCKER_REGISTRY}/${DOCKER_REPO}:${service}-${DOCKER_TAG}|' \
                          k8s/${service}/deployment.yaml

                          kubectl apply -f k8s/${service}/deployment.yaml
                          kubectl apply -f k8s/${service}/service.yaml
                        """
                    }
                }
            }
        }
    }
    
}
