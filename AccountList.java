import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountList {

    private ArrayList<Account> accounts;
    private String filename;

    private Account updatedAcc;

    public AccountList(String filename) {
        this.accounts = new ArrayList<>();
        this.filename = filename;
        this.readFile(filename);
    }

    private void readFile(String filename) {
        Scanner input = null;

        try {
            input = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (input.hasNext()) {

            String line = input.nextLine();
            String[] accountInfo = line.split(",");
            int accountNumber = Integer.parseInt(accountInfo[0]);
            String accountPassword = accountInfo[1];
            String accountName = accountInfo[2];
            Double accountFunds = Double.parseDouble(accountInfo[3]);

            Account a = new Account(accountNumber, accountPassword, accountName, accountFunds);
            this.accounts.add(a);
        }
        input.close();
    }

    public Account loginToAccount(int accountNumInput, String accountPassInput) {
        for (Account a : this.accounts) {
            if (a.getAccountNumber() == accountNumInput && a.getAccountPassword().equals(accountPassInput)) {
                return a;
            }
        }
        return null;
    }

    public void depositFunds(Account a, Double addedFunds) {

        int changeIndex = -1;
        int i = 0;

        for (Account account : this.accounts) {
            if (account == a) {

                Double updatedFunds = a.getFunds() + addedFunds;

                changeIndex = i;
                updatedAcc = new Account(a.getAccountNumber(), a.getAccountPassword(), a.getAccountName(),
                        updatedFunds);
                break;
            }
            i++;
        }

        if (changeIndex > -1) {
            this.accounts.remove(changeIndex);
            this.accounts.add(changeIndex, updatedAcc);
            this.writeFile(filename);
        }

    }

    public void withdrawFunds(Account a, Double removedFunds) {

        int changeIndex = -1;
        int i = 0;

        for (Account account : this.accounts) {
            if (account == a) {
                Double updatedFunds = a.getFunds() - removedFunds;

                changeIndex = i;
                updatedAcc = new Account(a.getAccountNumber(), a.getAccountPassword(), a.getAccountName(),
                        updatedFunds);
                break;
            }
            i++;
        }

        if (changeIndex > -1) {
            this.accounts.remove(changeIndex);
            this.accounts.add(changeIndex, updatedAcc);
            this.writeFile(filename);
        }
    }

    public void viewBalance(Account a) {
        for (Account account : this.accounts) {
            if (account == a) {
                System.out.println(a.getAccountName() + " - " + a.getAccountNumber() + ": $" + a.getFunds());
                break;
            }
        }

    }

    private void writeFile(String filename) {
        // create print writer
        PrintWriter output = null;

        try {
            output = new PrintWriter(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Account a : this.accounts) {
            // print each account onto the csv file with toString method
            output.println(a);
        }
        // close file
        output.close();
    }

}
