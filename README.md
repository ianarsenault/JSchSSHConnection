# JSchSSHConnection

### Setup

This simple smoke-test assumes your remote server ssh is configured to accept host key type being used for auth (`ssh-rsa,ssh-dss,ecdsa-sha2-nistp256,ecdsa-sha2-nistp384,ecdsa-sha2-nistp521 `) and also accepts public key authentication `PubkeyAuthentication yes` 

1. If you are using IntelliJ import as maven project
2. Set ENV variables
    
    ```
    BASTION_HOST=mybastionhost.com
    BASTION_USER=bastion_user
    SSH_KEY=~/.ssh/<private_key>
   
    ```
3. Build and Run 