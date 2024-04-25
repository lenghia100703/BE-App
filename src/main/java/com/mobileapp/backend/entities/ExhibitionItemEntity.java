package com.mobileapp.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "exhibition_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExhibitionItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    private String description;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity adminId;
}
