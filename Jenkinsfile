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
    //echo "Artifact created"
              }
            }
    stage("Docker Build"){
        steps {
            sh 'docker version'
            //sh 'docker pull suvo7886/insurance-project:1.0"
            sh "docker build -t suvo7886/insurance-project:v2 ."
            sh 'docker image list'
            }
        }
       stage('Login to DockerHub') {
        steps {
           sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
           // sh 'winpty docker login'
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
            }
        }
        stage("Intialize Build Server"){
            steps {
                sh 'terraform -chdir=terraform init'
            }
        }
        stage("Plan Build Server"){
            steps {
                sh 'terraform -chdir=terraform plan'
            }
        }
        stage("Create Build Server"){
            steps {
                sh "terraform -chdir=terraform apply -auto-approve=true"
            }
        }
       // stage('configure test-server and deploy insure-me'){
       //     echo "configuring test-server"
          //  sh 'ansible-playbook configure-test-server.yml'
     //       ansiblePlaybook become: true, credentialsId: 'ssh-key-ansibles', disableHostKeyChecking: true, installation: 'ansible', inventory: '/etc/ansible/hosts', playbook: 'configure-test-server.yml'
   //     }
    //    stage('Approve - Deployment to Kubernetes Cluster'){
    //        steps{
    //            script {
    //               env.APPROVED_DEPLOY = input message: 'User input required Choose "Yes" | "Abort"'
    //            }
    //        }
   //     }
     //   stage('Deploy to Kubernetes Cluster') {
           // when {
             //   branch 'master'
            //  }
        //      steps {
             //   sh 'kubectl apply -f k8s_deploy.yml'
             // }
          //          steps {
       //       script {
       //       sshPublisher(publishers: [sshPublisherDesc(configName: 'Tomcat', sshCredentials: [encryptedPassphrase: '{AQAAABAAAAAQMehNiXEPfBOSBRiDhoCIsavgPk0knad+653Wr2hSzWQ=}', key: '', keyPath: '', username: 'admin'], transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'kubectl apply -f k8s_deploy.yml', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '.', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '*.yml')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
        //  kubernetesDeploy(configs: "k8s_deploy.yml", "service_deploy.yml")
      //    }
    //    }
    }
  }
