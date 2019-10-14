package com.lp2.sigmonio.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "localizations")
public class Localization extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    @NonNull private String title;

    @Column(name = "description", nullable = false)
    @NonNull private String description;

}