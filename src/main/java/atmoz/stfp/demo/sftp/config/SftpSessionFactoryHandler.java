package atmoz.stfp.demo.sftp.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

@Data
@Configuration
@ConfigurationProperties("sftp")
@ConditionalOnExpression("${sftp.active:true}")
public class SftpSessionFactoryHandler {

    @Value("${sftp.host}")
    private String host;

    @Value("${sftp.port}")
    private int port;

    @Value("${sftp.username}")
    private String username;

    @Value("${sftp.password}")
    private String password;

    @Value("${sftp.directory.localFolder}")
    private String localFolder;

    @Value("${sftp.directory.root}")
    private String remoteFolder;

    @Value("${sftp.save.dateformat}")
    private String dateFormat;

    public DefaultSftpSessionFactory gimmeFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setAllowUnknownKeys(true);
        factory.setUser(username);
        factory.setPassword(password);
        return factory;
    }

}
