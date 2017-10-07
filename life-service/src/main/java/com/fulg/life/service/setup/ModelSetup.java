package com.fulg.life.service.setup;

import com.fulg.life.model.entities.Bank;
import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.Category;
import com.fulg.life.model.entities.FrequencyType;
import com.fulg.life.service.BankAccountService;
import com.fulg.life.service.BankService;
import com.fulg.life.service.CategoryService;
import com.fulg.life.service.FrequencyTypeService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

/**
 * Created by alessandro.fulgenzi on 12/02/16.
 */
@Component
public class ModelSetup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(ModelSetup.class);

    private static final String BANK_NAME_PROPERTY = "account.bank.";

    private static final String CATEGORY_HOME = "home";
    private static final String CATEGORY_ATTRIBUTE_DELIMITER = "#";
    private static final String CATEGORIES = "home#Home\n" +
            "\trental#Rental\n" +
            "\tutilities#Utilities\n" +
            "\t\tgas#Gas\n" +
            "\t\telectricity#Electricity\n" +
            "\t\ttel-internet-tv#Telephone, Internet, TV\n" +
            "\tyellow-storage#Yellow Storage\n" +
            "monthly-expenses#Monthly Expenses\n" +
            "\tale#Ale\n" +
            "\t\tale-oyster#Oyster\n" +
            "\t\tale-bank#Bank\n" +
            "\t\tale-Mobile#Mobile\n" +
            "\t\tale-Spare#Spare\n" +
            "\tsimo#Simo\n" +
            "\t\tsimo-oyster#Oyster\n" +
            "\t\tsimo-bank#Bank\n" +
            "\t\tsimo-Mobile#Mobile\n" +
            "\t\tsimo-Spare#Spare\n" +
            "\tfamily#Family\n" +
            "\t\tgroceries#Groceries\n" +
            "\t\trestaurant#Restaurant\n" +
            "\t\tfamili-spare#Spare\n" +
            "holiday#Holiday\n" +
            "\ttorre-di-maremma#Torre di Maremma\n" +
            "\tholiday-roma#Roma\n" +
            "tax#Tax\n" +
            "\tflastech-vat#Flastech VAT\n" +
            "\tflastech-tax-return#Flastech Tax Return\n" +
            "\tale-tax-return#Tax Return Ale\n" +
            "\tsimo-tax-return#Tax Return Simona\n";

    @Resource
    BankAccountService bankAccountService;
    @Resource
    CategoryService categoryService;
    @Resource
    BankService bankService;
    @Resource
    FrequencyTypeService frequencyTypeService;
    @Resource(name = "lifeServiceProperties")
    private Properties properties;

    @Resource
    String barclaysBank;
    @Resource
    String lloydsBank;
    @Resource
    String intesaBank;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        LOG.info("onApplicationEvent: " + event.toString());

        createBanks();

