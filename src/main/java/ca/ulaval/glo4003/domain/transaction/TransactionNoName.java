package ca.ulaval.glo4003.domain.transaction;

import ca.ulaval.glo4003.domain.user.UserRepository;
import java.time.LocalDate;
import java.util.List;

public class TransactionNoName {
  private final UserRepository userRepository;

  public TransactionNoName(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<Transaction> getTransactions(LocalDate from, LocalDate to) {
    return null;
  }

  public List<Transaction> getTransactionByEmail(LocalDate from, LocalDate to, String email) {
    //List<Transaction> transactions = userRepository.
    return null;
  }

  public List<Transaction> getTransactionByTitle(LocalDate from, LocalDate to, String title) {
    return null;
  }

  private List<Transaction> getAllTransactions() {
    return null;
  }
}
