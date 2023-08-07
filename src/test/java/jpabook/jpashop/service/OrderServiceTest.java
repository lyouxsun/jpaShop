package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("구의 증명", "최진영", 10000, 10);

        int orderStock=5;

        //when : 주문 생성
        Long orderId = orderService.order(member.getId(), book.getId(), orderStock);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 주문상태가 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("상품 주문시 상품 종류 수가 정확해야 한다,", 1, getOrder.getOrderItems().size());
        assertEquals("상품 주문시 총금액이 정확해야 한다.", book.getPrice()*orderStock, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 합니다.", 5, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("구의 증명", "최진영", 10000, 10);
        int orderStock=17;

        //when
        orderService.order(member.getId(), book.getId(), orderStock);

        //then
        fail("재고 수량 에러가 발생해야 합니다.");

    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("구의 증명", "최진영", 10000, 10);
        int orderStock = 7;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderStock);
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 상태는 cancel이 되어야 합니다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("제품의 수량이 원상복구 되어야 합니다.", 10, book.getStockQuantity());

    }


    private Book createBook(String bookName, String author, int price, int stockQuatity) {
        Book book = new Book();
        book.setName(bookName);
        book.setAuthor(author);
        book.setPrice(price);
        book.setStockQuantity(stockQuatity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("이영선");
        member.setAddress(new Address("서울", "충정로", "12345"));
        em.persist(member);
        return member;
    }

}