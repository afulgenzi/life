package com.fulg.life.msaccess.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fulg.life.msaccess.test.AbstractTest;

public class AllDaoTest extends AbstractTest {
    private static final Logger LOG = LoggerFactory.getLogger(AllDaoTest.class);

    @Autowired
    AccessValuteDao valuteDao;
    @Autowired
    AccessEntrateUsciteDao entrateUsciteDao;

    @Test
    public void testGetAll() {
        assertNotNull(valuteDao);

        assertNotNull(entrateUsciteDao);
    }

}
