package com.fulg.life.data;

import java.io.Serializable;

public class ItemData implements Serializable {
	Long pk;

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public boolean equals(Object obj) {
		if (obj instanceof ItemData) {
			ItemData item = (ItemData) obj;
			if (this.getPk().equals(item.getPk())) {
				return true;
			}
		}
		return false;
	}

}
