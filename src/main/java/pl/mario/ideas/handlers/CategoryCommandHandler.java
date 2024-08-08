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
                LOG.info("List of category");
                if (!command.getParam().isEmpty()) {
                    throw new IllegalArgumentException("category list doesn't support any additional params");
                }
                List<Category> categories = categoryDao.findAll();
                categories.forEach(System.out::println);
                break;
            case ADD:
                LOG.info("Add category");
                if (command.getParam().isEmpty())
                    throw new IllegalArgumentException("category name is required");
                String categoryName = String.join(" ", command.getParam());
                categoryDao.add(new Category(categoryName));
                break;
            case REMOVE:
                LOG.info("Remove category");
                if (command.getParam().isEmpty())
                    throw new IllegalArgumentException("wrong format. Check help for more information");
                String removeCategoryName = String.join(" ", command.getParam());
                categoryDao.remove(removeCategoryName);
                System.out.println("Category was removed: " + removeCategoryName);
                break;
            case UPDATE:
                LOG.info("Update category");
                if (command.getParam().size() < 2) {
                    throw new IllegalArgumentException("Both old and new category names are required");
                }

                String fullCommand = String.join(" ", command.getParam());
                String[] parts = splitCommand(fullCommand);

                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid format for category names");
                }

                String oldCategoryName = parts[0].trim();
                String newCategoryName = parts[1].trim();

                LOG.info("Old category name: " + oldCategoryName);
                LOG.info("New category name: " + newCategoryName);

                Category category = categoryDao.findOne(oldCategoryName)
                        .orElseThrow(() -> new IllegalArgumentException("Category not found: " + oldCategoryName));

                category.setName(newCategoryName);
                categoryDao.update(category, oldCategoryName);
                System.out.println("Category was updated: " + oldCategoryName + " to " + newCategoryName);
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown action: %s from command %s",
                        command.getAction(), command.getCommand()));
        }
    }

    private String[] splitCommand(String command) {
        command = command.trim().replaceAll("^\"|\"$", "");
        String[] parts = command.split("\" \"");
        if (parts.length == 1) {
            parts = command.split("\"");
        }
        return parts;
    }
}
