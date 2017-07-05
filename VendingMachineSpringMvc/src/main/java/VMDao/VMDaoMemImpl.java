/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VMDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author camber
 */
public class VMDaoMemImpl implements VMDao {

    private Map<Long, Snack> inventory;
    private long currentId;

    public VMDaoMemImpl() {
        this.inventory = new HashMap<>();
        this.currentId = 0;

        String[] snackNames = {"munchies", "cheetos", "grippos", "lifeSavers", "doritos", "chex mix", "fruit pie", "honey bun", "donettes"};
        String[] snackPrices = {"1.50", "1.25", "1.75", "1.00", "1.25", "1.25", "1.50", "1.50", "2.00"};
        int[] snackQuantities = {1, 2, 3, 2, 1, 3, 2, 4, 3};

        for (int i = 0; i < snackNames.length; i++) {
            Snack newSnack = new Snack();
            newSnack.setName(snackNames[i]);
            newSnack.setPrice(new BigDecimal(snackPrices[i]));
            newSnack.setQuantity(snackQuantities[i]);
            this.addSnack(newSnack);
        }
    }

    @Override
    public Snack getSnack(long id) {
        return inventory.get(id);
    }

    @Override
    public List<Snack> getAllSnacks() {
        return new ArrayList(inventory.values());
    }

    @Override
    public Snack addSnack(Snack snack) {
        snack.setSnackId(currentId + 1);
        currentId = snack.getSnackId();
        inventory.put(snack.getSnackId(), snack);
        return snack;
    }

    @Override
    public Snack updateSnack(Snack snack) {
        inventory.put(snack.getSnackId(), snack);
        return snack;
    }

    @Override
    public void deleteSnack(long id) {
        inventory.remove(id);
    }

}

// june 28
