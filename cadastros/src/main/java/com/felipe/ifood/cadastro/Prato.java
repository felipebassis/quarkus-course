package com.felipe.ifood.cadastro;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "prato")
public class Prato extends PanacheEntityBase {

    @Id
    @Column(name = "id")
    public UUID id = UUID.randomUUID();

    @Column(name = "nome")
    public String nome;

    @Column(name = "descricao")
    public String descricao;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    public Restaurante restaurante;
}
