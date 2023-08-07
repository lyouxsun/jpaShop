package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    /**
     * 새로운 item을 등록하는 화면 제시
     */
    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "/items/createItemForm";
    }

    /**
     * item을 등록하는 과정?
     */
    @PostMapping("/items/new")
    public String createForm(BookForm bookForm){
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";   // 저장된 책 목록을 보여주도록 redirect
    }

    //컨트롤러에서 (서비스 속의 메서드를 이용해서) 조회하고자 하는 데이터를 model에 담고, view를 return -> model이 view에 담겨서 화면으로 조회된다.
    @GetMapping("/items")
    public String itemList(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "/items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId")Long itemId, Model model){
        Book item = (Book) itemService.findOne(itemId);

        BookForm bookForm = new BookForm();
        bookForm.setId(itemId);
        bookForm.setAuthor(item.getAuthor());
        bookForm.setIsbn(item.getIsbn());
        bookForm.setName(item.getName());
        bookForm.setPrice(item.getPrice());
        bookForm.setStockQuantity(item.getStockQuantity());
        model.addAttribute("form", bookForm);

        return "/items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable String itemId, @ModelAttribute("form") BookForm bookForm){
        /*
        Book book = new Book();
        book.setId(bookForm.getId());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        */
        itemService.updateItem(bookForm.getId(), bookForm.getAuthor(), bookForm.getIsbn(), bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity());

        //itemService.saveItem(book);
        return "redirect:/items";
    }
}