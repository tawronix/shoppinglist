package com.javaguru.shoppinglist.console;

import com.javaguru.shoppinglist.product.Product;

public class UIFactory {
    private static UIFactory ourInstance = new UIFactory();

    private final Menu mainMenu;
    private final Menu editProductMenu;
    private final Menu shoppingCartMenu;

    private EditProductMenuService editProductMenuService;

    private UIFactory() {
        mainMenu = createMainMenu();
        editProductMenu = createEditProductMenu();
        shoppingCartMenu = createShoppingCartMenu();
    }

    public static UIFactory getInstance() {
        return ourInstance;
    }

    public Menu getMainMenu() {
        return mainMenu;
    }

    public Menu getEditProductMenu(Product product) {
        editProductMenuService.setProduct(product);
        return editProductMenu;
    }

    public Menu getShoppingCartMenu() {
        return shoppingCartMenu;
    }

    private Menu createMainMenu() {
        Menu mainMenu = new Menu("MENU");
        MainMenuService mainMenuService = new MainMenuService(mainMenu);
        mainMenu.addItem(new MenuItem("Create product", mainMenuService::createProduct));
        mainMenu.addItem(new MenuItem("Find product by id", mainMenuService::findProductById));
        mainMenu.addItem(new MenuItem("Edit product", mainMenuService::editProduct));
        mainMenu.addItem(new MenuItem("Delete product", mainMenuService::deleteProduct));
        mainMenu.addItem(new MenuItem("Shopping Cart", mainMenuService::shoppingCart));
        mainMenu.addItem(new MenuItem("Exit", mainMenuService::exit));
        return mainMenu;
    }

    private Menu createEditProductMenu() {
        Menu editProductMenu = new Menu("EDIT PRODUCT");
        editProductMenuService = new EditProductMenuService(editProductMenu);
        editProductMenu.addItem(new MenuItem("Name", editProductMenuService::editName));
        editProductMenu.addItem(new MenuItem("Category", editProductMenuService::editCategory));
        editProductMenu.addItem(new MenuItem("Price", editProductMenuService::editPrice));
        editProductMenu.addItem(new MenuItem("Discount", editProductMenuService::editDiscount));
        editProductMenu.addItem(new MenuItem("Description", editProductMenuService::editDescription));
        editProductMenu.addItem(new MenuItem("Save", editProductMenuService::save));
        editProductMenu.addItem(new MenuItem("Cancel", editProductMenuService::cancel));
        return editProductMenu;
    }

    private Menu createShoppingCartMenu() {
        Menu shoppingCartMenu = new Menu("SHOPPING CART");
        ShoppingCartMenuService shoppingCartMenuService = new ShoppingCartMenuService(shoppingCartMenu);
        shoppingCartMenu.addItem(new MenuItem("Create shopping cart", shoppingCartMenuService::createShoppingCart));
        shoppingCartMenu.addItem(new MenuItem("Find shopping cart", shoppingCartMenuService::findShoppingCart));
        shoppingCartMenu.addItem(new MenuItem("Delete shopping cart", shoppingCartMenuService::deleteShoppingCart));
        shoppingCartMenu.addItem(new MenuItem("Back", shoppingCartMenuService::back));
        return shoppingCartMenu;
    }
}