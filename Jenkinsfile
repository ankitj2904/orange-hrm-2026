pipeline {
    agent any
    
    tools {
        maven 'Maven 3.8.6'
        jdk 'JDK 11'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }
        
        stage('Test') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: 'target/surefire-reports/**, reports/**, screenshots/**', fingerprint: true
                }
            }
        }
    }
    
    post {
        always {
            emailext body: '${DEFAULT_CONTENT}',
                     subject: '${DEFAULT_SUBJECT}',
                     to: '${DEFAULT_RECIPIENTS}'
            cleanWs()
        }
    }
} 