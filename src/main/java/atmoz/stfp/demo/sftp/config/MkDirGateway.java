package atmoz.stfp.demo.sftp.config;

import atmoz.stfp.demo.utils.LocalPaths;
import lombok.AllArgsConstructor;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@AllArgsConstructor
public class MkDirGateway {

    private final SftpSessionFactoryHandler factoryHandler;

    public void createDirectories() {

        Session session = factoryHandler.gimmeFactory().getSession();

        String pathToUploadedFiles = factoryHandler.getRemoteFolder();

        try {

            if(!session.exists(factoryHandler.getRemoteFolder()))
                session.mkdir(factoryHandler.getRemoteFolder());

            if (!session.exists(pathToUploadedFiles))
                session.mkdir(pathToUploadedFiles);

            File localDirectory = new File(factoryHandler.getLocalFolder());
            if(localDirectory.mkdir())
                System.out.println("O diretorio files foi criado localmente");

        }catch (IOException e) {
            e.printStackTrace();
            session.close();
        }


    }

}
