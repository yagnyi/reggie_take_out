package com.oil.reggie_take_out.dto;

import com.oil.reggie_take_out.entity.Setmeal;
import com.oil.reggie_take_out.entity.SetmealDish;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes = new ArrayList<>();

    private String categoryName;


}
