package com.amela.thymeleafDialect;

public class PagingUtils {

    private final int PAGES_PER_SITE = 3;

    public int getStart(int totalPage, int currentPage){
        if(totalPage <= PAGES_PER_SITE){
            return 0;
        } else {
            if(currentPage < PAGES_PER_SITE){
                return 0;
            } else {
                return currentPage - (PAGES_PER_SITE - 1);
            }
        }
    }

    public int getEnd(int totalPage, int currentPage){
        if(totalPage <= PAGES_PER_SITE){
            return totalPage - 1;
        } else {
            if(currentPage < PAGES_PER_SITE){
                return PAGES_PER_SITE - 1;
            } else {
                return currentPage;
            }
        }
    }
}
