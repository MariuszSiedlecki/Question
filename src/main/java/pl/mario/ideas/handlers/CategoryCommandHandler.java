package pl.mario.ideas.handlers;

import pl.mario.ideas.dao.CategoryDao;
import pl.mario.ideas.input.UserInputCommand;
import pl.mario.ideas.model.Category;

import java.util.List;
import java.util.logging.Logger;

public class CategoryCommandHandler extends BaseCommandHandler {
    private static Logger LOG = Logger.getLogger(CategoryCommandHandler.class.getName());

    private static final String COMMAND_NAME = "category";
    private CategoryDao categoryDao;


    public CategoryCommandHandler() {
        categoryDao = new CategoryDao();
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void handle(UserInputCommand command) {
        if (command.getAction() == null) {
            throw new IllegalArgumentException("Action can't be null");
        }
        switch (command.getAction()) {
            case LIST:
                LOG.info("list of category");
                if (!command.getParam().isEmpty()) {
                    throw new IllegalArgumentException("category list doesn't support any additional params");
                }
                List<Category> categories = categoryDao.findAll();
                categories.forEach(System.out::println);
                break;
            case ADD:
                LOG.info("Add category");
                if (command.getParam().size() != 1)
                    throw new IllegalArgumentException("wrong format. Check help for mor information");
                String categoryName = command.getParam().get(0);
                categoryDao.add(new Category(categoryName));
                break;
            case REMOVE:
                LOG.info("Remove category");
                if (command.getParam().size() != 1)
                    throw new IllegalArgumentException("Wrong format. Check help for more information");
                String removeCategoryName = command.getParam().get(0);
                System.out.println("Category was removed: " + removeCategoryName);
                categoryDao.remove(removeCategoryName);
                break;
            case UPDATE:
                LOG.info("Update category");
                if (command.getParam().size() != 2)
                    throw new IllegalArgumentException("Wrong format. Check help for more information");

                String oldCategoryName = command.getParam().get(0);
                String newCategoryName = command.getParam().get(1);
                LOG.info("Old category name: " + oldCategoryName);
                LOG.info("New category name: " + newCategoryName);

                Category category = categoryDao.findOne(oldCategoryName)
                        .orElseThrow(() -> new IllegalArgumentException("Category not found:" + oldCategoryName));

                category.setName(newCategoryName);
                categoryDao.update(category, oldCategoryName);
                System.out.println("Category was update:" + oldCategoryName + " to " + newCategoryName);
                break;
            default: {
                throw new IllegalArgumentException(String.format("Unknown action: %s from command %s",
                        command.getAction(), command.getCommand()));
            }
        }
    }
}
