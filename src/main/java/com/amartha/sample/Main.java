package com.amartha.sample;

import com.amartha.sample.accessor.BillingAccessor;
import com.amartha.sample.accessor.BillingAccessorInMemoryImpl;
import com.amartha.sample.model.Bill;
import com.amartha.sample.model.CreateLoanSpec;
import com.amartha.sample.model.IsDelinquentSpec;
import com.amartha.sample.model.MakePaymentSpec;
import com.amartha.sample.service.BillingService;
import com.amartha.sample.service.BillingServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BillingAccessor accessor = new BillingAccessorInMemoryImpl();
        BillingService billingService = new BillingServiceImpl(accessor);
        Scanner scanner = new Scanner(System.in);

        // initialize loan and bills
        CreateLoanSpec createLoanSpec = new CreateLoanSpec(50, BigDecimal.valueOf(5000000), BigDecimal.valueOf(0.1));
        int loanID = billingService.createLoan(createLoanSpec);

        int menu = 0;
        do {
            System.out.println("----");
            System.out.println("Menu");
            System.out.println("----");
            System.out.println("1. get loans");
            System.out.println("2. get bills");
            System.out.println("3. get outstanding");
            System.out.println("4. make payment");
            System.out.println("5. is delinquent");
            System.out.println("\n0. exit");
            System.out.print("Choose: ");

            try {
                String input = scanner.nextLine();
                menu = Integer.parseInt(input);

                switch (menu) {
                    case 1 -> {
                        System.out.println("Get Loans");
                        System.out.println(billingService.getLoans());
                    }
                    case 2 -> {
                        System.out.println("Get Bills");
                        List<Bill> bills = billingService.getBills(loanID);
                        if (bills != null) {
                            for (Bill bill : bills) {
                                System.out.println(bill);
                            }
                        }
                    }
                    case 3 -> {
                        System.out.println("Get Outstanding");
                        System.out.println(billingService.getOutstanding(loanID));
                    }
                    case 4 -> {
                        System.out.println("Make Payment");
                        System.out.print("Date: ");
                        LocalDate date = LocalDate.parse(scanner.nextLine());
                        System.out.print("Amount: ");
                        BigDecimal amount = new BigDecimal(scanner.nextLine());
                        boolean success = billingService.makePayment(new MakePaymentSpec(loanID, date, amount));
                        System.out.println("Payment " + (success ? "Succeed" : "Failed"));
                    }
                    case 5 -> {
                        System.out.println("Is Delinquent");
                        System.out.print("Date: ");
                        LocalDate date = LocalDate.parse(scanner.nextLine());
                        System.out.println("Delinquent -> " + billingService.isDelinquent(new IsDelinquentSpec(loanID, date)));
                    }
                }
                System.out.println("===========");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (menu != 0);

        scanner.close();
    }
}