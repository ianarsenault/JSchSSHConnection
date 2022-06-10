import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class JSchSSHConnection {
    public static void main(String[] args) {
        String host = System.getenv("BASTION_HOST");
        String user = System.getenv("BASTION_USER");
        String privateKey = System.getenv("SSH_KEY");
        try{

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            jsch.addIdentity(privateKey);
            System.out.println("identity added ");

            Session session=jsch.getSession(user, host, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("session created.");


            session.connect();
            System.out.println("session connected.....");

            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand("pwd");
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);

            InputStream in=channel.getInputStream();
            channel.connect();
            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    System.out.print(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
