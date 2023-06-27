package com.bitflip.sanolagani.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.models.Transaction;

import com.bitflip.sanolagani.repository.TransactionRepo;
import com.bitflip.sanolagani.repository.UserRepo;
import com.bitflip.sanolagani.service.AdminService;
import com.bitflip.sanolagani.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userrepo;
	@Autowired
	TransactionRepo transaction_repo;
	
	@Override
	public void saveUser(User u) {
		userrepo.save(u);
	}

	@Override
	public boolean checkEmail(String email) {
		User user = userrepo.findByEmail(email);
		if(user==null) {
			return false;
		}
		return true;
	}

	@Override
	public void updatePassword(String email, String password) {
		List<User> userlist = userrepo.findAll();
		for(User user:userlist) {
			if(user.getEmail().equals(email)) {
				String encode_password=AdminServiceImpl.encodePassword(password);
				user.setPassword(encode_password);
				userrepo.save(user);
			}
		}
		
	}

	public List<Transaction> getCollateralSummery(int id) {
          List<Transaction> transaction_list = new ArrayList<>();
		List<Transaction> transactionlist = transaction_repo.findAll();
		if(!transactionlist.isEmpty()) {
			for(Transaction transaction:transactionlist) {
				if(transaction.getUser().getId()==id&&transaction.getTransaction_type().equals("collateral")) {
					transaction_list.add(transaction);
				}
			}
			return transaction_list;
			
		}
		return null;
		
	}

	public List<Transaction> getFundHistory(int id) {
		List<Transaction> transaction_list = new ArrayList<>();
		List<Transaction> transactionlist = transaction_repo.findAll();
		if(!transactionlist.isEmpty()) {
			for(Transaction transaction:transactionlist) {
				if(transaction.getUser().getId()==id) {
					transaction_list.add(transaction);
				}
			}
			return transaction_list;
			
		}
		return null;
		
	}

}
