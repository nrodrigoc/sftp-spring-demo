package atmoz.stfp.demo.controller;

import atmoz.stfp.demo.sftp.service.SftpGateway;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/sftp")
@AllArgsConstructor
public class StfpController {

    private final SftpGateway sftpGateway;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile) {

        try {
            File file = sftpGateway.saveFileLocally(multipartFile);

            if(file.exists()) {
                sftpGateway.uploadToFTP(file);
                return ResponseEntity.ok("Arquivo " + multipartFile.getOriginalFilename() + " upado com sucesso!");
            }else {
                throw new Exception("Não foi possivel upar o arquivo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("O arquivo nao pode ser upado", HttpStatus.BAD_GATEWAY);
        }

    }


}
