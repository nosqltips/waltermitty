package com.nosqlrevolution.util;

import com.nosqlrevolution.enums.BoostField;
import com.nosqlrevolution.enums.Wildcard;
import com.nosqlrevolution.model.Boost;
import com.nosqlrevolution.model.BuilderModel;
import com.nosqlrevolution.model.BuilderModel.BooleanType;
import static com.nosqlrevolution.model.BuilderModel.BooleanType.*;
import static com.nosqlrevolution.model.BuilderModel.QueryType.*;
import com.nosqlrevolution.model.FacetRequest;
import com.nosqlrevolution.model.SelectableFacet;
import java.util.List;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import static org.elasticsearch.index.query.FilterBuilders.*;
import org.elasticsearch.index.query.QueryBuilder;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 *
 * @author cbrown
 */
public class QueryUtil {
    public static QueryBuilder getFilteredQuery(QueryBuilder qb1, FilterBuilder fb1) {
        return filteredQuery(
            qb1, 
            fb1
            );
    }

    public static QueryBuilder getBooleanQuery(List<BuilderModel> models) {
        BoolQueryBuilder bq = boolQuery();
        for (BuilderModel model : models) {
            if (model.getBooleanType() == MUST) {
                bq.must(model.getQueryBuilder());
            } else if (model.getBooleanType() == SHOULD) {
                bq.should(model.getQueryBuilder());
            } else {
                bq.mustNot(model.getQueryBuilder());
            }
        }
        return bq;
    }

    public static QueryBuilder getTermBuilder(String field, String value) {
        return termQuery(field, value);
    }
    
    public static QueryBuilder getTermBuilder(String field, String value, float boost) {
        return termQuery(field, value)
                .boost(boost);
    }
    
    public static QueryBuilder getGtRangeQuery(String field, String value) {
        return rangeQuery(field)
                .gte(value);
    }
    public static QueryBuilder getMatchAllQuery() {
        return matchAllQuery();
    }

    public static FilterBuilder getMatchAllFilter() {
        return matchAllFilter();
    }

    public static FilterBuilder getBooleanFilter(List<BuilderModel> models) {
        BoolFilterBuilder bq = boolFilter();
        for (BuilderModel model : models) {
            if (model.getBooleanType() == MUST) {
                bq.must(model.getFilterBuilder());
            } else if (model.getBooleanType() == SHOULD) {
                bq.should(model.getFilterBuilder());
            } else {
                bq.mustNot(model.getFilterBuilder());
            }
        }
        return bq;
    }

    public static QueryBuilder getQueryBuilder(String field, String term) {
        Wildcard wild = ParseUtil.isWildcard(term);
        if (wild == Wildcard.NONE) {
            //System.out.println("Query Wildcard.NONE " + term);
            return termQuery(field, term);
        } else if (wild == Wildcard.PREFIX) {
            //System.out.println("Query Wildcard.PREFIX " + term.substring(0, term.length() -1));
            return prefixQuery(field, term.substring(0, term.length() -1));
        } else {
            //System.out.println("Query Wildcard.FULL " + term);
            return wildcardQuery(field, term);
        }       
    }
    
    public static FilterBuilder getFilterBuilder(String field, String term) {
        Wildcard wild = ParseUtil.isWildcard(term);
        if (wild == Wildcard.NONE) {
            //System.out.println("Filter Wildcard.NONE " + term);
            return termFilter(field, term);
        } else if (wild == Wildcard.PREFIX) {
            //System.out.println("Filter Wildcard.PREFIX " + term.substring(0, term.length() -1));
            return prefixFilter(field, term.substring(0, term.length() -1));
        } else {
            // no wildcard filter
            //System.out.println("Filter Wildcard.FULL " + term);
            return null;
        }       
    }    

    // TODO: range filters are also available
    public static QueryBuilder getRangeQueryBuilder(String field, String from, String to) {
        if (from == null && to == null) {
            return null;
        }
        
        // Range <
        if (from == null) {
            return rangeQuery(field)
                .lt(to);
        }
        
        // Range >
        if (to == null) {
            return rangeQuery(field)
                .gt(from);
        }

        // Range inclusive
        return rangeQuery(field)
            .from(from)
            .to(to);
    }
    
