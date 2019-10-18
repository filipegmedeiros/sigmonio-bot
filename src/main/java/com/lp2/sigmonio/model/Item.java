package com.lp2.sigmonio.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull private String name;

    @NonNull private String description;

    @Transient
    @JsonDeserialize
    private int localization_id;

    @JsonDeserialize
    @Transient
    private int category_id;

    @ManyToOne
    @JoinColumn(name = "localization_id")
    private Localization localization;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}

