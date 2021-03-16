package domain.payables;

import lombok.Getter;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ap_hold_id_s", sequenceName = "ap_hold_id_s", initialValue = 1, allocationSize = 1)
@Table(name = "ap_hold_all")
@Getter
public class APHold {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ap_hold_id_s")
    @Column(name = "hold_id")
    private Long holdId;

    @Column(name = "hold_code", nullable = false)
    private String code;

    @Column(name = "msg", nullable = false)
    private String msg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private AbstractAPInvoiceEntity invoiceEntity;

    public APHold() {
    }

    public APHold(InvoiceHoldType invoiceHoldType, AbstractAPInvoiceEntity invoiceEntity) {
        this.code = invoiceHoldType.getHoldCode();
        this.msg = invoiceHoldType.getMsg();
        this.invoiceEntity = invoiceEntity;
    }
}
