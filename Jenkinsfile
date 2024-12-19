pipeline {
    agent any
    environment {
        MAVEN_HOME = tool 'Maven'
        SONAR_TOKEN = credentials('gestion-biblio-token')
    }
    stages {
        stage('Checkout') {
            steps {
                git branch:'main' , url:'https://github.com/GDYaseen/GestionBibliotheque.git'
            }
        }
        stage('Build') {
            steps {
                sh "mvn clean compile"
            }
        }
        stage('Test') {
            steps {
                sh "mvn test"
            }
        }
        stage('Quality Analysis') {
            steps {
                withSonarQubeEnv('Sonarqube') {
                    sh "mvn clean verify sonar:sonar -Dsonar.projectKey=gestion-bibliotheque -Dsonar.projectName='gestion-bibliotheque' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=${SONAR_TOKEN}"
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Déploiement simulé réussi'
            }
        }
    }
    post {
        success {
            echo "Success"
            emailext(to: 'lahrigayassine@gmail.com',
                subject: 'Build Success',
                body: 'Le build a été complété avec succès.')
        }
        failure {
            echo "Fail"
            emailext(to: 'lahrigayassine@gmail.com',
                subject: 'Build Failed',
                body: 'Le build a échoué.')
        }
    }
}
