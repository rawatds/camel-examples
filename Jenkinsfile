pipeline {
	agent any
	
    // options { timestamps () }
    
    parameters {
        string(name: 'developer', defaultValue: 'DSR', description: 'The developer')
        choice(name: 'vers', choices:['v1.0.1', 'v1.0.2', 'v2.0.0'] , description: 'Select the version')
        booleanParam(name: 'executeTests', defaultValue: true, description: 'Whether to exeucte tests?')
        
    }
    
    environment {
        SERVER_CRED = credentials('server-cred')
        //U_NAME = "${SERVER_CRED['USERNAME']}"
        // USERNAME=''
        // PASSWD=''

    }
    
    

	stages {
	
		stage("Checkout") {
			steps{
				git url:"https://github.com/rawatds/camel-examples.git", branch: "main"
			}
		
		}
		
		stage("Build") {
		
			steps{
			    echo "BUILD"
			    echo "The params are: ${params.developer}. ${params.vers}, ${params.executeTests}"
			    echo "*** The server credentials are ${SERVER_CRED_USR} and  ${SERVER_CRED_PSW}***"
				sh 'mvn clean package'
			
			}
		}
		
		stage("test") {
		    when {
		        expression {
		            params.executeTests == true
		        }
		        
		    }
		
			steps{
			    echo "TEST"
			    echo "*** The server credentials are ${SERVER_CRED_USR} and  ${SERVER_CRED_PSW}***"
				sh 'mvn clean package'
			
			}
		}
		
		stage("Sonarqube Analysis") {
		
			steps{
			    
			    withCredentials([
			        usernamePassword(credentialsId: 'server-cred', usernameVariable: 'USERNAME', passwordVariable: 'PASSWD')    
			        ]) {
			        echo "## User: ${USERNAME} and Pass: ${PASSWD}"    
			    }
			    
          withSonarQubeEnv('SonarQubeServerLocal') {
            sh 'mvn sonar:sonar'
          }
			}
		}
	}
}
