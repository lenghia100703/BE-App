package com.mobileapp.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "news")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    private String image;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private UserEntity adminId;
}
