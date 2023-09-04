package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor  // <- final 이 붙은 애를 생성자로 만들어줌
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items (Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item",item);

        return "basic/item";
    }
   // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){

        itemRepository.save(item);
        //model.addAttribute("item",item); //자동 추가, 생략 가능

        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model){
        //Item -> item 소문자로 자동 치환

        itemRepository.save(item);
        //model.addAttribute("item",item); //자동 추가, 생략 가능
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV4(Item item){ //@ModelAttribute가 생략 된것
        itemRepository.save(item);
        return "basic/item";
    }
    //@PostMapping("/add")
    public String addItemV5(Item item){ //@ModelAttribute가 생략 된것
        itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();
        //변수를 더해서 사용하면 URL인코딩이 안되기 때문에 위험함. -> RedirectAttributes 권장
    }
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){ //@ModelAttribute가 생략 된것
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",saveItem.getId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }

}
