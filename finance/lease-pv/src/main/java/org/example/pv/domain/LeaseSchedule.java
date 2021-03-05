package org.example.pv.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;

public class LeaseSchedule {
  private LeaseContract contract;
  private final LocalDate scheduleDate;
  private final BigDecimal scheduleAmount;
  private BigDecimal interestAmount;
  private BigDecimal schedulePV;
  private BigDecimal leaseLibAmount;
  private BigDecimal roundingAmount;

  public LeaseSchedule(LocalDate scheduleDate, double scheduleAmount) {
    this.scheduleDate = scheduleDate;
    this.scheduleAmount = BigDecimal.valueOf(scheduleAmount);
  }

  BigDecimal calculateSchedulePV(long index) {
    this.schedulePV = this.scheduleAmount.divide(
        BigDecimal.valueOf(
            Math.pow(1 + this.contract.getInterestRate() / 100d, index)), 0, RoundingMode.HALF_EVEN);

    return schedulePV;
  }

  BigDecimal calculateInterestAndLeaseLiability(BigDecimal leaseBalance) {
    BigDecimal interestRate = BigDecimal.valueOf(this.contract.getInterestRate() / 100d);
    this.interestAmount = leaseBalance.multiply(interestRate).setScale(0, RoundingMode.HALF_EVEN);
    this.leaseLibAmount = this.scheduleAmount.subtract(this.interestAmount);
    return this.leaseLibAmount;
  }

  void setContract(LeaseContract contract) {
    this.contract = contract;
  }

  void adjustDiff(BigDecimal adjustAmount) {
    this.leaseLibAmount = this.leaseLibAmount.add(adjustAmount);
    this.interestAmount = this.interestAmount.subtract(adjustAmount);
    this.roundingAmount = adjustAmount;
  }

  public static Comparator<LeaseSchedule> orderByLocalDate() {
    return (o1, o2) -> o1.getScheduleDate().isBefore(o2.getScheduleDate()) ? -1 :
        o1.getScheduleDate().isAfter(o2.getScheduleDate()) ? 1 : 0;
  }

  @Override
  public String toString() {
    return "LeaseSchedule{" +
        "scheduleDate=" + scheduleDate +
        ", scheduleAmount=" + scheduleAmount +
        ", interestAmount=" + interestAmount +
        ", schedulePV=" + schedulePV +
        ", leaseLibAmount=" + leaseLibAmount +
        ", roundingAmount=" + roundingAmount +
        '}';
  }

  public LocalDate getScheduleDate() {
    return scheduleDate;
  }

  public BigDecimal getScheduleAmount() {
    return scheduleAmount;
  }

  public BigDecimal getInterestAmount() {
    return interestAmount;
  }

  public BigDecimal getSchedulePV() {
    return schedulePV;
  }

  public BigDecimal getLeaseLibAmount() {
    return leaseLibAmount;
  }

  public BigDecimal getRoundingAmount() {
    return roundingAmount;
  }
}
