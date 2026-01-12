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

        stage('Detect Deployment Scope') {
            steps {
                script {

                    def diffCmd = """
                    if [ -z "${GIT_PREVIOUS_SUCCESSFUL_COMMIT}" ]; then
                        git diff --name-only ${GIT_COMMIT}
                    else
                        git diff --name-only ${GIT_PREVIOUS_SUCCESSFUL_COMMIT} ${GIT_COMMIT}
                    fi
                    """

                    def changedFiles = sh(
                        script: diffCmd,
                        returnStdout: true
                    ).trim()

                    if (!changedFiles) {
                        echo "No file changes detected."
                        env.CHANGED_SERVICES = ""
                        return
                    }

                    def changedList = changedFiles.split("\n")
                    echo "Changed files: ${changedList}"

                    def services = []
                    def fullRedeploy = false

                    changedList.each { file ->

                        if (file == "ManualReDeployer.txt") {
                            fullRedeploy = true
                        }

                        // Root-level services
                        if (file.contains("/")) {
                            services << file.split("/")[0]
                        }
                    }

                    if (fullRedeploy) {
                        echo "Manual redeploy requested. Deploying ALL services."
                        services = sh(
                            script: "ls -d */ | grep -v k8s | grep -v .git | sed 's#/##'",
                            returnStdout: true
                        ).trim().split("\n")
                    }

                    // ---- SANDBOX-SAFE DEDUPLICATION ----
                    def uniqueServices = new HashSet()
                    services.each { svc ->
                        uniqueServices.add(svc)
                    }
                    services = uniqueServices.toArray().toList()
                    // -----------------------------------

                    if (services.isEmpty()) {
                        echo "No deployable services detected."
                        env.CHANGED_SERVICES = ""
                    } else {
                        env.CHANGED_SERVICES = services.join(",")
                        echo "Services to deploy: ${env.CHANGED_SERVICES}"
                    }
                }
            }
        }

        stage('Build & Push Images') {
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
                            echo "Building & pushing image for ${service}"

                            sh """
                              docker build \
                                -t ${DOCKER_REGISTRY}/${DOCKER_REPO}:${service}-${DOCKER_TAG} \
                                ${service}

                              docker push ${DOCKER_REGISTRY}/${DOCKER_REPO}:${service}-${DOCKER_TAG}
                            """
                        }
                    }
                }
            }
        }

        stage('Deploy to Minikube') {
            when {
                expression { env.CHANGED_SERVICES?.trim() }
            }
            steps {
                script {
                    env.CHANGED_SERVICES.split(",").each { service ->
                        echo "Deploying ${service} to Minikube"

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
