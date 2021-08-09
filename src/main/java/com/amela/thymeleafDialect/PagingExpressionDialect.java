package com.amela.thymeleafDialect;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

public class PagingExpressionDialect extends AbstractDialect implements IExpressionObjectDialect {

    private final IExpressionObjectFactory PAGING_EXPRESSION_OBJECTS_FACTORY = new PagingExpressionFactory();

    public PagingExpressionDialect(String name) {
        super(name);
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return PAGING_EXPRESSION_OBJECTS_FACTORY;
    }
}
