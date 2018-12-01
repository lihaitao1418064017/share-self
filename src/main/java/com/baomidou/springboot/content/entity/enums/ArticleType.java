package com.baomidou.springboot.content.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  ArticleType {
    FRESH(0,"新鲜事"),
    VIDEO(1, "视频"),
    SUPER_MEMBER(2, "趣闻"),
    PHOTOS(3, "图集"),
    SPECIAL(4, "专题");

    private Integer value;
    private String typeName;


    ArticleType(final Integer value, final String typeName){
        this.value=value;
        this.typeName=typeName;
    }


    public static ArticleType getByTypeName(final String typeName){
        for(ArticleType articleType:ArticleType.values()){
            if(articleType.getTypeName().equals(typeName)){
                return articleType;
            }
        }
        return null;
    }

    public String getTypeName(){
        return this.typeName;
    }
    public Integer getValue(){
        return this.value;
    }

}
