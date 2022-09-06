package com.fastcampus.MyWeb1.domain;

import org.springframework.web.util.UriComponentsBuilder;

public class SearchCondition {
    private Integer page = 1;
    private Integer pageSize = 15;
    private String keyword = "";
    private String option = "";

    public SearchCondition(){}

    public SearchCondition(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public SearchCondition(Integer page, Integer pageSize, String keyword, String option) {
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.option = option;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getOffset(){
        int tmp = page == 0 ? 1:page;
        return (tmp-1)*pageSize;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getQueryString(Integer page){
        UriComponentsBuilder temp = UriComponentsBuilder.newInstance();

        temp.queryParam("page", page);
        if(pageSize != 15)
            temp.queryParam("pageSize",pageSize);
        if(!"".equals(option))
            temp.queryParam("option", option);
        if(!"".equals(keyword))
            temp.queryParam("keyword", keyword);

        return temp.build().toString();
    }

    public String getQueryString(){
        UriComponentsBuilder temp = UriComponentsBuilder.newInstance();
        temp.queryParam("page", getPage());
        if(pageSize != 15)
            temp.queryParam("pageSize",pageSize);
        if(!"".equals(option))
            temp.queryParam("option", option);
        if(!"".equals(keyword))
            temp.queryParam("keyword", keyword);

        return temp.build().toString();
    }
}
