package com.fulg.life.model.entities;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "PK")
    Long pk;

//	@Version
//	@Column(name="UPDATE_TS")
//	private Calendar updateTs;
//
//	@Column(name="CREATION_TS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
//	private Calendar creationTs;

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

	@Override
	public int hashCode() {
		return pk.intValue();
	}

	@Override
	public boolean equals(Object paramObject) {
		if (paramObject instanceof Item) {
			return ((Item) paramObject).pk.equals(this.pk);
		} else {
			return super.equals(paramObject);
		}
	}
}
