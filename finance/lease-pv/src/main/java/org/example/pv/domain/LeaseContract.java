package org.example.pv.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class LeaseContract {
    private final double interestRate;
    private final int periodCount;
    private final BigDecimal leaseTotalAmount;
    private final List<LeaseSchedule> schedules;
    private BigDecimal presentValue = new BigDecimal("0.0");
    private BigDecimal leaseLiab = new BigDecimal("0.0");
    private BigDecimal interest = new BigDecimal("0.0");

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
     * @param schedule Lease Schedule 입력.
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
     */
    public void calculatePV() {
        /**
         * Important!!
         * PV 계산은 Payment Schdule의 순서가 중요하다.
         * 아래 총 금액이 같아도 2개의 PV는 다르다.
         *
         *   Case #1       Case#2
         *   1월 100       1월 200
         *   2월 200       2월 300
         *   3월 300       3월 100
         *
         */
        this.schedules.sort(LeaseSchedule.orderByLocalDate());

        // 각 Line별 PV 계산 및 총 PV 등록.
        presentValue = IntStream.rangeClosed(0, periodCount - 1)
            .boxed()
            .map(i -> this.schedules.get(i).calculateSchedulePV(i + 1))
            .reduce(BigDecimal::add)
            .orElseThrow(() -> new RuntimeException("Calculate PV Error"));

        // Lease 부채와 PV의 합계는 같다.
        // 하지만 Interest 금액은 비율과 반올림이 적용되어 단수이가 발생한다.
        // 이 단수차이는 가장 마지막 Payment Schedule 에 적용한다.
        // 단수차이가 발생하면 아래와 같이 계산한다.
        //  1. 가장 마지막 Schedule 의 Lease 부채 = Lease 부채 + 단수차이
        //  2. 가장 마지막 Schedule 의 이자비용    = 이자비용 - 단수차이
        BigDecimal leaseBalance = new BigDecimal(presentValue.toString());
        for (LeaseSchedule schedule : schedules) {
            leaseBalance = leaseBalance.subtract(
                schedule.calculateInterestAndLeaseLiability(leaseBalance));

            leaseLiab = leaseLiab.add(schedule.getLeaseLibAmount());
            interest = interest.add(schedule.getInterestAmount());
        }

        if (!leaseBalance.equals(BigDecimal.ZERO.setScale(leaseBalance.scale(), RoundingMode.HALF_UP))) {
            schedules.get(schedules.size() - 1)
                .adjustDiff(leaseBalance);

            leaseLiab = leaseLiab.add(leaseBalance);
            interest = interest.subtract(leaseBalance);
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
                schedule.getRoundingAmount() != null ?
                    numberFormat.format(schedule.getRoundingAmount()) : ""
            );
        }
    }

    @Override
    public String toString() {
        return "LeaseContract{" +
            "interestRate=" + interestRate +
            ", periodCount=" + periodCount +
            ", leaseTotalAmount=" + leaseTotalAmount +
            ", presentValue=" + presentValue +
            ", leaseLiab=" + leaseLiab +
            ", interest=" + interest +
            '}';
    }

    public double getInterestRate() {
        return interestRate;
    }

    public static class OverflowScheduleSize extends RuntimeException {
        public OverflowScheduleSize() {
        }
    }
}
