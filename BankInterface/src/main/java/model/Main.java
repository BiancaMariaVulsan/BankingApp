package model;

import repository.AccHolderRepository;
import repository.AdminRepository;
import repository.CurrentAccRepository;
import repository.SavingsAccRepository;

import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        SavingsAccRepository savingsAccRepository = new SavingsAccRepository();
        CurrentAccRepository currentAccRepository = new CurrentAccRepository();
        AdminRepository adminRepository = new AdminRepository();
        AccHolderRepository userRepository = new AccHolderRepository();

        /** Read the Users from the database in the beg of the proj */
        ArrayList<AccHolder> accHolders = userRepository.selectAllEntities();
        ArrayList<Admin> admins = adminRepository.selectAllEntities();
        System.out.println("AccHolders:");
        for (AccHolder accHolder : accHolders) {
            System.out.println(accHolder.getFirstName());
        }
        System.out.println("Admins:");
        for (Admin admin : admins) {
            System.out.println(admin.getFirstName());
        }

        /** Admin - just for testing -> uncomment and change the id accordingly to test delete */
//        Date date = new Date(2001, 6, 15);
//        java.sql.Date sqlDate = new java.sql.Date(date.getDate());
//        Admin admin1 = new Admin(26, "Maria", "Toma", sqlDate, "6012345678910"); // dau un id aiurea pt test, in aplicatie oricum ii iau din db
//        // add admin
//        // un user nu poate sa fie si accHolder si admin
//        if (!adminRepository.checkIfObjectAlreadyExists(admin1) && !userRepository.checkIfObjectAlreadyExists(admin1))
//            adminRepository.insertEntity(admin1);
//        // delete admin
//        if(!admins.isEmpty()) {
//            adminRepository.deleteEntity(admins.get(0));
//        }

        /** Account Holder - just for testing -> uncomment and change the id accordingly to test delete */
        // add accHolder
        // dau un id aiurea pt test, in aplicatie oricum ii iau din db
//        AccHolder accHolder1 = new AccHolder(24, "Gabi", "Stancu", "6067887456880", "GabiS", "5678");
//        if(!adminRepository.checkIfObjectAlreadyExists(accHolder1) && !userRepository.checkIfObjectAlreadyExists(accHolder1))
//            userRepository.insertEntity(accHolder1);
//        // delete accHolder
//        if(!accHolders.isEmpty()) {
//            // Deja id-ul e setat cand sterg, deci pot sa sterg dupa id
//            userRepository.deleteEntity(accHolders.get(0));
//         }

        /** Current Account */
//        // open account
//        Account acc1 = new Savings(1, accHolders.get(1), 55.0);
//        if(!savingsAccRepository.checkIfObjectAlreadyExists(acc1) && !currentAccRepository.checkIfObjectAlreadyExists(acc1)) {
//            accHolders.get(1).createAccount(acc1);
//        }
//        // close account
//        AccHolder accHolder = accHolders.get(0);
//        if(!accHolder.getAccounts().isEmpty()) {
//            Account accountToClose = accHolder.getAccounts().get(0);
//            accHolder.closeAccount(accountToClose);
//        }

        /** Savings Account */
        // open account
//        Account acc2 = new Current(1, accHolders.get(2), 70.0);
//        if(!savingsAccRepository.checkIfObjectAlreadyExists(acc2) && !currentAccRepository.checkIfObjectAlreadyExists(acc2)) {
//            accHolders.get(2).createAccount(acc2);
//        }
//        // close account
//        AccHolder accHolder = accHolders.get(0);
//        if(!accHolder.getAccounts().isEmpty()) {
//            Account accountToClose = accHolder.getAccounts().get(0);
//            accHolder.closeAccount(accountToClose);
//        }

        /** Print the list of accounts of accholder */
        if (!accHolders.isEmpty()) {
            ArrayList<Account> accounts1 = accHolders.get(0).getAccounts();
            for (Account account : accounts1) {
                System.out.println(account.accNumber);
            }
        }

        /** Test Transaction */
//         accHolders.get(0).getAccounts().get(0).transfer(accHolders.get(0).getAccounts().get(1), 2.0, "christmas gift");
        accHolders.get(1).getAccounts().get(0).transfer(accHolders.get(0).getAccounts().get(0), 3.0, "dinner");
//        ArrayList<Transaction> transactions = accHolders.get(0).getAccounts().get(0).getTransactions();
//        for(Transaction transaction : transactions) {
//            System.out.println(transaction.id);
//        }

        /** Test Deposit/Withdraw */
//        accHolders.get(0).getAccounts().get(0).deposit(50);
//        accHolders.get(0).getAccounts().get(0).withdraw(10);

        /** Test Admin's actions */
        Account account1 = new Savings(1, accHolders.get(2), 100.0);
//        AccHolder accHolder = accHolders.get(2);
//        admins.get(0).createUserAccount(accHolder, account1);
//        Account accToDelete = accHolder.getAccounts().get(1);
//        admins.get(0).closeUserAccount(accHolder, accToDelete);
        AccHolder accHolderToAdd = new AccHolder(12, "Casiana", "Vulsan", "6132923874839", "CasiV", "8894");
//        admins.get(0).createUser(accHolderToAdd);
//        admins.get(0).deleteUser(accHolder);
//        admins.get(0).makeTransaction(accHolders.get(0).getAccounts().get(0), accHolders.get(1).getAccounts().get(0), 10, "fun");
    }
}
