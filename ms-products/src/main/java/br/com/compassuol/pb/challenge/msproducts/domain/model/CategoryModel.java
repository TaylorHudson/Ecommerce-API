package br.com.compassuol.pb.challenge.msproducts.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_category")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class CategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;



}
