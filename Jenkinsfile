pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "chinmay277"
        DOCKER_REPO     = "demo2"
        DOCKER_TAG      = "${BUILD_NUMBER}"
        KUBECONFIG      = "/home/user/.kube/config"  // WSL path to kubeconfig
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

                    def diffCmd = '''
                    if [ -z "$GIT_PREVIOUS_SUCCESSFUL_COMMIT" ]; then
                        git diff --name-only HEAD~1 HEAD || true
                    else
                        git diff --name-only $GIT_PREVIOUS_SUCCESSFUL_COMMIT $GIT_COMMIT
                    fi
                    '''

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
                    echo "Changed files:\n${changedList}"

                    def services = []
                    def fullRedeploy = false

                    changedList.each { file ->

                        if (file == "ManualReDeployer.txt") {
                            fullRedeploy = true
                        }

                        if (file.contains("/")) {
                            services.add(file.split("/")[0])
                        }
                    }

                    if (fullRedeploy) {
                        echo "Manual redeploy triggered. Deploying ALL services."

                        def allServices = sh(
                            script: '''
                              ls -d */ | \
                              grep -v k8s | \
                              grep -v .git | \
                              sed 's#/##'
                            ''',
                            returnStdout: true
                        ).trim()

                        services = allServices ? allServices.split("\n") : []
                    }

                    // ---- SANDBOX SAFE DEDUPLICATION ----
                    def uniqueServices = []
                    services.each { svc ->
                        if (!uniqueServices.contains(svc)) {
                            uniqueServices.add(svc)
                        }
                    }
                    services = uniqueServices
                    // -----------------------------------

                    if (services.isEmpty()) {
                        echo "No deployable services detected."
                        env.CHANGED_SERVICES = ""
                    } else {
                        env.CHANGED_SERVICES = services.join(",")
                        echo "Services selected for deployment: ${env.CHANGED_SERVICES}"
                    }
                }
            }
        }

        stage('Build & Push Docker Images') {
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

                        sh '''
                          echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        '''

                        env.CHANGED_SERVICES.split(",").each { service ->
                            echo "Building and pushing image for: ${service}"

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
                        echo "Deploying service: ${service}"

                        sh """
                          sed -i 's|IMAGE_PLACEHOLDER|${DOCKER_REGISTRY}/${DOCKER_REPO}:${service}-${DOCKER_TAG}|' \
                          k8s/${service}/deployment.yaml

                          kubectl apply --validate=false -f k8s/${service}/deployment.yaml
                          kubectl apply --validate=false -f k8s/${service}/service.yaml

                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully."
        }
        failure {
            echo "Pipeline failed. Check logs above."
        }
    }
}
