package atmoz.stfp.demo.sftp.service.impl;

import atmoz.stfp.demo.sftp.config.MkDirGateway;
import atmoz.stfp.demo.sftp.config.SftpSessionFactoryHandler;
import atmoz.stfp.demo.sftp.service.SftpGateway;
import atmoz.stfp.demo.utils.LocalPaths;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

@Component
@AllArgsConstructor
@Slf4j
public class StfpGatewayImpl implements SftpGateway {

    private final SftpSessionFactoryHandler factoryHandler;

    private final MkDirGateway mkDirGateway;

    @Override
    public void downloadFile(String filename) {

//        Session session = factoryHandler.gimmeFactory().getSession();
//
//        InputStream data;
//
//        try {
//            mkDirGateway.createDirectories();
//        }



    }

    @Override
    public File saveFileLocally(MultipartFile filePart) {

        String localPath =  LocalPaths.getSyncPath() + LocalPaths.getOSSeparator() + filePart.getOriginalFilename();

        try {
            mkDirGateway.createDirectories();

            filePart.transferTo(Paths.get(localPath));
            return new File(localPath);
        } catch (IOException e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    @Async
    public void uploadToFTP(File file) {

        if (!file.exists()){
            log.info("File {} doesn't exist.", file.getAbsolutePath());
            return;
        }

        try {

            Session session = factoryHandler.gimmeFactory().getSession();

            mkDirGateway.createDirectories();

            String filename = file.getName();

            String destination = factoryHandler.getRemoteFolder() + "/" + factoryHandler.getLocalFolder() + "/" + filename;

            InputStream inputStream = new FileInputStream(file);

            session.write(inputStream, destination);

            session.close();

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }



}
