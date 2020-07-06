package com.felipe.ifood.cadastro;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "localizacao")
public class Localizacao extends PanacheEntityBase {

    @Id
    @Column(name = "id")
    public UUID id = UUID.randomUUID();

    @Column(name = "latitude")
    public Double latitude;

    @Column(name = "longitude")
    public Double longitude;
}
