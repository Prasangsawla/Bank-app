package app;

import Service.BankService;
import Service.impl.BankServiceimpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("Welcome to console bank");
        Scanner sc = new Scanner(System.in);
        BankService bankService = new BankServiceimpl();
        boolean running = true;
        while(running)
        {
            System.out.println("""
                1)Open Account
                2)Deposit
                3)Withdraw
                4)Transfer
                5)Account Statements
                6)List Account
                7)Search Account by customer name
                0)Exit
                """);
            System.out.print("Choose : ");
            String choice = sc.nextLine().trim();
            //System.out.println("Choice : "+ choice);

            switch (choice){
                case "1" -> openAccount(sc ,bankService);
                case "2" -> deposit(sc,bankService);
                case "3" -> withdraw(sc,bankService);
                case "4" -> transfer(sc,bankService);
                case "5" -> statement(sc,bankService);
                case "6"->listAccounts(sc , bankService);
                case "7"->searchAccounts(sc,bankService);
                case "0"->running=false;
            }
        }
    }

    private static void openAccount(Scanner sc, BankService bankservice) {
        System.out.println("Customer name: ");
        String name = sc.nextLine().trim();
        System.out.println("Customer email: ");
        String email = sc.nextLine().trim();
        System.out.println("Account Type (SAVINGS/CURRENT): ");
        String Type=sc.nextLine().trim();
        System.out.println("Initial deposit(optional,blank for 0): ");
        String amt=sc.nextLine().trim();
        Double initial = Double.valueOf(amt);
        String accountnumber = bankservice.openAccount(name,email,Type);
        if(initial>0)
            bankservice.deposit(accountnumber,initial,"Initial repo");
        System.out.println("Account opened " + accountnumber);
    }

    private static void deposit(Scanner sc,BankService bankService) {
        System.out.println("Account number : ");
        String accountNumber = sc.nextLine().trim();
        System.out.println("Amount : ");
        Double amount = Double.valueOf(sc.nextLine().trim());
        bankService.deposit(accountNumber,amount,"Deposit");
        System.out.println("Deposited");

    }

    private static void withdraw(Scanner sc , BankService bankService) {
        System.out.println("Account number : ");
        String accountNumber = sc.nextLine().trim();
        System.out.println("Amount : ");
        Double amount = Double.valueOf(sc.nextLine().trim());
        bankService.withdraw(accountNumber,amount,"withdrawl");
        System.out.println("Withdrawn");
    }

    private static void transfer(Scanner sc,BankService bankService) {
        System.out.println("From Account : ");
        String from = sc.nextLine().trim();
        System.out.println("To account : ");
        String to = sc.nextLine().trim();
        System.out.println("Amount: ");
        Double amount = Double.valueOf(sc.nextLine().trim());
        bankService.transfer(from,to,"Transfer",amount);
        System.out.println("Withdrawn");

    }

    private static void statement(Scanner sc, BankService bankService) {
        System.out.println("Account number: ");
        String account = sc.nextLine().trim();
        bankService.getStatement(account).forEach(t -> {
            System.out.println(t.getTimestamp() + " | " + t.getType() + " | " + t.getAmount() + " | " + t.getNote());
        });
    }


    private static void listAccounts(Scanner sc,BankService bankService) {
        bankService.listAccounts().forEach(a->{
            System.out.println(a.getAccountNumber()+"|"+a.getAccountType()+"|" + a.getBalance());
        });

    }

    private static void searchAccounts(Scanner scanner, BankService bankService) {
        System.out.println("Customer name contains: ");
        String q = scanner.nextLine().trim();
        bankService.searchAccountsByCustomerName(q).forEach(account ->
                System.out.println(account.getAccountNumber() + " | " + account.getAccountType() + " | " + account.getBalance())
        );
    }
}
