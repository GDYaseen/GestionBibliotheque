pipeline {
    agent any
    environment {
        MAVEN_HOME = tool 'Maven'
        SONAR_TOKEN = credentials('gestion-biblio-token')
    }
    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[
                    url: 'https://github.com/GDYaseen/GestionBibliotheque.git',
                    credentialsId: 'github-token'
            ]]
        ])
            }
        }
        stage('Build') {
            steps {
                sh '${MAVEN_HOME}/bin/mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh '${MAVEN_HOME}/bin/mvn test'
            }
        }
        stage('Quality Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "${MAVEN_HOME}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=gestion-bibliotheque -Dsonar.projectName='gestion-bibliotheque' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=${SONAR_TOKEN}"
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
            // emailext to: 'lahrigayassine@gmail.com',
            //     subject: 'Build Success',
            //     body: 'Le build a été complété avec succès.'
        }
        failure {
            echo "Fail"
            // emailext to: 'lahrigayassine@gmail.com',
            //     subject: 'Build Failed',
            //     body: 'Le build a échoué.'
        }
    }
}
