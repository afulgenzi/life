package com.fulg.life.model.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "banks")
public class Bank extends Item {

    @Column(name = "CODE")
    String code;

    @Column(name = "NAME")
    String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
