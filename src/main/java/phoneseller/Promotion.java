package phoneseller;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Promotion_table")
public class Promotion {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private Double point;
    private String process;

    @PostPersist
    public void onPostPersist(){
        System.out.println(this.toString());
        System.out.println("promotion persist");
        if(point > 0){
            System.out.println("비동기어쩌구 결제해서 비동기로 포인트생성~");
//            PromoCompleted promoCompleted = new PromoCompleted();
//            BeanUtils.copyProperties(this, promoCompleted);
//            promoCompleted.publish();

        } else {
            System.out.println("동기화로 결제취소해서 프로모취소~");
//            PromoCancelled promoCancelled = new PromoCancelled();
//            BeanUtils.copyProperties(this, promoCancelled);
//            promoCancelled.publish();

        }
    }

    @PreUpdate
    public void onPreUpdate(){
        System.out.println("promotion update");
    }

    @PostRemove
    public void onPostRemove(){

        System.out.println("promotion remove");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", point=" + point +
                ", process=" + process +
                '}';
    }
}
