package com.amela.thymeleafDialect;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PagingExpressionFactory implements IExpressionObjectFactory {

    private static final String TEMPORAL_EVALUATION_VARIABLE_NAME = "paging";

    private static final Set<String> ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(TEMPORAL_EVALUATION_VARIABLE_NAME)));

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    @Override
    public Object buildObject(IExpressionContext context, String expressionObjectName) {
        if(TEMPORAL_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)){
            return new PagingUtils();
        }
        return null;
    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        return true;
    }
}
