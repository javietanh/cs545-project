package edu.mum.domain.view;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SellerDto {
    private Long id;
    private String name;
    private String description;
    private String status;
    private LocalDate created;
    private String picture;
    private MultipartFile upload;
}
