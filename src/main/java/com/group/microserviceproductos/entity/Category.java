package com.group.microserviceproductos.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Esto genera un id auto-incrementable
    private Long id;

    private String name;


}
