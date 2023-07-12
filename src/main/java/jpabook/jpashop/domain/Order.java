package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)      //***** 양방향 연관관계 : 연관관계 주인을 정해줘야 한다. *****
    @JoinColumn(name="member_id")   //매핑을 뭘로 할거냐를 정해줌 -> FK이름을 의미
    //주인인 관계는 아무것도 안적어줘도 됨
    private Member member;      //member:order = 1:N

    @OneToMany(mappedBy = "order", cascade=CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")       //주인
    private Delivery delivery;

    private LocalDateTime orderDate;        //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;     //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);

    }
}
