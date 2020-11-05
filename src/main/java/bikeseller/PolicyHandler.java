package bikeseller;

import bikeseller.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    PromotionRepository promotionRepository;


    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCompleted_PointSave(@Payload PayCompleted payCompleted){

        // 비동기 방식으로 카프카 리스너를 통해 결제가 완료된 이벤트를 파악 -> 프로모션 포인트 제공
        if(payCompleted.isMe()){
            System.out.println("promotion_policy_wheneverPayCompleted_PointSave");

            Promotion promotion = new Promotion();
            promotion.setOrderId(payCompleted.getOrderId());
            //if(payCompleted.getPrice() != null && payCompleted.getPrice() > 0) {
                promotion.setPoint((double) 100);
                promotion.setProcess("Payed");
            //}
            promotionRepository.save(promotion);

            System.out.println("##### listener PayComplete : " + payCompleted.toJson());
        }
    }

}
