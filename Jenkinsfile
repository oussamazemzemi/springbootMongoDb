def SONAR_HOST_URL="http://localhost:9000/"
def SONAR_PROJECT_NAME="studentinformationsystem" 
def SONAR_TOKEN="cea794b1758519e55bbd860b51fed9a8203b0223"
def dockerFile_api="Dockerfile"
def image_api_name="${SONAR_PROJECT_NAME}"
def image_api_path="${image_api_name}-snapshot:latest"
def dockerContext="."

pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Setup parameters') {
            steps {
                script { 
                    properties([
                        parameters([
                            choice(
                                choices: ['-SNAPSHOT', 'RELEASE'], 
                                name: 'AS'
                            ),
                            booleanParam(
                                defaultValue: false, 
                                description: 'Ignore Failed Junit Tests', 
                                name: 'BOOLEAN'
                            ),
                            text(
                                defaultValue: '''
                                this is a multi-line 
                                string parameter example
                                ''', 
                                 name: 'MULTI-LINE-STRING'
                            ),
                            string(
                                defaultValue: 'scriptcrunch', 
                                name: 'STRING-PARAMETER', 
                                trim: true
                            )
                        ])
                    ])
                }
            }
        }
        
        stage('Build') {
            steps {
                //prepare choices
                script{
                    echo "aze=${AS}"
    				if (AS.equals("RELEASE")){
    					image_api_path="${image_api_name}:latest"
    				}
                }
			
                // Get some code from a GitHub repository
                git 'https://github.com/oussamazemzemi/springbootMongoDb.git'

                // (bat: Windows Batch Script or sh: Shell Script) =>  Run Maven on a Unix agent.
                //to ignore TEST add: -Dmaven.test.failure.ignore=true
                bat "mvn clean package -Dmaven.test.failure.ignore=${BOOLEAN}" 

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
        
        stage('Nexus deploy') {
            steps {
                dir("${env.WORKSPACE}") {
                    configFileProvider([configFile(fileId: '76967159-6fc9-4248-ba6f-fc052da7f8d3', variable: 'MAVEN_GLOBAL_SETTINGS')]) {
                        bat "mvn -B -s ${MAVEN_GLOBAL_SETTINGS} clean deploy -DskipTests -Dmaven.install.skip=true -X" 
                    }
                }
            }
        }
        
        stage('Docker build') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    dir("${env.WORKSPACE}") {
                        configFileProvider([configFile(fileId: '76967159-6fc9-4248-ba6f-fc052da7f8d3', variable: 'MAVEN_GLOBAL_SETTINGS')]) {
                            bat "docker build --force-rm -f ${dockerFile_api} --tag ${env.dockerHubUser}/${image_api_path} ${dockerContext}" 
                        }
                    }
                }
            }
        }
        
        stage('Docker Push') {
          agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    bat "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    bat "docker push ${env.dockerHubUser}/${image_api_path}"
                }
            }
        }
        
    }
}
