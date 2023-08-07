package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {
    private  Long id;

    private String name;
    private int price;  //item 한개당 가격
    private int stockQuantity;   //남은 수량

    private String author;
    private String isbn;
}
