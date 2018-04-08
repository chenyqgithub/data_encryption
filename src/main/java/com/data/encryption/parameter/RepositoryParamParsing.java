package com.data.encryption.parameter;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件构造器
 * Created by user on 2017/9/20.
 */
public class RepositoryParamParsing {
    public static final String CONDITION_EQUALS = "EQUALS";
    public static final String CONDITION_LIKE = "LIKE";
    public static final String CONDITION_OR = "OR";
    public static final String CONDITION_IN = "IN";

    private String entityAttribute;//查询属性
    private String entityValue;//查询属性值
    private String endityConditionType;//查询条件

    public String getEntityAttribute() {
        return entityAttribute;
    }

    public void setEntityAttribute(String entityAttribute) {
        this.entityAttribute = entityAttribute;
    }

    public String getEntityValue() {
        return entityValue;
    }

    public void setEntityValue(String entityValue) {
        this.entityValue = entityValue;
    }

    public String getEndityConditionType() {
        return endityConditionType;
    }

    public void setEndityConditionType(String endityConditionType) {
        this.endityConditionType = endityConditionType;
    }

    public RepositoryParamParsing(String entityAttribute, String entityValue, String endityConditionType) {
        this.entityAttribute = entityAttribute;
        this.entityValue = entityValue;
        this.endityConditionType = endityConditionType;
    }

    public RepositoryParamParsing() {
    }

    public static Predicate[] structurePredicates(List<RepositoryParamParsing> list, Root<?> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> lstPredicates = new ArrayList<Predicate>();
        list.forEach((a) -> {
            if(!org.springframework.util.StringUtils.isEmpty(a.getEntityValue())) {
                switch (a.getEndityConditionType()) {
                    case RepositoryParamParsing.CONDITION_EQUALS:
                        Path<String> andName = root.get(a.entityAttribute);
                        Predicate andKey = criteriaBuilder.equal(andName, a.getEntityValue());
                        lstPredicates.add(andKey);
                        break;
                    case RepositoryParamParsing.CONDITION_LIKE:
                        Path<String> likeName = root.get(a.entityAttribute);
                        Predicate likeKey = criteriaBuilder.like(likeName, "%" + a.getEntityValue() + "%");
                        lstPredicates.add(likeKey);
                        break;
                    case RepositoryParamParsing.CONDITION_IN:
                        break;
                    case RepositoryParamParsing.CONDITION_OR:
                        break;
                }
            }
        });
        //默认条件筛选
        Path<String> status = root.get("status");
        Predicate statusKey = criteriaBuilder.equal(status, 1);
        lstPredicates.add(statusKey);
        Predicate[] arrayPredicates = new Predicate[lstPredicates.size()];
        return lstPredicates.toArray(arrayPredicates);
    }

}

