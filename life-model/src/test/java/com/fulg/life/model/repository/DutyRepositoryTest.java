package com.fulg.life.model.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.fulg.life.model.entities.Duty;
import com.fulg.life.model.entities.GenericItem;
import com.fulg.life.model.entities.Item;

@Transactional
public class DutyRepositoryTest extends AbstractRepositoryIntegrationTest {
	private static Logger LOG = LoggerFactory.getLogger(DutyRepositoryTest.class);

	@Autowired
	@Qualifier("lifeModelProperties")
	Properties props;

	@Before
	public void cleanUp() {
		genericItemRepository.deleteAll();
	}

	@Test
	public void testCreateDuty() {
		{
			Duty duty = createDuty("Title1", "Description1", new Date());

			Duty readDuty = dutyRepository.findOne(duty.getPk());
			assertNotNull(readDuty);
		}
	}

	@Test
	public void testFindByMonth() {
		{
			Date now = new Date();
			Duty duty = createDuty("Title1", "Description1", now);
			assertNotNull(duty);

			Calendar cal = new GregorianCalendar();
			cal.setTime(now);
			int currentMonth_1_12 = cal.get(Calendar.MONTH) + 1;
			List<Duty> readItem = dutyRepository.findByMonth(currentMonth_1_12, 2013);
			assertNotNull(readItem);
		}
	}

	@Test
	public void testDutyHierarchy() {
		{
			// Add 1 Duty
			Duty duty = createDuty("Duty.Title1", "Duty.Description1", new Date());
			assertNotNull(duty);

			// Add 2 GenericItems
			GenericItem item1 = createGenericItem("GenericItem.Title1", "GenericItem.Description1");
			assertNotNull(item1);
			GenericItem item2 = createGenericItem("GenericItem.Title2", "GenericItem.Description2");
			assertNotNull(item2);

			// Check for all GenericItems (3)
			List<GenericItem> genericItemList = genericItemRepository.findAll();
			assertNotNull(genericItemList);
			assertEquals(3, genericItemList.size());
			for (Item entity : genericItemList) {
				LOG.info("1 - " + entity.getClass().getSimpleName() + ", Id:" + entity.getPk());
			}

			// Check for all Duties (1)
			List<Duty> dutyList = dutyRepository.findAll();
			assertNotNull(dutyList);
			assertEquals(1, dutyList.size());
			for (Item entity : dutyList) {
				LOG.info("2 - " + entity.getClass().getSimpleName() + ", Id:" + entity.getPk());
			}
		}
	}

}
