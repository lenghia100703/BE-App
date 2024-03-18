package com.mobileapp.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exhibition_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExhibitionItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    private String description;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private UserEntity adminId;
}
