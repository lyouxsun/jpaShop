package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
public abstract class Item {
    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private int price;  //item 한개당 가격

    private int stockQuatity;   //남은 수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
