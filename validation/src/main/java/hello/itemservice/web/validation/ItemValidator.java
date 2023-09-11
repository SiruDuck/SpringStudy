package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {
    //검증을 지원하느냐 와 검증을 하는로직
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        //파라미터로 넘어오는 clazz가 지원이 되냐 || 자식클래스까지 커버가능
        //item == clazz
        //item == subItem
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target; //캐스팅  || Errors bindingResult의 부모클래스

        if (!StringUtils.hasText(item.getItemName())){
            errors.rejectValue("itemName", "required");
            //new String[] {"required.item.itemName","required"};
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            errors.rejectValue("price","range", new Object[]{1000,1000000},null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999){
            errors.rejectValue("quantity","max", new Object[]{9999},null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){
                errors.reject("totalPriceMin", new Object[]{10000,resultPrice},null);
                //object는 데이터가 넘어오는게 아님
            }
        }

    }
}
