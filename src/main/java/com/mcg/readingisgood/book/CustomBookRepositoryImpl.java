package com.mcg.readingisgood.book;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class CustomBookRepositoryImpl implements CustomBookRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void updateBookStock(String id, Long newStock) {
        Query query = new Query().addCriteria(where("_id").is(id));

        Update update = new Update();
        update.set("stock", newStock);
        mongoTemplate.update(Book.class).matching(query).apply(update).first();
    }
}
