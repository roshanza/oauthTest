package au.com.metlife.reference.utils;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;

public class CommonUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class.getName());

	private CommonUtils() {
		super();
	}


	public static HttpEntity<String> formEntity(HttpServletRequest request) {
		
		LOGGER.info("Header:"+ request.getHeader("Authorization"));
		LOGGER.info("Header:"+ request.getHeader("accept"));
		
		final HttpHeaders headers = new HttpHeaders();
		headers.set("accept", request.getHeader("accept"));
		headers.set(HttpHeaders.AUTHORIZATION, request.getHeader("Authorization"));
		return new HttpEntity<>("parameters", headers);
	}
	
	
	public static  String getUniqueStringEveryTime(){
	    return new UUID(get64MostSignificantBitsForVersion1(), get64LeastSignificantBitsForVersion1()).toString();
	}
	
	private static long get64LeastSignificantBitsForVersion1() {
	    Random random = new Random();
	    long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
	    long variant3BitFlag = 0x8000000000000000L;
	    return random63BitLong + variant3BitFlag;
	}

	private static long get64MostSignificantBitsForVersion1() {
	    LocalDateTime start = LocalDateTime.of(1582, 10, 15, 0, 0, 0);
	    Duration duration = Duration.between(start, LocalDateTime.now());
	    long seconds = duration.getSeconds();
	    long nanos = duration.getNano();
	    long timeForUuidIn100Nanos = seconds * 10000000 + nanos * 100;
	    long least12SignificatBitOfTime = (timeForUuidIn100Nanos & 0x000000000000FFFFL) >> 4;
	    long version = 1 << 12;
	    return 
	      (timeForUuidIn100Nanos & 0xFFFFFFFFFFFF0000L) + version + least12SignificatBitOfTime;
	}
	
public static String getFundCodeFromJWT(Jwt jwt) {
		
		String cleintId = jwt.getClaimAsString("client_id");
		if(cleintId!=null && cleintId.length()==8) {
			return cleintId.substring(4);
		}
		else {
			throw new ResponseStatusException(FORBIDDEN, "Invalide Client ID:"+cleintId);
		}
		
		
	}
}
