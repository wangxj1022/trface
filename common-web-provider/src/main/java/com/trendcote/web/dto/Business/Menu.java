package com.trendcote.web.dto.Business;

import java.util.List;

/**
 * @author 岩
 * @date 2018/10/16/016
 */
public class Menu {
    private List<Button> button;

    public Menu() {
    }

    public List<Button> getButton() {
        return button;
    }

    public void setButton(List<Button> button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "button=" + button +
                '}';
    }
}
