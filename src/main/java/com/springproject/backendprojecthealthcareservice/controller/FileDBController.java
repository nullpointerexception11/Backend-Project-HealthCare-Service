package com.springproject.backendprojecthealthcareservice.controller;

import com.springproject.backendprojecthealthcareservice.dto.FileDTO;
import com.springproject.backendprojecthealthcareservice.model.FileDB;
import com.springproject.backendprojecthealthcareservice.service.FileDBService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@CrossOrigin("http://localhost:8081")
@RequestMapping(path = "/files")
@RestController
public class FileDBController {

    private final FileDBService service;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('NURSE') or hasRole('SECRETARY')")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileDB fileDB = service.store(file);
            Map<String, String> map = new HashMap<>();
            map.put("imageId", fileDB.getId());
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("message", "Could not upload file: " + file.getOriginalFilename() + " !");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
        }
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('NURSE') or hasRole('SECRETARY')")
    public ResponseEntity<List<FileDTO>> getAllFiles() {

        List<FileDTO> files = service.getAllFiles().map(dbfile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbfile.getId())
                    .toUriString();

            return new FileDTO(dbfile.getName(), fileDownloadUri, dbfile.getType(),
                    dbfile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or " +
            "hasRole('DOCTOR') or hasRole('NURSE') or hasRole('SECRETARY')")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDB fileDB = service.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename="
                        + fileDB.getName() + "")
                .body(fileDB.getData());
    }

    @GetMapping("/display/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or " +
            "hasRole('DOCTOR') or hasRole('NURSE') or hasRole('SECRETARY')")
    public ResponseEntity<byte[]> displayImage(@PathVariable String id) {

        FileDB fileDB = service.getFile(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(fileDB.getData(), headers, HttpStatus.CREATED);
    }

}
