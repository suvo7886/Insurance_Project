pipeline {
    agent { 
    label 'slave' 
    }
    tools{
        maven 'M2_HOME'
    }
    environment {   
        DOCKERHUB_CREDENTIALS=credentials('dockerhub')
    } 
stages{
    stage('Checkout from Github') {
        steps{
         script{
             checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'Github-Login1', url: 'https://github.com/suvo7886/Insurance_Project.git']])
         }
           }
       }
    stage('Package with Maven') {
        steps{
        sh "mvn -Dmaven.test.failure.ignore=true clean package"
    	echo "Building done"
              }
            }
    stage("Build with Docker "){
        steps {
            sh 'docker version'
            sh "docker build -t suvo7886/insurance-project:v2 ."
            sh 'docker image list'
	echo "Image created"
            }
        }
       stage('Login to DockerHub') {
        steps {
           sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            }
        }
       stage('Approve for Push Image to Dockerhub'){
        steps{
            script {
                env.APPROVED_DEPLOY = input message: 'User input required Choose "Yes" | "Abort"'
                }
            }
        }
        stage('Push to DockerHub') {
            steps {
           sh "docker push suvo7886/insurance-project:v2"
	   echo "Push done"
            }
        }
        stage("Provisioning Server Using Terraform"){
            steps {
	     sh 'terraform -chdir=terraform init'
             sh 'terraform -chdir=terraform plan'
             sh "terraform -chdir=terraform apply -auto-approve -input=false"
	     echo "Server create done"
            }
        }
      stage("Configure Server Using Ansible"){
	steps {
          echo "Configuration done"
          sh 'ansible-playbook configure-test-server.yml'
          ansiblePlaybook become: true, credentialsId: 'Ansible_Server', disableHostKeyChecking: true, installation: 'ansible', inventory: '/etc/ansible/hosts', playbook: 'configure-test-server.yml'
      	}
      }
        stage('Approve for Deployment to Kubernetes Cluster'){
           steps{
              script {
                env.APPROVED_DEPLOY = input message: 'User input required Choose "Yes" | "Abort"'
              }
         }
    }
    stage('Deploy to Kubernetes Cluster') {
            steps {
		script {
			sshPublisher(publishers: [sshPublisherDesc(configName: 'K8S_Server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'kubectl apply -f deployment.yml', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '.', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '*.yml')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])	
			}
		}
			  post {
				success {
				  sh "echo 'Send mail on success'"
				  mail bcc: '', body: "Deployment to Kubernetes Cluster is successful", cc: 'suvo7886@gmail.com', from: '', replyTo: '', subject: "Job ${JOB_NAME} (${BUILD_NUMBER})", to: 'suvo7886@gmail.com'
				}
				failure {
				  sh "echo 'Send mail on failure'"
				  mail bcc: '', body: "Deployment to Kubernetes Cluster Failed", cc: 'suvo7886@gmail.com', from: '', replyTo: '', subject: "Deployment Status", to: 'suvo7886@gmail.com'
				}
			  }	
	         }
            }
      }
