pipeline {
    agent any

    environment {
        TEAM = 'your-team' // Set via Jenkins UI, parameters, or elsewhere
    }

    stages {
        stage('Parallel Testing') {
            parallel {
                stage('Branch 1') {
                    when {
                        expression {
                            return env.TEAM == 'other-team'
                        }
                    }
                    steps {
                        echo 'Running Branch 1 (other team)'
                    }
                }

                stage('Branch 2') {
                    when {
                        expression {
                            return env.TEAM == 'your-team'
                        }
                    }
                    steps {
                        echo 'Running Branch 2 (your team)'
                    }
                }
            }
        }
    }
}





Already Working, used for backup, dont change anything

pipeline {
    agent any
    stages {
        stage('Clone Repo') {
            steps {
                checkout([$class: 'GitSCM',
                    userRemoteConfigs: [[
                        url: 'https://github.com/Marionette9969/Jenkins-Git',
                        credentialsId: 'jenkins-docker'
                    ]],
                    branches: [[name: '*/main']]
                ])
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("hello-web")
                }
            }
        }

        stage('Run Container on Port 3000') {
            steps {
                script {
                    // Run in detached mode and map host:container ports
                    sh 'docker run -d -p 3000:3000 --name hello-web hello-web'
                }
            }
        }
    }
}

pipeline {
    agent any

    stages {
        stage ('Parallel Testing') {
    parallel {
        branch1: {
            echo 'Running branch 1'
            // some steps
            echo "Testing Branch 1"
        }
        branch2: {
            echo 'Running branch 2'
            // some steps
            echo "Testing Branch 2"
        }
    }
        }
}
}

===================================================

pipeline {
    agent any
    stages {
        stage('Clone Repo') {
            steps {
                checkout([$class: 'GitSCM',
                    userRemoteConfigs: [[
                        url: 'https://github.com/Marionette9969/Jenkins-Git',
                        credentialsId: 'jenkins-docker'
                    ]],
                    branches: [[name: '*/main']]
                ])
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("hello-web")
                }
            }
        }

        stage('Run and Check Container on Port 3000') {
            steps {
                script {
            def containerExists = sh(script: "docker ps -q -f name=hello-web", returnStdout: true).trim()
            if (containerExists) 
            {
                echo "Container 'hello-web' is already running. Skipping docker run."
            } 
            else 
            {
                echo "Starting new container 'hello-web' on port 3000."
                sh 'docker run -d -p 3000:3000 --name hello-web hello-web'
            }
                    
                }
     
            }
        }
    }
}

pipeline {
    agent any

    stages {
        stage ('Parallel Testing') {
    parallel {
        branch1: {
            echo 'Running branch 1'
            // some steps
            echo "Testing Branch 1"
        }
        branch2: {
            echo 'Running branch 2'
            // some steps
            echo "Testing Branch 2"
        }
    }
        }
}
}
