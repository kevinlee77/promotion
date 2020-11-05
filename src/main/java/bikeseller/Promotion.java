package bikeseller;

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

    @PrePersist
    public void onPrePersist() {
        System.out.println("promotion pre persist");
    }

    @PostPersist
    public void onPostPersist(){
        System.out.println(this.toString());
        System.out.println("promotion persist");

        if("Payed".equals(process)){
            // 결제 완료된 이벤트를 통해 프로모션 제공 완료 처리
            setProcess("PromotionCompleted");
            //setPoint((double) getOrderId());

            PromoCompleted promoCompleted = new PromoCompleted();
            BeanUtils.copyProperties(this, promoCompleted);
            promoCompleted.publish();

            System.out.println("*** 프로모션 포인트 제공 완료 ***");
        } else if("PayCancelled".equals(process)){
            setProcess("PromotionCancelled");
            //setPoint((double) getOrderId());

            PromoCancelled promoCancelled = new PromoCancelled();
            BeanUtils.copyProperties(this, promoCancelled);
            promoCancelled.publish();
            System.out.println("*** 결제 취소로 인한 프로모션 포인트 제공 회수 ***");
        }
    }

    @PreUpdate
    public void onPreUpdate(){
        System.out.println("promotion pre update");
    }

    @PostUpdate
    public void onPostUpdate(){
        System.out.println("promotion post update");
    }

    @PreRemove
    public void onPreRemove(){
        System.out.println("promotion pre remove");
    }

    @PostRemove
    public void onPostRemove(){
        System.out.println("promotion post remove");
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
