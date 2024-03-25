package org.ssb.meet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ssb.meet.service.MeetingRoomService;
import org.ssb.meet.service.MeetingService;
import org.ssb.meet.service.ParticipantService;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MeetApplicationTests {

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private MeetingRoomService meetingRoomService;

	@Autowired
	private ParticipantService participantService;

	@Test
	void contextLoads() {
		assertThat(meetingService).isNotNull();
		assertThat(meetingRoomService).isNotNull();
		assertThat(participantService).isNotNull();
	}

}
