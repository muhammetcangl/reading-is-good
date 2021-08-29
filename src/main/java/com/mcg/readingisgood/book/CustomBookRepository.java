package com.mcg.readingisgood.book;

public interface CustomBookRepository {
    public void updateBookStock(String id, Long newStock);
}
