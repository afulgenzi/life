package com.fulg.life.model.repository;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fulg.life.model.entities.Duty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/META-INF/applicationContext-model.xml")
@Transactional
public class ContextTest {
	private static final Logger LOG = LoggerFactory.getLogger(ContextTest.class);

	// @Autowired
	// ApplicationContext applicationContet;

	@Autowired
	DutyRepository dutyRepository;

	@Test
	public void testContext() {
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
		System.out.println("dutyRepository:" + dutyRepository);
	}

	@Test
	public void testCreateDuty() {
		{
			Duty duty = createDuty("Title1", "Description1", new Date());

			Duty readDuty = dutyRepository.findOne(duty.getPk());
			assertNotNull(readDuty);

		}
	}

	protected Duty createDuty(String title, String description, Date date) {
		Duty duty = new Duty();
		duty.setTitle(title);
		duty.setDescription(description);
		duty.setDate(date);

		duty = dutyRepository.save(duty);
		LOG.info("Created Duty Id: " + duty.getPk());

		return duty;
	}

}