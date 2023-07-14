package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;    //주문 가격
    private int count;      //주문 수량

    //==생성 메서드==//
    /**
     * 주문에 속해있는 각각의 item 생성 기능
     */
    public static OrderItem  createOrderItem (Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        //주문한 만큼 item의 수량을 (변경)빼주는 거 까먹지마!!!
        item.subtractStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    /**
     * 주문에 속해있는 각각의 item 취소 기능
     */
    public void cancel() {
        getItem().addStock(count);
        //Item클래스 안에 있는 addStock메서드를 쓰려고 하니까 getItem을 해야됨
    }

    //==(금액)조회 로직==//

    /**
     * 주문 상품 각각의 총금액 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
