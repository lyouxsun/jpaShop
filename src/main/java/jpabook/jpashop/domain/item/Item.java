package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter//변수를 수정해야 할 때, @Setter를 쓰는게 아니라 //같은 클래스 내에 addStock, subtract 처럼 핵심 비즈니스 메서드를 만들어서 그걸로 변경하는 것이 좋다!!!
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
public abstract class Item {
    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private int price;  //item 한개당 가격

    private int stockQuantity;   //남은 수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==// : 재고가 늘고 줄고 하는 로직이 필요

    /**
     * stock 증가
     */
    public void addStock(int quantity){
        this.stockQuantity+=quantity;
    }

    /**
     * stock 감소 & 0보다 작아지는거 방지
     */
    public void subtractStock(int quantity){
        int remainStock = this.stockQuantity-quantity;
        if(remainStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=remainStock;
    }
}