    // TODO: range filters are also available
    public static FilterBuilder getRangeFilterBuilder(String field, String from, String to) {
        if (from == null && to == null) {
            return null;
        }
        
        // Range <
        if (from == null) {
            return rangeFilter(field)
                .lt(to);
        }
        
        // Range >
        if (to == null) {
            return rangeFilter(field)
                .gt(from);
        }

        // Range inclusive
        return rangeFilter(field)
            .from(from)
            .to(to);
    }
    
    public static QueryBuilder getTextQuery(String field, String terms) {
        return matchQuery(field, terms)
                .slop(1)
                .analyzer("simple");                
    }
    
    /**
     * Get the user selections for each FacetField and add to the builder
     * 
     * @param builders
     * @param previousRequest
     * @param boolType
     * @return 
     */
    public static List<BuilderModel> addAllSelections(List<BuilderModel> builders, List<FacetRequest> previousRequest, BooleanType boolType) {
        if (previousRequest == null) { return null; }
        
        for (FacetRequest request: previousRequest) {
            List<SelectableFacet> selected = AggregationUtil.getSelections(request.getSelectables());
            if (selected != null) {
                for (SelectableFacet select: selected) {
                    builders.add(new BuilderModel(getQueryBuilder(request.getField().getName(), select.getName()), QUERY, boolType));
                }
            }
        }
        
        return builders;
    }
    
    /**
     * Get the user selections for each FacetField and add to the builder
     * 
     * @param builders
     * @param previousRequest
     * @param boolType
     * @param boosts
     * @return 
     */
    public static List<BuilderModel> addAllSelectionsMLT(List<BuilderModel> builders, List<FacetRequest> previousRequest, BooleanType boolType, List<Boost> boosts) {
        if (previousRequest == null) { return null; }
        
        for (FacetRequest request: previousRequest) {
            List<SelectableFacet> selected = AggregationUtil.getSelections(request.getSelectables());
            if (selected != null) {
                for (SelectableFacet select: selected) {
                    float boost = 1.0F;
                    switch (request.getField()) {
                        case BIRTH_YEAR:                            
                            boost = getBoost(BoostField.BIRTH_YEAR, boosts); break;
                        case GENDER:
                            boost = getBoost(BoostField.GENDER, boosts); break;
                        case STATE:
                            boost = getBoost(BoostField.STATE, boosts); break;
                        case NUM_CLAIMS:
                            boost = getBoost(BoostField.NUM_CLAIMS, boosts); break;
                        case NUM_CONTRIBUTIONS:
                            boost = getBoost(BoostField.NUM_CONTRIBUTIONS, boosts); break;
                        case NUM_DEPENDENTS:
                            boost = getBoost(BoostField.NUM_DEPENDENTS, boosts); break;
                        case NUM_PAYMENTS:
                            boost = getBoost(BoostField.NUM_PAYMENTS, boosts); break;
                        case TOTAL_BALANCES:
                            boost = getBoost(BoostField.TOTAL_BALANCES, boosts); break;
                        case TOTAL_CLAIMS:
                            boost = getBoost(BoostField.TOTAL_CLAIMS, boosts); break;
                        case TOTAL_CONTRIBUTIONS:
                            boost = getBoost(BoostField.TOTAL_CONTRIBUTIONS, boosts); break;
                        case TOTAL_PAYMENTS:
                            boost = getBoost(BoostField.TOTAL_PAYMENTS, boosts); break;
                        case CPT_CODES_UNIQUE:
                            boost = getBoost(BoostField.CPT_CODES_UNIQUE ,boosts); break;
                    }
                    builders.add(new BuilderModel(getTermBuilder(request.getField().getName(), select.getName(), boost), QUERY, boolType));
                }
            }
        }
        
        return builders;
    }
    
    private static float getBoost(BoostField field, List<Boost> boosts) {
        for (Boost boost: boosts) {
            if (field == boost.getField()) {
                return boost.getBoost();
            }
        }
        
        // If not found, return default boost
        return field.getDefaultBoost();
    }
 }
