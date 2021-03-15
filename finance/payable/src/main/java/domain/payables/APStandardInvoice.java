package domain.payables;


import domain.embed.CurrencyAmount;
import domain.enums.payable.InvoiceHoldType;
import domain.payables.abstracts.AbstractAPInvoiceEntity;
import domain.payables.abstracts.AbstractAPLineEntry;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ap_std_invoices_all")
@DiscriminatorValue(value = "STANDARD")
@Getter
public class APStandardInvoice extends AbstractAPInvoiceEntity {

    @OneToMany(mappedBy = "invoiceEntity", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<APItemLine> itemLines = new ArrayList<>();

    @OneToMany(mappedBy = "invoiceEntity", cascade = CascadeType.PERSIST)
    private final List<AbstractAPLineEntry> lines = new ArrayList<>();

    public APStandardInvoice() {
        super();
    }

    private APStandardInvoice(String vendorName, Long taxClassificationCode, CurrencyAmount currencyAmount) {
        super(vendorName, taxClassificationCode, currencyAmount);
    }

    public boolean isAmountPositive() {
        return List.of(1, 0).contains(super.getCurrencyAmount().getAmount().compareTo(BigDecimal.ZERO));
    }

    public APStandardInvoice addLine(APItemLine line) {
        itemLines.add(line);
        line.addInvoiceEntry(this);
        return this;
    }

    public boolean isSameLineAndHeaderAmount() {
        BigDecimal totalLineSum = this.lines.stream()
                .map(x -> x.getLineAmount().getAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        return totalLineSum.compareTo(super.getCurrencyAmount().getAmount()) == 0;
    }


    public void validate() {
        InvoiceHoldType invoiceHoldType = super.getStatus().validate(this);

        if (invoiceHoldType != null) {
            super.addHold(invoiceHoldType);
        } else {
            super.validateOK();
        }
    }

    public static class Builder {
        private String vendorName;
        private long taxClassificationCode;
        private CurrencyAmount currencyAmount;

        public Builder() {
        }

        public Builder vendorName(String vendorName) {
            this.vendorName = vendorName;
            return this;
        }

        public Builder taxClassificationCode(long taxClassificationCode) {
            this.taxClassificationCode = taxClassificationCode;
            return this;
        }

        public Builder currencyAmount(CurrencyAmount currencyAmount) {
            this.currencyAmount = currencyAmount;
            return this;
        }

        public APStandardInvoice build() {
            Objects.requireNonNull(vendorName);
            Objects.requireNonNull(currencyAmount);

            return new APStandardInvoice(this.vendorName,
                    this.taxClassificationCode, this.currencyAmount);
        }

    }

}
