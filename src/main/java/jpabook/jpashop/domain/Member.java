package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue //자동생성되도록 해주는 애노테이션
    @Column(name="member_id")
    private Long id;

    private String name;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "member") //연관관계 주인이 아닐 때 (읽기전용이 됨),, order테이블의 member 필드에 의해 mapped됐다는 의미
    private List<Order> orders = new ArrayList<>();
}
