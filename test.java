import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import java.util.Date;

@Repository
public class SchemeRepositoryCustomImpl implements SchemeRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public SchemeRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Scheme> findSchemesBySchemeIdOrPriority(String schemeId) {
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("schemeId").is(schemeId),
                Criteria.where("schemeId").exists(false).and("validIndicator").is(true)
        );

        Aggregation aggregation = newAggregation(
                match(criteria),
                lookup("plan", "plans", "_id", "plans"),
                unwind("plans"),
                lookup("document", "plans.documents", "_id", "plans.documents"),
                lookup("content", "plans.contents", "_id", "plans.contents"),
                lookup("benefit", "plans.benefits", "_id", "plans.benefits"),
                lookup("pricing", "plans.pricings", "_id", "plans.pricings"),
                project("schemeId", "priority").and(
                        Field.from("plans").filteredBy(
                                Criteria.where("validIndicator").is(true)
                                        .andOperator(
                                                Criteria.where("startDate").lte(new Date()),
                                                Criteria.where("endDate").gte(new Date())
                                        )
                        )
                ).as("plans"),
                group("_id").first("schemeId").as("schemeId").first("priority").as("priority").push("plans").as("plans"),
                sort(Sort.Direction.DESC, "priority")
        );

        return mongoTemplate.aggregate(aggregation, "scheme", Scheme.class).getMappedResults();
    }
}
