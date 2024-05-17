import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SchemeService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Document> getSchemeWithDetails(String schemeId) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.ofHours(8));
        String currentDate = now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        MatchOperation matchScheme = Aggregation.match(
                new Criteria().orOperator(
                        Criteria.where("schemeId").is(schemeId),
                        new Criteria().andOperator(
                                Criteria.where("validIndicator").is(true),
                                Criteria.where("effectivestartTime").exists(true).ne(""),
                                Criteria.where("effectiveEndTime").exists(true).ne("")
                        )
                )
        );

        SortOperation sortByPriority = Aggregation.sort(Sort.by(Sort.Direction.DESC, "priority"));

        LimitOperation limitToTopPriority = Aggregation.limit(1);

        LookupOperation lookupPlans = LookupOperation.newLookup()
                .from("plan")
                .localField("_id")
                .foreignField("schemeId")
                .as("plans");

        AggregationOperation filterPlans = context -> new Document("$set", new Document("plans", new Document("$filter", 
                new Document("input", "$plans")
                .append("as", "plan")
                .append("cond", new Document("$and", List.of(
                        new Document("$eq", List.of("$$plan.validIndicator", true)),
                        new Document("$lte", List.of("$$plan.effectivestartTime", currentDate)),
                        new Document("$gte", List.of("$$plan.effectiveEndTime", currentDate))
                )))
        )));

        LookupOperation lookupDocuments = LookupOperation.newLookup()
                .from("document")
                .localField("plans._id")
                .foreignField("planId")
                .as("plans.documents");

        AggregationOperation filterDocuments = context -> new Document("$set", new Document("plans.documents", new Document("$filter", 
                new Document("input", "$plans.documents")
                .append("as", "document")
                .append("cond", new Document("$and", List.of(
                        new Document("$eq", List.of("$$document.validIndicator", true)),
                        new Document("$lte", List.of("$$document.effectivestartTime", currentDate)),
                        new Document("$gte", List.of("$$document.effectiveEndTime", currentDate))
                )))
        )));

        LookupOperation lookupContents = LookupOperation.newLookup()
                .from("content")
                .localField("plans._id")
                .foreignField("planId")
                .as("plans.contents");

        AggregationOperation filterContents = context -> new Document("$set", new Document("plans.contents", new Document("$filter", 
                new Document("input", "$plans.contents")
                .append("as", "content")
                .append("cond", new Document("$and", List.of(
                        new Document("$eq", List.of("$$content.validIndicator", true)),
                        new Document("$lte", List.of("$$content.effectivestartTime", currentDate)),
                        new Document("$gte", List.of("$$content.effectiveEndTime", currentDate))
                )))
        )));

        LookupOperation lookupBenefits = LookupOperation.newLookup()
                .from("benefit")
                .localField("plans._id")
                .foreignField("planId")
                .as("plans.benefits");

        AggregationOperation filterBenefits = context -> new Document("$set", new Document("plans.benefits", new Document("$filter", 
                new Document("input", "$plans.benefits")
                .append("as", "benefit")
                .append("cond", new Document("$and", List.of(
                        new Document("$eq", List.of("$$benefit.validIndicator", true)),
                        new Document("$lte", List.of("$$benefit.effectivestartTime", currentDate)),
                        new Document("$gte", List.of("$$benefit.effectiveEndTime", currentDate))
                )))
        )));

        LookupOperation lookupPricings = LookupOperation.newLookup()
                .from("pricing")
                .localField("plans._id")
                .foreignField("planId")
                .as("plans.pricings");

        AggregationOperation filterPricings = context -> new Document("$set", new Document("plans.pricings", new Document("$filter", 
                new Document("input", "$plans.pricings")
                .append("as", "pricing")
                .append("cond", new Document("$and", List.of(
                        new Document("$eq", List.of("$$pricing.validIndicator", true)),
                        new Document("$lte", List.of("$$pricing.effectivestartTime", currentDate)),
                        new Document("$gte", List.of("$$pricing.effectiveEndTime", currentDate))
                )))
        )));

        Aggregation aggregation = Aggregation.newAggregation(
                matchScheme,
                sortByPriority,
                limitToTopPriority,
                lookupPlans,
                filterPlans,
                lookupDocuments,
                filterDocuments,
                lookupContents,
                filterContents,
                lookupBenefits,
                filterBenefits,
                lookupPricings,
                filterPricings
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "scheme", Document.class);
        return results.getMappedResults();
    }
}
