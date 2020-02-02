package com.javaguru.shoppinglist.console;

import com.javaguru.shoppinglist.product.Product;

public class UIFactory {
    private static UIFactory ourInstance;

    private final Menu mainMenu;
    private final Menu editMenu;

    private EditMenuService editMenuService;

    private UIFactory() {
        mainMenu = createMainMenu();
        editMenu = createEditMenu();
    }

    public static UIFactory getInstance() {
        if (ourInstance == null) {
            ourInstance = new UIFactory();
        }
        return ourInstance;
    }

    public Menu getMainMenu() {
        return mainMenu;
    }

    public Menu getEditMenu(Product product) {
        editMenuService.setProduct(product);
        return editMenu;
    }

    private Menu createMainMenu() {
        Menu mainMenu = new Menu("MENU");
        MainMenuService mainMenuService = new MainMenuService(mainMenu);
        mainMenu.addItem(new MenuItem("Create product", mainMenuService::createProduct));
        mainMenu.addItem(new MenuItem("Find product by id", mainMenuService::findProductById));
        mainMenu.addItem(new MenuItem("Edit product", mainMenuService::editProduct));
        mainMenu.addItem(new MenuItem("Delete product", mainMenuService::deleteProduct));
        mainMenu.addItem(new MenuItem("Exit", mainMenuService::exit));
        return mainMenu;
    }

    private Menu createEditMenu() {
        Menu editMenu = new Menu("EDIT");
        editMenuService = new EditMenuService(editMenu);
        editMenu.addItem(new MenuItem("Name", editMenuService::editName));
        editMenu.addItem(new MenuItem("Category", editMenuService::editCategory));
        editMenu.addItem(new MenuItem("Price", editMenuService::editPrice));
        editMenu.addItem(new MenuItem("Discount", editMenuService::editDiscount));
        editMenu.addItem(new MenuItem("Description", editMenuService::editDescription));
        editMenu.addItem(new MenuItem("Save", editMenuService::save));
        return editMenu;
    }
}
