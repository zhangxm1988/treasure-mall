package com.example.treasuremall.api.facade;


public interface ItemQueryFacade {

    /**
     * 根据商品ID查询商品
     * @param itemId 商品ID
     */
    void queryItemById(Integer itemId);

}
