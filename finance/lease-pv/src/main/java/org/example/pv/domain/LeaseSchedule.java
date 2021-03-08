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

  /**
   * Payment Schedule 1개의 PV를 계산한다.
   *
   * @param index Schedule 순서.
   * @return 계산한 PV
   */
  BigDecimal calculateSchedulePV(long index) {
    this.schedulePV = this.scheduleAmount.divide(
        BigDecimal.valueOf(
            Math.pow(1 + this.contract.getInterestRate() / 100d, index)), 0, RoundingMode.HALF_UP);
    return schedulePV;
  }

  /**
   * PV로 리스부채와 이자를 계산.
   *
   * @param leaseBalance 각 라인을 누적 차감한 리스부채 금액.
   * @return 남은 리스부채 금액.
   */
  BigDecimal calculateInterestAndLeaseLiability(BigDecimal leaseBalance) {
    BigDecimal interestRate = BigDecimal.valueOf(this.contract.getInterestRate() / 100d);
    this.interestAmount = leaseBalance.multiply(interestRate).setScale(0, RoundingMode.HALF_UP);
    this.leaseLibAmount = this.scheduleAmount.subtract(this.interestAmount);
    return this.leaseLibAmount;
  }

  /**
   * LeaseContract와 연결.
   * @param contract
   */
  void setContract(LeaseContract contract) {
    this.contract = contract;
  }

  /**
   * 단수차이 적용 method. 단수차이가 발생하면 아래와 같이 계산한다.
   *  1. 가장 마지막 Schedule 의 Lease 부채 = Lease 부채 + 단수차이
   *  2. 가장 마지막 Schedule 의 이자비용    = 이자비용 - 단수차이
   *  3. 단수차이 저장
   * @param contract
   */
  void adjustDiff(BigDecimal adjustAmount) {
    this.leaseLibAmount = this.leaseLibAmount.add(adjustAmount);
    this.interestAmount = this.interestAmount.subtract(adjustAmount);
    this.roundingAmount = adjustAmount;
  }

  /**
   * 날짜 순서로 정렬하기 위한 Comparator.
   * @return Comparator<LeaseSchedule>
   */
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