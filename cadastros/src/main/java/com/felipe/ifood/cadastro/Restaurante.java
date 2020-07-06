package com.felipe.ifood.cadastro;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "restaurante")
public class Restaurante extends PanacheEntityBase {

    @Id
    @Column(name = "id")
    public UUID id = UUID.randomUUID();

    @Column(name = "proprietario")
    public String proprietario;

    @Column(name = "cnpj")
    public String cnpj;

    @Column(name = "nome")
    public String nome;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "localizacao_id")
    public Localizacao localizacao;

    @CreationTimestamp
    public LocalDate dataCriacao;

    @UpdateTimestamp
    public LocalDate dataAtualizacao;
}
