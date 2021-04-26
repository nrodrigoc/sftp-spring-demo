package atmoz.stfp.demo.utils;

import atmoz.stfp.demo.sftp.config.SftpSessionFactoryHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.annotation.PostConstruct;

@Configuration("LocalPaths")
@AllArgsConstructor
public class LocalPaths {

    private final SftpSessionFactoryHandler factoryHandler;

    private static String syncPath;

    public static String getBasePath() {
        return new FileSystemResource("").getFile().getAbsolutePath();
    }

    public static String getSyncPath() {
        return getBasePath() + getOSSeparator() + syncPath;
    }

    public static String getOSSeparator() {
        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win")) return "\\";

        return "/";
    }

    @PostConstruct
    private void setProperties() {
        syncPath = factoryHandler.getLocalFolder();
    }
}
