package in.anubhav.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import in.anubhav.jpa.entity.UserDetails;

public interface IUserDetails extends JpaRepository<UserDetails , Integer> {

}
