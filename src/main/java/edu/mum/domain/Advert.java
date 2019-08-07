package edu.mum.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Advert {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private String image;
    private String url;
    private Integer count;
}
