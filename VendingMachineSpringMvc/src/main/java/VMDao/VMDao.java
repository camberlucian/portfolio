/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VMDao;

import java.util.List;

/**
 *
 * @author camber
 */
public interface VMDao {

    public Snack getSnack(long id);

    public List<Snack> getAllSnacks();

    public Snack addSnack(Snack snack);

    public Snack updateSnack(Snack snack);

    public void deleteSnack(long id);

}

// june 28
