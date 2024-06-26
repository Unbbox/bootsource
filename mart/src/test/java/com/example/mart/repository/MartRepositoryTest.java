package com.example.mart.repository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mart.entity.Delivery;
import com.example.mart.entity.DeliveryStatus;
import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.OrderStatus;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MartRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void insertTest() {
        // 멤버3
        memberRepository.save(Member.builder().name("user1").city("서울시").street("중구").zipcode("15102").build());
        memberRepository.save(Member.builder().name("user2").city("경기도").street("수원시").zipcode("35102").build());
        memberRepository.save(Member.builder().name("user3").city("부산시").street("서구").zipcode("12102").build());

        // 아이템3
        itemRepository.save(Item.builder().name("티셔츠").price(28500).stockQuantity(100).build());
        itemRepository.save(Item.builder().name("바지").price(35000).stockQuantity(100).build());
        itemRepository.save(Item.builder().name("양말").price(2800).stockQuantity(100).build());
    }

    // 주문
    @Test
    public void orderInsertTest() {
        // 누가 주문하느냐?
        Member member = Member.builder().id(1L).build();

        // 어떤 아이템
        Item item = Item.builder().id(1L).build();

        // 주문 + 주문상품
        Order order = Order.builder().member(member).orderDate(LocalDateTime.now()).orderStatus(OrderStatus.ORDER)
                .build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder().item(item).order(order).orderPrice(26000).count(2).build();
        orderItemRepository.save(orderItem);
    }

    @Test
    public void readTest() {
        // 전체 회원 조회
        memberRepository.findAll().forEach(member -> System.out.println(member));

        // 전체 아이템 조회
        itemRepository.findAll().forEach(item -> System.out.println(item));

        // 주문아이텝 조회 시 주문 정보 출력(OrderItem(N):Order(1))
        // rderItem(id=1, item=Item(id=1, name=티셔츠, price=28500, stockQuantity=100),
        // order=Order(id=1, member=Member(id=1, name=user1, zipcode=15102, city=서울시,
        // street=중구), orderDate=2024-04-05T15:04:25.499669, orderStatus=ORDER),
        // orderPrice=26000, count=2)
        // 객체 그래프 탐색 => N:1 관계에서 FetchType.EAGER 이기 때문에
        orderItemRepository.findAll().forEach(entity -> {
            System.out.println(entity);
            System.out.println("상품정보 " + entity.getItem());
            System.out.println("구매자 " + entity.getOrder().getMember().getName());
        });
    }

    // @Transactional
    @Test
    public void readTest2() {
        // @OneToMany 를 이용해 조회
        // 관련있는 엔티티를 처음부터 안가지고 옴
        // Order : OrderItem
        Order order = orderRepository.findById(3L).get();
        System.out.println(order); // 에러 발생 =>

        // Order를 기준으로 OrderItem 조회
        // 1. transactional 사용 // 2. FetchType 변경
        System.out.println(order.getOrderItems());

        // 배송지 조회
        System.out.println(order.getDelivery().getCity());
    }

    @Transactional
    @Test
    public void readTest3() {
        // @OneToMany 를 이용해 조회
        // 관련있는 엔티티를 처음부터 안가지고 옴
        // Member : Order
        // 회원의 주문내역 조회(@Transactional)
        Member member = memberRepository.findById(1L).get();
        System.out.println(member);
        System.out.println(member.getOrders());

        // Order order = orderRepository.findById(member.getOrders());
        // order.getMember() == member.getId();
    }

    @Test
    public void updateTest() {
        // 멤버 주소 수정
        Member member = memberRepository.findById(1L).get();
        member.setCity("대전");
        memberRepository.save(member);

        // 아이템 가격 수정
        Item item = itemRepository.findById(1L).get();
        item.setPrice(27700);
        itemRepository.save(item);

        // 주문상태 수정 CANCEL
        Order order = orderRepository.findById(1L).get();
        order.setOrderStatus(OrderStatus.CANCEL);
        orderRepository.save(order);
    }

    @Test
    public void deleteTest() {
        // 주문 제거 시 주문 아이템 제거 가능?
        // 주문 조회 시 주문 아이템 조회 가능?
        // orderRepository.deleteById(1L); ==> 주문 아이템이 존재하기 때문에 에러남

        // 주문아이템 제거 후 주문 제거
        orderItemRepository.delete(OrderItem.builder().id(1L).build());
        orderRepository.deleteById(1L);
    }

    @Test
    public void orderInsertDeliveryTest() {
        // 누가 주문하느냐?
        Member member = Member.builder().id(2L).build();

        // 어떤 아이템
        Item item = Item.builder().id(2L).build();

        // 배송지 입력
        Delivery delivery = Delivery.builder().city("서울시").street("123-12").zipcode("11160")
                .deliveryStatus(DeliveryStatus.READY).build();
        deliveryRepository.save(delivery);

        // 주문 + 주문상품
        Order order = Order.builder().member(member).orderDate(LocalDateTime.now()).orderStatus(OrderStatus.ORDER)
                .delivery(null)
                .build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder().item(item).order(order).orderPrice(26000).count(2).build();
        orderItemRepository.save(orderItem);
    }

    @Test
    public void deliveryOrderGet() {
        // 배송지를 통해서 관련있는 Order 가져오기
        Delivery delivery = deliveryRepository.findById(1L).get();

        System.out.println(delivery);
        System.out.println("관련 주문" + delivery.getOrder());
    }
}
