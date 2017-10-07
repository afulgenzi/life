package com.fulg.life.facade.converter.reverse;

import com.fulg.life.data.ItemData;
import com.fulg.life.model.entities.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractReverseConverter<TARGET extends Item, SOURCE extends ItemData> {

	protected abstract TARGET createTarget(Long pk);

	public TARGET convert(SOURCE source) {
		return convert(source, createTarget(source.getPk()));
	}

	public TARGET convert(SOURCE source, TARGET target) {
		return populate(source, target);
	}

	public final List<TARGET> convertAll(List<SOURCE> list) {
		List<TARGET> retList = new ArrayList<TARGET>();

		for (SOURCE obj : list) {
			retList.add(this.convert(obj));
		}

		return retList;
	}

	protected abstract TARGET populate(SOURCE source, TARGET target);
}
