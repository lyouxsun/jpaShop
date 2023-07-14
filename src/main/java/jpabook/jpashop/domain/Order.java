package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
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

    //==생성메서드==//   : 근데 생성 메서드 자기 자신 안에 있는게 원래 맞는건가,,
    /**
     * 주문 (생성) 기능
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);     //주문상태를 ORDER로 강제해두기
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소 기능
     */
    public void cancel(){
        //배송상태확인 -> 주문 상태만 바꾸면됨
        if(delivery.getStatus() == DeliveryStatus.COMP){    //이미 배송이 시작돼버린 상태라면
            throw new IllegalStateException("이미 배송완료된 상품은 주문 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);

        //한개의 주문에 여러개의 item존재가능 -> 각각의 item에 cancel 날려주기
        for(OrderItem orderItem: orderItems){
            orderItem.cancel();     //OrderItem의 cancel메서드에서 각각의 수량을 원상복구 시키면 됨
        }
    }

    //==조회 로직==//
    /**
     * 주문의 총금액 조회
     */
    public int getTotalPrice(){
        int totalPrice=0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
            //totalPrice += orderItem.getOrderPrice() * orderItem.getCount();       //왜 이렇게 안하고 굳이 메서드를 하나 더 만드는거지?
        }
        return totalPrice;
    }
}
