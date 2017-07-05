/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc;

import ServiceLayer.VMMoneyException;
import ServiceLayer.VMServiceLayer;
import ServiceLayer.VMVendException;
import VMDao.Snack;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author camber
 */
@Controller
public class VMController {

    private VMServiceLayer service;

    @Inject
    public VMController(VMServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String displayMachine(HttpServletRequest request, Model model) {

        List<Snack> snacks = service.giveSlots();
        model.addAttribute("snacks", snacks);

        BigDecimal moneyIn = service.getMoney();
        model.addAttribute("money", moneyIn);

        model.addAttribute("message", "select");

        return "vendingMachine";
    }

    @RequestMapping(value = "/insertMoney", method = RequestMethod.GET)
    public String insertMoney(HttpServletRequest request, Model model) {
        String amount = request.getParameter("amount");
        try {
            service.addMoney(amount);
        } catch (VMMoneyException e) {
            model.addAttribute("message", e.getLocalizedMessage());
        }

        List<Snack> snacks = service.giveSlots();
        model.addAttribute("snacks", snacks);

        BigDecimal moneyIn = service.getMoney();
        model.addAttribute("money", moneyIn);

        return "vendingMachine";
    }

    @RequestMapping(value = "/vend", method = RequestMethod.POST)
    public String vend(HttpServletRequest request, Model model) {
        String id = request.getParameter("itemSelect");
        String message;
        try {
            message = service.vend(id);
            model.addAttribute("message", message);
            model.addAttribute("change", service.giveChange());
        } catch (VMVendException e) {
            model.addAttribute("message", e.getLocalizedMessage());

        }

        BigDecimal moneyIn = service.getMoney();
        model.addAttribute("money", moneyIn);

        List<Snack> snacks = service.giveSlots();
        model.addAttribute("snacks", snacks);

        return "vendingMachine";
    }

    @RequestMapping(value = "/giveChange", method = RequestMethod.GET)
    public String giveChange(Model model) {
        model.addAttribute("change", service.giveChange());

        BigDecimal moneyIn = service.getMoney();
        model.addAttribute("money", moneyIn);

        List<Snack> snacks = service.giveSlots();
        model.addAttribute("snacks", snacks);

        model.addAttribute("message", "select");

        return "vendingMachine";
    }

}

// june 28
