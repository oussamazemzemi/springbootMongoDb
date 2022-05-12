def SONAR_HOST_URL="http://localhost:9000/"
def SONAR_PROJECT_NAME="studentinformationsystem" 
def SONAR_TOKEN="cea794b1758519e55bbd860b51fed9a8203b0223"

pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/oussamazemzemi/springbootMongoDb.git'

                // (bat: Windows Batch Script or sh: Shell Script) =>  Run Maven on a Unix agent.
                //to ignore TEST add: -Dmaven.test.failure.ignore=true
                bat "mvn clean package" 

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'api/target/*.jar'
                }
            }
        }
        
        stage('Sonar Analysis') {
            steps {
                // (bat: Windows Batch Script or sh: Shell Script) =>  Run Maven on a Unix agent.
                //to ignore TEST add: -Dmaven.test.failure.ignore=true
                bat "mvn sonar:sonar -Psonar -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.projectKey=${SONAR_PROJECT_NAME} -Dsonar.login=${SONAR_TOKEN}" 
            }
        }
    }
}
