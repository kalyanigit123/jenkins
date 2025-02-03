pipeline{
    agent any
    stages{
      stage('Build'){
        steps{
             sh ''' cd $WORKSPACE
             docker build -t spring:v${BUILD_NUMBER} .'''
      }
}
stage('docker upload'){
 steps{
    sh """
      docker tag spring:v${BUILD_NUMBER} ekalyani/reactjs:spring-v${BUILD_NUMBER}
      docker push ekalyani/reactjs:spring-v${BUILD_NUMBER}
      """
 }
}
stage('push ECR'){
    steps{
        sh '''
        aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 545684843058.dkr.ecr.ap-south-1.amazonaws.com

        docker tag simple-web-app:v${BUILD_NUMBER} 545684843058.dkr.ecr.ap-south-1.amazonaws.com/simple-web-app:V${BUILD_NUMBER}
        docker push 545684843058.dkr.ecr.ap-south-1.amazonaws.com/simple-web-app:v${BUILD_NUMBER}
        '''
    }
}
}

 }
