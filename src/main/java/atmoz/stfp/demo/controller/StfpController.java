package atmoz.stfp.demo.controller;

import atmoz.stfp.demo.sftp.service.SftpGateway;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

    @GetMapping
    public ResponseEntity<?> downloadFile(@RequestParam String filename) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Content-Disposition", "filename=" + filename);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        InputStreamResource resource = sftpGateway.downloadFile(filename);

        if (resource != null) {
            headers.setContentLength(resource.contentLength());
            return new ResponseEntity<>(sftpGateway.downloadFile(filename), headers, HttpStatus.OK);
        }

        return new ResponseEntity<>("O arquivo não foi encontrado", HttpStatus.BAD_GATEWAY);
    }


}
