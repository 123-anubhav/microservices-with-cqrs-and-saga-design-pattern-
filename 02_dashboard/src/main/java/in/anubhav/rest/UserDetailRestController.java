package in.anubhav.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.anubhav.constant.AppConstants;
import in.anubhav.jpa.entity.UserDetails;
import in.anubhav.service.IUserService;

@RestController
public class UserDetailRestController {

	Logger logger = LoggerFactory.getLogger(UserDetailRestController.class);

	private IUserService userService;

	public UserDetailRestController(IUserService userService) {
		super();
		this.userService = userService;
	}

	@KafkaListener(topics = AppConstants.TOPIC, groupId = "user_data_v2",
			containerFactory = "userKafkaListenerContainerFactory"  // 👈 explicitly tell Spring
			)
	public void subscribeMsg(UserDetails user) {
		logger.info(
				"************** UserDetailRestController.subscribeMsg() ************** execution started\nuser data are ::",
				user);
		System.out.print("*** Msg Recieved From Kafka *** :: ");

		System.out.println(user);
		logger.info(
				"************** UserDetailRestController.subscribeMsg() ************** db saving operation start\n");
		userService.save(user);
		logger.info("************** UserDetailRestController.subscribeMsg() ************** db saving operation end\n");
	}

	@GetMapping("/records")
	public ResponseEntity<List<UserDetails>> fetchAllRecord() {
		return new ResponseEntity(userService.fetchAllRecord(), HttpStatus.OK);
	}

}
