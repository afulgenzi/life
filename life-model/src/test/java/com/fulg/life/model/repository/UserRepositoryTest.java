package com.fulg.life.model.repository;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.fulg.life.model.entities.User;

@Transactional
public class UserRepositoryTest extends AbstractRepositoryIntegrationTest {
	private static Logger LOG = LoggerFactory.getLogger(UserRepositoryTest.class);

	@Before
	public void cleanUp() {
		genericItemRepository.deleteAll();
	}

	@Test
	public void testFindAll() {
		{
			createUser();

			List<User> itemList = userRepository.findAll();

			assertNotNull(itemList);
			// assertEquals(1, itemList.size());

			LOG.info("Users found: " + itemList.size());
			for (User user : itemList) {
				LOG.info("firstName:" + user.getFirstName() + ", lastName:" + user.getLastName() + ", email:"
						+ user.getEmail());
			}
		}
	}

}
