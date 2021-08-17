package com.example.treasuremall.app.item.facade;

import com.example.treasuremall.api.facade.ItemQueryFacade;
import org.springframework.stereotype.Service;

@Service("itemQueryFacade")
public class ItemQueryFacadeImpl implements ItemQueryFacade {

    @Override
    public void queryItemById(Integer itemId) {
        System.out.println("Hello World！！!");
    }

}
