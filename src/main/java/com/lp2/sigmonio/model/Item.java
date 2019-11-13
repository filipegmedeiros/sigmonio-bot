package com.lp2.sigmonio.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "localization_id")
    private Localization localization;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}

