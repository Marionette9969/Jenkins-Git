pipeline {
    agent any

    stages {
        stage('Docker Check') {
            steps {
            sh 'docker --version'
            }
        }
        
        stage('Github Token') {
            steps {
                checkout([$class: 'GitSCM', 
                    userRemoteConfigs: [[
                        url: 'https://github.com/Marionette9969/Jenkins-Git',
                        credentialsId: 'Docker-Repo-Testing'
            ]],
            branches: [[name: '*/main']]
            ])
            }
        }

        stage('Docker Build') {
            steps {
                script {
                dockerImage = docker.build("jenkins-docker-hello")
                }
            }
        }

        stage('Deploy Docker') {
            steps {
                script {
                    dockerImage.run()
                }
                
            }
        }
    }
}



pipeline {
    agent any

    stages {
        stage('Clone Repo') {
            steps {
                checkout([$class: 'GitSCM', 
                    userRemoteConfigs: [[
                        url: 'https://github.com/Marionette9969/Jenkins-Git',
                        credentialsId: 'Docker-Repo-Testing'
                    ]],
                    branches: [[name: '*/main']]
                ])
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("jenkins-docker-hello")
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    dockerImage.run()
                }
            }
        }
    }
}



===========================================================================

Test Docker on VM Server

pipeline {
    agent any
    stages {
        stage('Check Docker') {
            steps {
                sh 'docker --version'
            }
        }
        
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
                    dockerImage = docker.build("jenkins-docker-hello")
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    dockerImage.run()
                }
            }
        }
    }
}

Top already working

Test Docker on VM Server

pipeline {
    agent any
    stages {
        stage('Check Docker Version') {
            steps {
                sh 'docker --version'
            }
        }
        
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
                    try {
                        def container = dockerImage.run()
                        echo "Container ID: ${container.id}"
                        } catch (err) {
                        echo "Failed to run container: ${err}"
                            }
                        }
                    }   
        }

        stage('Run Docker Container') {
            steps {
                script {
                    try {
                        def container = dockerImage.run()
                        echo "Container ID: ${container.id}"
                    } catch (err) {
                        echo "Failed to run container: ${err}"
                    }
                }
            }
        }
    }
}







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

===================================================


pipeline {
    agent any

    stages {
        stage('Start NGINX Container') {
            steps {
                script {
                    // Pull NGINX image
                    sh 'docker pull nginx'

                    // Run NGINX container on port 3001
                    sh 'docker run -d --name test-nginx -p 3001:80 nginx'

                    // Wait for container to be ready
                    sleep(time: 5, unit: 'SECONDS')
                }
            }
        }

        stage('Test NGINX') {
            steps {
                script {
                    // Use curl to test HTTP response
                    sh 'curl -s -o /dev/null -w "%{http_code}" http://localhost:3001 | grep 200'
                }
            }
        }
        
        post {
        always {
            echo 'Cleaning up container...'
            sh 'docker stop test-nginx || true'
            sh 'docker rm test-nginx || true'
            }
    
         }
    }
}



pipeline {
    agent any

    stages {
        stage('Start NGINX Container') {
            steps {
                sh 'docker run -d --name test-nginx -p 3001:80 nginx'
                sleep(time: 5, unit: 'SECONDS')
            }
        }

        stage('Test NGINX') {
            steps {
                sh 'curl -s -o /dev/null -w "%{http_code}" http://localhost:3001 | grep 200'
            }
        }
    }

    // 👇 This is correct — top-level post block
    post {
        always {
            echo 'Cleaning up container...'
            sh 'docker stop test-nginx || true'
            sh 'docker rm test-nginx || true'
        }
    }
}