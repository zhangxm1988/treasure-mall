package com.example.treasuremall.api;

import com.example.treasuremall.api.content.item.ItemContent;

public class ApiTest {

    public static void main(String[] args) {
        TreasureMall.item.createQuery().addContent(ItemContent.BRAND).queryItemById(1L);
    }

}
