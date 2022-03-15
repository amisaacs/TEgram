package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TenmoService;
import jdk.swing.interop.SwingInterOpUtils;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private Account account = null;
    private TenmoService tenmoService = new TenmoService(API_BASE_URL);

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        // get token out of auth user and pass thru get balance
        System.out.println("Your current account balance is: $ " + tenmoService.getBalance(currentUser.getToken()));
	}

	private void viewTransferHistory() {
        account = tenmoService.getAccount(currentUser.getUser().getUsername(),currentUser.getToken() );
        User[] users = tenmoService.getUsers(currentUser.getToken());
        System.out.println("-------------------------------------------\n" +
                "Transfers\n" +
                "ID          From/To                 Amount\n" +
                "-------------------------------------------\n");
        Transfer[] transfers = tenmoService.listTransfersByUserId(account.getAccountId(),
                currentUser.getToken());
		for (Transfer transfer: transfers){
            if (account.getAccountId() == transfer.getAccountFrom()){
                //AMISAAC - get printf to work properly.
                //System.out.printf("          To: %d          %d.2");
                String userName = tenmoService.getUser(transfer.getAccountTo(), currentUser.getToken()).getUsername();
                System.out.println(transfer.getId() + "          To: " +   userName
                         + "            $ " + transfer.getAmount());
            } else {
                String userName = tenmoService.getUser(transfer.getAccountFrom(), currentUser.getToken()).getUsername();

                System.out.println(transfer.getId() + "          From: " + userName + "          $ " + transfer.getAmount());
            }
        }
        // view one transfer detail
        Long transferId = (long)consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel): ");
        Transfer transfer = tenmoService.getTransfer(transferId,currentUser.getToken() );
        System.out.println("");
        System.out.println("--------------------------------------------\n" +
                "Transfer Details\n" +
                "--------------------------------------------\n");
        System.out.println("Id: " + transfer.getId());
        System.out.println("From: " + tenmoService.getUser(transfer.getAccountFrom(), currentUser.getToken()).getUsername());
        System.out.println("To: " + tenmoService.getUser(transfer.getAccountTo(), currentUser.getToken()).getUsername());
        System.out.println("Type: "+ transfer.getTransferTypeId());
        System.out.println("Status: " + transfer.getTransferStatusId());
        System.out.println("Amount: $" + transfer.getAmount());
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        User[] users = tenmoService.getUsers(currentUser.getToken());
        System.out.println("-------------------------------------------\n" +
                "Users\n" +
                "ID          Name\n" +
                "-------------------------------------------\n");
        for (User user: users){
           if (currentUser.getUser().getId() != user.getId()){
               System.out.println(user.toString());
           }
        }
        System.out.println("--------- ");

        Long recipientId = (long) consoleService.promptForInt("Enter ID of user you are " +
                "sending to (0 to cancel):");

        BigDecimal transferAmount = consoleService.promptForBigDecimal("Enter amount:");

        //Get Username from userId
        String recipientName = getUserName(users, recipientId);

        Account accountSender = tenmoService.getAccount(currentUser.getUser().getUsername(),
                currentUser.getToken() );
        Account accountRecipient = tenmoService.getAccount(recipientName,
                currentUser.getToken() );
        Transfer transfer = new Transfer(2L,2L, accountSender.getAccountId(),
                accountRecipient.getAccountId(),
                transferAmount);

        tenmoService.makeTransfer( transfer,  transferAmount, currentUser.getToken());

	}

    private String getUserName(User[] users, Long userId) {
        String userName = "";
        for(User user: users){
            if (user.getId().equals(userId)){
                userName = user.getUsername();
            }
        }
        return userName;
    }

    private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
