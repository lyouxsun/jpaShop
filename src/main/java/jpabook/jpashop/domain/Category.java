package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name="category_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;        //하나의 카테고리는 여러개의 자식을 가질 수 있다.

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();     //하나의 자식은 여러개의 카테고리를 가질 수 있다.

    //==연관관계 (편의) 메서드==//
    public void addChildCategory(Category child){         //카테고리가 셀프로 parent와 child를 넣어줌, //카테고리 안에 다른 두 객체가 있다고 생각하기
        this.child.add(child);
        child.setParent(this);
    }
}
