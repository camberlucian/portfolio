/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMController;

import FMDao.FMDaoException;
import FMDto.Order;
import FMServiceLayer.FMServiceLayer;
import FMServiceLayer.InvalidInputException;
import FMUI.FMView;
import java.time.LocalDate;

/**
 *
 * @author camber
 */
public class FMController {

    private FMView view;
    private FMServiceLayer service;

    public FMController(FMView view, FMServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() throws InvalidInputException {
        boolean keepGoing = true;
        boolean training = true;
        int menuChoice;
        try {
            training = service.load();
        } catch (FMDaoException e) {
            view.displayErrorMessage(e.getLocalizedMessage());
        }

        if (training) {
            view.displayBanner("RUNNING IN TRAINING MODE");
        } else {
            view.displayBanner("RUNNING IN PRODUCTION MODE");
        }

        while (keepGoing) {
            menuChoice = view.displayMenuAndGetInput();

            switch (menuChoice) {
                case 1:
                    newOrder();
                    break;
                case 2:
                    showOrders();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    deleteOrder();
                    break;

                case 5:
                    if (training) {
                        view.displayBanner("Training Mode Cannot Save Data");
                    } else {
                        save();
                    }
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    view.displayBanner("no");

            }
        }
    }

    public void newOrder() {
        Order newOrder = view.createBasicOrder(service.getAllProducts(), service.getAllStates());
        try {
            newOrder = service.completeOrder(newOrder);
            boolean confirmed = view.confirmOrder(newOrder);
            if (confirmed) {
                service.addOrder(newOrder);
                view.displayBanner("Order added");
            }
        } catch (InvalidInputException e) {
            view.displayErrorMessage(e.getLocalizedMessage());
        }

    }

    public void showOrders() {
        try {
            view.printOrders(service.getOrders(view.getDateInput()));
        } catch (InvalidInputException e) {
            view.displayErrorMessage(e.getLocalizedMessage());
        }
    }

    public void editOrder() throws InvalidInputException {
        LocalDate date = view.getDateInput();
        int orderNumber = view.getOrderId();
        Order oldOrder = null;
        try {
            oldOrder = service.getSpecificOrder(date, orderNumber);
        } catch (InvalidInputException e) {
            view.displayErrorMessage(e.getLocalizedMessage());
        }
        if (view.confirmOrder(oldOrder)) {
            service.removeOrder(oldOrder);
            Order newOrder = view.editOrderBasic(oldOrder, service.getAllStates());
            boolean recalculate = view.getConfirmation("Would you like to recalculate the totals of this order based on the fields you edited?");
            if (recalculate) {
                newOrder = service.RecalcEditedOrder(oldOrder, newOrder);
            }
            if (view.confirmOrder(newOrder)) {
                service.addOrder(newOrder);
            } else {
                service.addOrder(oldOrder);
            }
        }
    }

    public void deleteOrder() {
        LocalDate date = view.getDateInput();
        int orderNumber = view.getOrderId();
        Order deletedOrder = null;
        try {
            deletedOrder = service.getSpecificOrder(date, orderNumber);
        } catch (InvalidInputException e) {
            view.displayErrorMessage(e.getLocalizedMessage());
        }
        if (view.confirmOrder(deletedOrder)) {
            service.removeOrder(deletedOrder);
        }
    }



    public void save() {
        boolean confirmed = view.getConfirmation("Are you sure you wish to save?");
        if (confirmed) {
            try {
                service.saveWork();
            } catch (FMDaoException e) {
                view.displayErrorMessage(e.getLocalizedMessage());
            }
        }
    }

}
// June 11
