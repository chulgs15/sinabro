package org.example.pv.domain;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class LeaseContract {
    private final double interestRate;
    private final int periodCount;
    private final BigDecimal leaseTotalAmount;
    private final List<LeaseSchedule> schedules;
    private BigDecimal presentValue;

    public LeaseContract(double interestRate, int periodCount, double leaseTotalAmount) {
        this.interestRate = interestRate;
        this.periodCount = periodCount;
        this.leaseTotalAmount = BigDecimal.valueOf(leaseTotalAmount);
        this.schedules = new ArrayList<>(periodCount);
    }

    /**
     * Lease 계약에 Payment Schedule을 추가한다.
     * 이 때 서로 연관관계를 Mapping 하는 작업도 같이 한다.
     *
     * @param schedule
     * @throws OverflowScheduleSize Payment Schedule 이 계획한 Period 개수보다 많으면 발생한다.
     *                              periodCount 조정 혹은 추가할 Schedule의 갯수를 확인한다.
     */
    public void addSchedule(LeaseSchedule schedule) {
        if (this.schedules.size() + 1 > periodCount)
            throw new OverflowScheduleSize();

        this.schedules.add(schedule);
        schedule.setContract(this);
    }

    /**
     * Payment Schedule에서 3가지를 계산한다.
     * 1. PV(Present Value)
     * 2. 이자(Interest)
     * 3. 리스부채(Lease Payable)
     * <p>
     * PV는 현재가치 계산을 사용한다.
     */
    public void calculatePV() {
        // 일단
        this.schedules.sort(LeaseSchedule.orderByLocalDate());

        presentValue = IntStream.rangeClosed(0, periodCount - 1)
            .boxed()
            .map(i -> this.schedules.get(i).calculateSchedulePV(i + 1))
            .reduce(BigDecimal::add)
            .orElseThrow(() -> new RuntimeException("Calculate PV Error"));

        BigDecimal leaseBalance = new BigDecimal(presentValue.toString());
        for (LeaseSchedule schedule : schedules) {
            leaseBalance = leaseBalance.subtract(
                schedule.calculateInterestAndLeaseLiability(leaseBalance));
        }

        // 가장 마지막 라인에 단수차리를 적용.
        if (!leaseBalance.equals(BigDecimal.ZERO)) {
            schedules.get(schedules.size() - 1)
                .adjustDiff(leaseBalance);
        }
    }

    public void dump() {
        System.out.println(toString());
        System.out.println();
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s \n",
            "Date", "Amount", "PV", "Interest", "Lease Lib.", "Rounding");
        System.out.println("========== ========== ========== ========== ========== ==========");

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        for (LeaseSchedule schedule : schedules) {
            System.out.printf("%s %10s %10s %10s %10s %10s \n",
                schedule.getScheduleDate(),
                numberFormat.format(schedule.getScheduleAmount()),
                numberFormat.format(schedule.getSchedulePV()),
                numberFormat.format(schedule.getInterestAmount()),
                numberFormat.format(schedule.getLeaseLibAmount()),
                numberFormat.format(
                    Optional.ofNullable(schedule.getRoundingAmount())
                        .orElse(BigDecimal.ZERO)));
        }
    }

    @Override
    public String toString() {
        return "LeaseContract{" +
            "interestRate=" + interestRate +
            ", periodCount=" + periodCount +
            ", leaseTotalAmount=" + leaseTotalAmount +
            ", schedules.size=" + schedules.size() +
            ", presentValue=" + presentValue +
            '}';
    }

    public double getInterestRate() {
        return interestRate;
    }

    public List<LeaseSchedule> getSchedules() {
        return schedules;
    }

    public static class OverflowScheduleSize extends RuntimeException {
        public OverflowScheduleSize() {
        }
    }
}
