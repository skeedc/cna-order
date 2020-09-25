package mall;

import mall.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    @Autowired
    OrderRepository orderRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverShipped_Updatestatus(@Payload Shipped shipped){

        if(shipped.isMe()){
            System.out.println("##### listener Updatestatus : " + shipped.toJson());
            // 재고량 수정
            // 수정전 소스
            /*
            Optional<Product> productOptional = productRepository.findById(orderPlaced.getProductId());
            Product product = productOptional.get();
            product.setStock(product.getStock() - orderPlaced.getQuantity());
            productRepository.save(product);
            */

            Optional<Order> orderOptional = orderRepository.findById(shipped.getOrderId());
            Order order = orderOptional.get();
            order.setStatus(shipped.getStatus());

            orderRepository.save(order);
        }
        }
    }

