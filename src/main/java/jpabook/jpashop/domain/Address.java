package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable //jpa의 내장타입이라는 의미 (어딘가에 내장될 수 있다.)
@Getter     //setter를 제공 안함 -> 변경이 아예 불가능(값을 새로 등록해야 한다,)
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }
    //기본생성자가 없으면 jpa가 리플랙션? 프록시? 를 못함

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
