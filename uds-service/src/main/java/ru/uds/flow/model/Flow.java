package ru.uds.flow.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "flows")
public class Flow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
}
