package com.springproject.backendprojecthealthcareservice.service;

import com.springproject.backendprojecthealthcareservice.model.FileDB;
import com.springproject.backendprojecthealthcareservice.repository.FileDBRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class FileDBService {

    private final FileDBRepository repository;

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

        repository.save(fileDB);
        return fileDB;
    }

    public FileDB getFile(String id) {
        return repository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return repository.findAll().stream();
    }
}
