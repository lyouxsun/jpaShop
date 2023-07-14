package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    //@PersistenceContext : @RequiredArgsConstructor가 있으면 안써도 된다.
    private final EntityManager em;

    /**
     * 상품 저장
     */
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);       //새로 생성한 객체일 때
        } else {
            em.merge(item);         //기존에 영속성 컨텍스트에 존재할 때 (update랑 비슷하다고 이해하기)
        }
    }

    /**
     * 상품 단건 조회
     */
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    /**
     * 상품 전체 조회
     */
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