//        createCategories();

        createFrequencies();
    }

    @Transactional
    public void createBanks()
    {

        // Barclays
        {
            Bank barclays = getOrCreateBank(barclaysBank);
            checkAccount(bankAccountService.getBarclays1(), barclays);
            checkAccount(bankAccountService.getBarclays2(), barclays);
        }

        // Lloyds
        {
            Bank lloyds = getOrCreateBank(lloydsBank);
            checkAccount(bankAccountService.getLloyds1(), lloyds);
            checkAccount(bankAccountService.getLloyds2(), lloyds);
        }

        // Intesa
        {
            Bank intesa = getOrCreateBank(intesaBank);
            checkAccount(bankAccountService.getIntesa1(), intesa);
            checkAccount(bankAccountService.getIntesa2(), intesa);
        }
    }

    public void createFrequencies()
    {
        getOrCreateFrequency("d", "days", "d", "daily", "Daily Frequency");
        getOrCreateFrequency("w", "weeks", "w", "weekly", "Weekly Frequency");
        getOrCreateFrequency("m", "months", "m", "monthly", "Monthly Frequency");
        getOrCreateFrequency("y", "years", "y", "yearly", "Yearly Frequency");
    }

    //    @Transactional
    public void createCategories()
    {
        final List<Category> fullCategoryHierarchy = parseAndSaveCategories(CATEGORIES);

        printCategories(fullCategoryHierarchy, 0);
    }

    private List<Category> parseAndSaveCategories(final String categoryText)
    {
        final List<Category> categories = Lists.newArrayList();

        List<String> lines = splitCategoriesByLine(categoryText);
        if (CollectionUtils.isNotEmpty(lines))
        {
            Category previousCategory = null;
            int previousLevel = -1;
            for (final String line : lines)
            {
                final int level = getCategoryLevel(line);
                final Category cat = buildCategoryFromLine(line);
//                LOG.info("Cat [{}], level [{}]", cat.getCode(), level);
                Category parentCategory = previousCategory;
                while (level <= previousLevel)
                {
                    parentCategory = parentCategory.getSupercategory();
                    previousLevel -= 1;
                }
                if (parentCategory == null)
                {
                    LOG.info("Adding root category [{} -> {}]", cat.getCode(), cat.getTitle());
                    categories.add(cat);
                } else
                {
                    if (cat.getSupercategory() == null)
                    {
                        LOG.info("Adding new category [{}] under [{}]", cat.getCode(), parentCategory.getCode());

                        cat.setSupercategory(parentCategory);
                        categoryService.save(cat);
                    } else if (!cat.getSupercategory().getCode().equals(parentCategory.getCode()))
                    {
                        LOG.info("Moving existing Category [{}] from [{}] to [{}]", cat.getCode(), cat.getSupercategory().getCode(), parentCategory.getCode());

                        cat.setSupercategory(parentCategory);
                        categoryService.save(cat);
                    }
                }
                previousCategory = cat;
                previousLevel = level;
            }
        }

        return categories;
    }

    private List<String> splitCategoriesByLine(final String categoryText)
    {
        final String[] array = categoryText.split("\n");
        return Lists.newArrayList(array);
    }

    private Category buildCategoryFromLine(final String line)
    {
        final String[] array = line.split(CATEGORY_ATTRIBUTE_DELIMITER);
        if (array == null || array.length != 2)
        {
            throw new RuntimeException("Found category with more than 2 attributes [" + line + "]");
        } else
        {
            return getOrCreateCategory(removeExtraChars(array[0]), removeExtraChars(array[1]));
        }
    }

    private String removeExtraChars(String str)
    {
        while (str.startsWith("\t"))
        {
            str = str.substring(1);
        }
        while (str.endsWith("\n"))
        {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }

    private int getCategoryLevel(String categoryText)
    {
        int level = 0;
        if (StringUtils.isNotBlank(categoryText))
        {
            while (categoryText.startsWith("\t"))
            {
                level++;
                categoryText = categoryText.substring(1);
            }
        }
        return level;
    }

    private Category getOrCreateCategory(final String code, final String title)
    {
        final Category existingCategory = categoryService.getCategoryByCode(code);
        if (existingCategory == null)
        {
            LOG.info("Creating Category for code [{}]", code);
            final Category cat = new Category();
            cat.setCode(removeExtraChars(code));
            cat.setTitle(removeExtraChars(title));
            categoryService.save(cat);
            return cat;
        } else
        {
            LOG.info("Found existing Category [{}], subCategories [{}]", code, existingCategory.getSubcategories().size());
            return existingCategory;
        }
    }

    private void printCategories(final List<Category> fullCategoryHierarchy, final int level)
    {
        if (CollectionUtils.isNotEmpty(fullCategoryHierarchy))
        {
            for (final Category cat : fullCategoryHierarchy)
            {
                String output = "";
                for (int ind = 0; ind < level; ind++)
                {
                    output += "\t";
                }
                output += cat.getCode() + " --> " + cat.getTitle();
                LOG.info(output);
                printCategories(cat.getSubcategories(), level + 1);
            }
        }
    }

    private Bank getOrCreateBank(final String bankCode)
    {
        Bank bank = bankService.getByCode(bankCode);
        if (bank == null)
        {
            LOG.info("Creating bank [{}]", bankCode);
            bank = createBank(bankCode);
        }
        return bank;
    }

    private Bank createBank(final String bankCode)
    {
        Bank bank = new Bank();
        bank.setCode(bankCode);
        bank.setName(getBankName(bankCode));
        bankService.insert(bank);
        return bank;
    }

    private FrequencyType getOrCreateFrequency(final String code, final String intervalUnit, final String intervalUnitInternal, final String title, final String description)
    {
        final FrequencyType freq = frequencyTypeService.getByCode(code);
        if (freq == null)
        {
            LOG.info("Creating FrequencyType [{}]", code);
            return createFrequency(code, intervalUnit, intervalUnitInternal, title, description);
        } else
        {
            LOG.info("Updating FrequencyType [{}]", code);
            freq.setCode(code);
            freq.setIntervalUnit(intervalUnit);
            freq.setIntervalUnitInternal(intervalUnitInternal);
            freq.setTitle(title);
            freq.setDescription(description);
            frequencyTypeService.update(freq);
            return freq;
        }
    }

    private FrequencyType createFrequency(final String code, final String intervalUnit, final String intervalUnitInternal, final String title, final String description)
    {
        FrequencyType item = new FrequencyType();
        item.setCode(code);
        item.setIntervalUnit(intervalUnit);
        item.setIntervalUnitInternal(intervalUnitInternal);
        item.setTitle(title);
        item.setDescription(description);
        frequencyTypeService.insert(item);
        return item;
    }

    private void checkAccount(final BankAccount account, final Bank bank)
    {
        if (account != null && account.getBank() == null)
        {
            LOG.info("Assigning bank {} to account {}({})", bank.getCode(), account.getAccountNumber(), account.getBankName());
            account.setBank(bank);
            bankAccountService.update(account);
        }
    }

    private String getBankName(final String bankCode)
    {
        final String propertyName = BANK_NAME_PROPERTY + bankCode;
        return properties.getProperty(propertyName);
    }
}

