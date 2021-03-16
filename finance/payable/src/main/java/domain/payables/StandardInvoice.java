package domain.payables;


import domain.embed.CurrencyAmount;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Entity
@Table(name = "ap_std_invoices_all")
@DiscriminatorValue(value = "STANDARD")
@Getter
public class StandardInvoice extends AbstractAPInvoiceEntity {

    @OneToMany(mappedBy = "invoiceEntity", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @Where(clause = "invoice_line_type = 'ITEM'")
    private final List<ItemLine> itemLines = new ArrayList<>();

    @OneToMany(mappedBy = "invoiceEntity", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @Where(clause = "invoice_line_type = 'TAX'")
    private final List<TaxLine> taxLines = new ArrayList<>();

//    @OneToMany(mappedBy = "invoiceEntity", cascade = CascadeType.PERSIST)
//    private final List<AbstractAPLineEntry> lines = new ArrayList<>();

    public StandardInvoice() {
        super();
    }

    private StandardInvoice(String vendorName, Long taxClassificationCode, CurrencyAmount currencyAmount, double taxRate) {
        super(vendorName, taxClassificationCode, currencyAmount, taxRate);
    }

    public boolean isAmountPositive() {
        return List.of(1, 0).contains(super.getCurrencyAmount().getAmount().compareTo(BigDecimal.ZERO));
    }

    public StandardInvoice addLine(ItemLine line) {
        itemLines.add(line);
        line.addInvoiceEntry(this);
        return this;
    }

    public boolean isSameLineAndHeaderAmount() {
        BigDecimal totalLineSum = this.itemLines.stream()
                .map(x -> x.getLineAmount().getAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        totalLineSum = totalLineSum.add(this.taxLines.stream()
            .map(x -> x.getLineAmount().getAmount())
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO));

        return totalLineSum.compareTo(super.getCurrencyAmount().getAmount()) == 0;
    }

    public void validate() {
        InvoiceHoldType invoiceHoldType = super.getStatus().validate(this);

        if (invoiceHoldType != null) {
            super.addHold(invoiceHoldType);
        } else {
            super.changeStatusToValidated();
        }
    }

    public void calculateTax() {
        if (this.getTaxRate() == null) {
            return;
        }

        BigDecimal totalSum = BigDecimal.ZERO;
        for (ItemLine itemLine : itemLines) {
            CurrencyAmount lineAmount = itemLine.getLineAmount();
            totalSum = totalSum.add(lineAmount.getConvertedAmount());

            BigDecimal taxRate = new BigDecimal(this.getTaxRate() / 100d);

            Currency currency = Currency.getInstance(lineAmount.getCurrency());
            int truncPoint = currency.getDefaultFractionDigits();

            BigDecimal taxAmount = lineAmount.getAmount().multiply(taxRate)
                .setScale(truncPoint, RoundingMode.DOWN);

            CurrencyAmount amount =
                new CurrencyAmount(lineAmount.getCurrency(), lineAmount.getExchangeRate(), taxAmount);

            totalSum = totalSum.add(amount.getConvertedAmount());

            TaxLine taxLine = new TaxLine(amount, "세금라인", this, itemLine);

            taxLines.add(taxLine);
        }

        // 단수차이 적용.
        BigDecimal diff = this.getCurrencyAmount().getConvertedAmount().subtract(totalSum);
        if (diff.compareTo(BigDecimal.ZERO) != 0) {
            // 첫번째 TaxLine에 적용
            CurrencyAmount lineAmount = taxLines.get(0).getLineAmount();
            lineAmount.adjustDiff(diff);
        }
    }

    public void cancel() {
        super.getStatus().cancel(this);
    }

    void createReverseLine() {
        int size = this.itemLines.size();
        for (int i = 0; i < size; i++) {
            CurrencyAmount reverseAmount = this.itemLines.get(i).getLineAmount().createReverseAmount();
            ItemLine reverseItemLine = new ItemLine(reverseAmount, "취소라인", this);
            this.itemLines.add(reverseItemLine);

            TaxLine taxLine = this.itemLines.get(i).getTaxLine().get(0);
            CurrencyAmount reverseTaxAmount = taxLine.getLineAmount().createReverseAmount();

            TaxLine reverseTaxLine = new TaxLine(reverseTaxAmount, "취소 세금 라인", this, reverseItemLine);
            this.taxLines.add(reverseTaxLine);
        }
    }

    void changeInvoiceAmountToZero() {
        super.changeInvoiceAmountZeroForCancel();
    }


    public static class Builder {
        private String vendorName;
        private long taxClassificationCode;
        private double taxRate;
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

        public Builder taxRate(double taxRate) {
            this.taxRate = taxRate;
            return this;
        }

        public Builder currencyAmount(CurrencyAmount currencyAmount) {
            this.currencyAmount = currencyAmount;
            return this;
        }

        public StandardInvoice build() {
            Objects.requireNonNull(vendorName);
            Objects.requireNonNull(currencyAmount);

            return new StandardInvoice(this.vendorName,
                    this.taxClassificationCode, this.currencyAmount, this.taxRate);
        }

    }

}
