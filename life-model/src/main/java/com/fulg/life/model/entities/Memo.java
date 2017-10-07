package com.fulg.life.model.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "memos")
public class Memo extends Item {

    @OneToOne
    GenericItem item;
}
