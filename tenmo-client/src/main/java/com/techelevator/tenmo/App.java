package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;
import io.cucumber.java.bs.A;

import java.math.BigDecimal;
import java.util.Scanner;

import static java.math.BigDecimal.ZERO;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService();
    private final TransferService transferService = new TransferService();
    public Scanner inputScanner = new Scanner(System.in);

    private AuthenticatedUser currentUser;

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
		// TODO Auto-generated method stub
       int userId = currentUser.getUser().getId();
       BigDecimal balance = accountService.getBalance(userId);
        System.out.println(balance.toString());;

		
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        //int userId = currentUser.getUser().getId();
        User[] activeUsers = accountService.listUsers();
        for (int i = 0; i < activeUsers.length; i++){

            System.out.println("");
            System.out.println((i + 1) + ".  " + activeUsers[i].getUsername());

        }
        System.out.println("Please type the full username of the user you would like to transfer to.");

        String accountToName = inputScanner.nextLine().trim();
        int accountTo = accountService.getIdByUsername(accountToName);
        System.out.println("Please enter the amount you would like to transfer.");
        BigDecimal transferAmount = inputScanner.nextBigDecimal();
        int accountFrom = accountService.getIdByUsername(currentUser.getUser().getUsername());
        BigDecimal accountFromBalance = accountService.getBalance(currentUser.getUser().getId());
        Transfer newTransfer = new Transfer(accountFrom, accountTo, transferAmount);
        if (accountFrom == accountTo || transferAmount.compareTo(ZERO) <= 0 || transferAmount.compareTo(accountFromBalance) > 0){
            newTransfer.setTransferStatusId(3);
        }
        else newTransfer.setTransferStatusId(2);
        newTransfer.setTransferTypeId(2);
        if (newTransfer.getTransferStatusId() == 3){
            transferService.createTransfer(newTransfer);
            System.out.println("Transfer is rejected.");
        }
        else transferService.createTransfer(newTransfer);

		
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
