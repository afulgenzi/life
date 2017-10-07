package com.fulg.life.migration.facade;

import com.fulg.life.migration.dto.LegacyTable;

public interface LegacyFacade {
    LegacyTable getTableInfo(String tableName, boolean loadContent);
}
