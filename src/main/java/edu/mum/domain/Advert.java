package edu.mum.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String image;
    @Transient
    @JsonIgnore
    private MultipartFile imageUpload;
    private String url;
    private Integer count;
    private LocalDateTime creationDateTime;
    private LocalDateTime expirationDateTime;
}
