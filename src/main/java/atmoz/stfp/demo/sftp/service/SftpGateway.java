package atmoz.stfp.demo.sftp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface SftpGateway {

    void downloadFile(String filename);

    File saveFileLocally(MultipartFile file);

    void uploadToFTP(File file) throws IOException;

}
