package tech.gomes.reading.management.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class UploadService {

    @Value("${file.upload.dir}")
    private String dir;

    private final Path fileStorageLocation;

    public UploadService(String dir) {
        fileStorageLocation = Paths.get(dir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível criar o diretório para salvar as imagens");
        }
    }

    public String uploadCoverImg(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (originalFileName.contains("..")) {
                throw new RuntimeException("Desculpe! O nome do arquivo contém uma sequência de caminho inválida " + originalFileName);
            }

            String fileExtension;
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch (Exception e) {
                fileExtension = "";
            }
            String generatedFileName = UUID.randomUUID() + fileExtension;

            Path targetLocation = this.fileStorageLocation.resolve(generatedFileName);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }

            return generatedFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Não foi possível armazenar o arquivo.", ex);
        }
    }

    public void deleteFile(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            log.warn("Não é possível excluir um arquivo vazio ou nulo.");
            return;
        }

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            Files.deleteIfExists(targetLocation);

            log.info("Arquivo {} foi excluído com sucesso.", fileName);
        } catch (IOException ex) {
            log.error("Não foi possível excluir o arquivo {}. Erro: {}", fileName, ex.getMessage());
            throw new RuntimeException("Não foi possível excluir o arquivo.", ex);
        }
    }
}
