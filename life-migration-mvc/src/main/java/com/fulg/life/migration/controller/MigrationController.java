package com.fulg.life.migration.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fulg.life.migration.dto.MigrationPackage;
import com.fulg.life.migration.exception.BusinessException;
import com.fulg.life.migration.facade.MigrationFacade;
import com.fulg.life.model.entities.GenericItem;
import com.fulg.life.model.entities.MigrationItem;
import com.fulg.life.model.repository.GenericItemRepository;
import com.fulg.life.model.repository.MigrationItemRepository;

@Controller
public class MigrationController extends AbstractBaseController {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory
			.getLogger(MigrationController.class);

	private static final String ITEM_TYPES = "itemTypes";

	@Resource(name = "migrationFacade")
	MigrationFacade migrationFacade;

	@Autowired
	GenericItemRepository genericItemRepository;

	@Autowired
	MigrationItemRepository migrationItemRepository;

	@RequestMapping(value = "/")
	public ModelAndView home(HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView("home");

		this.addDutiesToModel(model);

		return model;
	}

	@RequestMapping(value = "/migrate/execute", produces = "application/json")
	@ResponseBody
	public String migrate(HttpServletResponse response,
			@RequestParam("packageName") String packageName) throws IOException {
		MigrationPackage migrationPackage = new MigrationPackage();
		migrationPackage.setPackageName(packageName);

		migrationFacade.migrate(migrationPackage, true);

		return "Just migrated package: " + migrationPackage.getPackageName()
				+ " (" + migrationPackage.getMigrationItems().size()
				+ " items)";
	}

	private void addDutiesToModel(ModelAndView model) {
		List<GenericItem> items = genericItemRepository.findAll();

		Map<String, List<GenericItem>> itemMap = splitResultIntoMap(items);
		for (String itemType : itemMap.keySet()) {
			model.addObject(itemType, itemMap.get(itemType));
		}
		model.addObject(ITEM_TYPES, itemMap.keySet());
	}

	private Map<String, List<GenericItem>> splitResultIntoMap(
			List<GenericItem> items) {
		Map<String, List<GenericItem>> itemMap = new HashMap<String, List<GenericItem>>();
		if (!CollectionUtils.isEmpty(items)) {
			for (GenericItem item : items) {
				String itemClass = item.getClass().getSimpleName();
				if (itemMap.get(itemClass) == null) {
					itemMap.put(itemClass, new ArrayList<GenericItem>());
				}
				itemMap.get(itemClass).add(item);
			}
		}
		return itemMap;
	}

	@RequestMapping(value = "/migrate/showAvailableMigrations")
	public ModelAndView showAvailableMigrations(HttpServletResponse response)
			throws IOException {
		ModelAndView model = new ModelAndView("availableMigrations");

		model.addObject("availableMigrations", getAvailableMigrations());

		return model;
	}

	private List<MigrationPackage> getAvailableMigrations() {
		List<MigrationPackage> returnMigrationPackages = new ArrayList<MigrationPackage>();

		MigrationPackage migrationPackage = null;

		for (String packageName : migrationFacade.getMigrationPackages()) {
			migrationPackage = new MigrationPackage();
			migrationPackage.setPackageName(packageName);
			returnMigrationPackages.add(migrationPackage);
		}

		return returnMigrationPackages;
	}

	@RequestMapping(value = "/migrate/showPackage", method = RequestMethod.GET)
	public ModelAndView showTable(HttpServletResponse response,
			@RequestParam("packageName") String packageName)
			throws IOException, BusinessException {
		ModelAndView model = new ModelAndView("availableMigrations");

		model.addObject("availableMigrations", getAvailableMigrations());
		MigrationPackage migrationPackage = new MigrationPackage();
		migrationPackage.setPackageName(packageName);
		migrationPackage.getMigrationItems().addAll(
				migrationItemRepository.findBySource(packageName));
		this.calculateLastMigration(migrationPackage);
		model.addObject("selectedTable", migrationPackage);

		return model;
	}

	private void calculateLastMigration(MigrationPackage migrationPackage) {
		if (migrationPackage.getMigrationItems().size() > 0) {
			Date lastFound = null;
			for (MigrationItem item : migrationPackage.getMigrationItems()) {
				if (lastFound == null
						|| item.getMigrationDate().after(lastFound)) {
					lastFound = item.getMigrationDate();
				}
			}
		}
	}
}
