package edu.mum.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    private String messageContent;
    private String messageStatus;
    private LocalDateTime messageDate = LocalDateTime.now();
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;
}
