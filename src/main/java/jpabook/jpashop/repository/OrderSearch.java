package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    //회원이름, 주문상태로 주문 검색 가능
    private String memberName;
    private OrderStatus orderStatus;    //[ORDER, CANCEL]
}
