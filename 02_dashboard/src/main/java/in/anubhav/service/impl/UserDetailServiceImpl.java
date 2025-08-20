package in.anubhav.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.anubhav.jpa.IUserDetails;
import in.anubhav.jpa.entity.UserDetails;
import in.anubhav.service.IUserService;

@Service
public class UserDetailServiceImpl implements IUserService {

	private IUserDetails userDetail;

	public UserDetailServiceImpl(IUserDetails userDetail) {
		super();
		this.userDetail = userDetail;
	}

	@Override
	public List<UserDetails> fetchAllRecord() {
		return userDetail.findAll();
	}

	@Override
	public void save(UserDetails user) {
		userDetail.save(user);
	}

}
